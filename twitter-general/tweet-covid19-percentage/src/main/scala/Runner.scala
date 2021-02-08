package TweetCovid19Percentage
import org.apache.log4j._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.{DataFrameReader,DataFrame,Row,Dataset}

object Runner {
    // A case class to hold the tweet text data for data set
    case class Tweet(value: String)
    
    def main(args: Array[String]): Unit = {
        // Set the log level to only print errors
        Logger.getLogger("org").setLevel(Level.ERROR)
        // Grab the Spark Session object, set the app Name option, EMR will handle the rest of the config
        val spark = SparkSession.builder().master("local").appName("TweetCovid19Percentage").getOrCreate()
        // TODO: Learn more about spark implicits because you know nothing atm 
        import spark.implicits._
        // TODO: Hardcode for now, replace with s3 at some point
        val filePath = "test-data.txt"
        // Calculate the Percentage of Covid related Tweets from the input data file
        tweetCovid19Percentage(filePath, spark)
        spark.stop()
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
        return 0
    }
    /**
      * A function that takes a string filepath and parses the data from the file,
      * returns a data frame with each row as the text of a tweet
      * @param path The path to the input data file
      * @return A dataframe containing the text of a tweet in each row
      */
    def ReadInputFileToDS(path: String, spark: SparkSession): Dataset[Tweet] = {
        import spark.implicits._
        val tweetDataSet = spark.read.text(path).as[Tweet]
        //tweetDataSet.show()
        return tweetDataSet
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
