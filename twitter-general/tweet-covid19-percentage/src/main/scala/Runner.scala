package TweetCovid19Percentage
import org.apache.log4j._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.{DataFrameReader,DataFrame,Row,Dataset}
import scala.sys.process._
import java.io.PrintWriter

object Runner {
    // A case class to hold the tweet text data for data set
    case class Tweet(value: String)
    
    def main(args: Array[String]): Unit = {
        // Set the log level to only print errors
        // Uncomment for local run to filter out the excessive barrage of logging output from Spark
        //Logger.getLogger("org").setLevel(Level.ERROR)

        // Grab the Spark Session object, set the app Name option, EMR will handle the rest of the config
        val spark = SparkSession.builder().master("yarn").appName("TweetCovid19Percentage").getOrCreate()
        // TODO: Learn more about spark implicits because you know very little
        import spark.implicits._

        // Adds some jars necessary for our application to run as a thin jar on a Spark cluster.
        //spark.sparkContext.addJar("https://repo1.maven.org/maven2/org/apache/hadoop/hadoop-aws/2.7.4/hadoop-aws-2.7.4.jar")
        //spark.sparkContext.addJar("https://repo1.maven.org/maven2/com/amazonaws/aws-java-sdk/1.7.4/aws-java-sdk-1.7.4.jar")
        
        //val filePath = "test-data.txt"
        // Selecting the input data filepath on S3 based on the user supplied CL parameter
        val filePath = SelectInputDataPath(args(0).toInt)
        // Calculate the Percentage of Covid related Tweets from the input data file
        tweetCovid19Percentage(filePath, spark)

        // Save the results of processing the user defined input data set (CL parameter) to AWS S3
        SaveToS3(args(0).toInt)
        // End the Spark instance for propriety
        spark.stop()
    }

    def SaveToS3(dataRangePrefixSelection: Int): Unit = {
      val fileName = dataRangePrefixSelection match {
          case 0 => "CovidPercentResults-Dec_11-Dec_25"
          case 1 => "CovidPercentResults-Dec_26-Jan_05"
          case 2 => "CovidPercentResults-Feb_03-Feb_14"
          case 3 => "S3ConnectionTestResults"
          case _ => "S3ConnectionTestResults"
      }

      // This line will be executed in the terminal(.!) it uses the AWS CLI tool
      // to move the local file to S3 and rename it. Uncomment for running locally
      //val sendToS3 = s"aws s3 mv Results.csv s3://covid-analysis-p3/datawarehouse/twitter-general/Covid19Percentage/$fileName.csv"
        //sendToS3.!

        // Sends our renamed results file to S3 from Hadoop.
        // Uncomment for running on the EMR cluster
        val sendToLocal = s"hdfs dfs -get Results.csv Results.csv"
        val sendToS3 = s"aws s3 mv Results.csv s3://covid-analysis-p3/datawarehouse/twitter-general/covid19-percent/$fileName.csv"
        sendToLocal.!
        sendToS3.!
    }

    /**
      * A function to calculate the percentage of Covid19 related tweets from the batch data indicated
      * by the input file path parameter. It returns the percentage itself as an integer.
      * @param path The path to the input data file
      * @return The percentage as an Integer
      */
    def tweetCovid19Percentage(path: String, spark: SparkSession): Int = {
        import spark.implicits._
        // Grab the data from the input file and store in a dataset
        val tweetDataSet = ReadInputFileToDS(path, spark) 
        // Create a new dataset by mapping the text values to the boolean values returned 
        // by the IsCovidRelatedText function
        val covidFlags = tweetDataSet.map(x => IsCovidRelatedText(x.value))
        //covidFlags.show()
        return CalculatePercentage(covidFlags)
    }
    
    /**
      * A function that takes a Dataset containing the boolean values returned from
      * the IsCovidRelatedText function, filters the true values, calculates the
      * percentage and returns it as an integer
      * @param resultsDataSet the results of IsCovidRelatedText (Dataset[Boolean])
      * @return The calculated percentage of true values in the results set as integer
      */
    def CalculatePercentage(resultsDataSet:Dataset[Boolean]):Int = {
        // Print line for debugging
        println("Starting CalculatePercentage function...")
        // Store the total count of result values
        val totalCount = resultsDataSet.count()
        // Filter the true values into a new Dataset
        val countingTruesDS = resultsDataSet.filter(x => x.equals(true))
        // Count the true values
        val trueCount = countingTruesDS.count()
        // Calculate the percentage using doubles, then cast back to Integer
        val thePercentage = (trueCount.toDouble/totalCount.toDouble)*100

        // Print line for debugging
        println("Calculated.." + thePercentage)

        // Create a CSV file for the results output
        val writer = new PrintWriter("Results.csv")
        writer.print("Covid,Non-Covid\n")
        writer.print(thePercentage.toString() + "," + (100-thePercentage).toString())
        writer.close()

        return thePercentage.toInt
    }

    /**
      * A function that takes a string filepath and parses the data from the file,
      * returns a DataSet with each row as the text of a tweet
      * @param path The path to the input data file
      * @return A DataSet containing the text of a tweet in each row
      */
    def ReadInputFileToDS(path: String, spark: SparkSession): Dataset[Tweet] = {
        // Must import within scope for any file I/O calls
        import spark.implicits._
        // Grab the file from the parameter passed path and cache it since we will be working with it
        val tweetDataSet = spark.read.text(path).as[Tweet].cache()
        // Print line for debugging
        tweetDataSet.show()
        // Return the DataSet of tweets
        return tweetDataSet
    }

    /**
      * A function that uses pattern matching to select the S3 input path for input 
      * data range indicated by the integer parameter
      * @param dataRangePrefixSelection an Integer mapped to input datasets
      * @return the full S3 file path to the chosen input dataset
      */
    def SelectInputDataPath(dataRangePrefixSelection: Int): String = {
        var fullS3Path = ""
        dataRangePrefixSelection match {
          case 0 => fullS3Path = "s3a://covid-analysis-p3/datalake/twitter-general/dec_11-dec_25/*"
          case 1 => fullS3Path = "s3a://covid-analysis-p3/datalake/twitter-general/dec_26-jan_05/*"
          case 2 => fullS3Path = "s3a://covid-analysis-p3/datalake/twitter-general/feb_03-feb_14/02-03.txt"
          case 3 => fullS3Path = "s3a://covid-analysis-p3/datalake/twitter-general/test-data/*"
          case _ => "Not a valid input data range"
        }
        return fullS3Path
    }

    /**
      * This function takes the text from a given tweet, 
      * checks the lexicon of covid related words for any matches
      * @param text the text of a given tweet
      * @return True if any matches to covid words in the tweet, false if none
      */
    def IsCovidRelatedText(text: String): Boolean = {
        // Grab the list of covid terms
        val termsList = CovidTermsList.getTermsList
        // Loop through the whole list, convert each to lowercase,
        // Test to see if the tweet text contains each word, converting 
        // text to lowercase as well to match strings in any case
        for(i <- termsList){
            if(text.toLowerCase.contains(i.toLowerCase())){
                return true
            }
        }
        return false
    }


}
