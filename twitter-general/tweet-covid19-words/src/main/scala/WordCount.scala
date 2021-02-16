import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import scala.sys.process._
import java.io.File
import java.io.PrintWriter
import java.io.FileOutputStream
import java.io.FileInputStream
import org.apache.hadoop.fs.FileSystem
import org.apache.hadoop.fs.Path
import scala.collection.immutable.ListMap
import org.apache.spark.sql.Row
import org.apache.spark.sql.Dataset

object WordCount {

    // Simple case class that will be used to create a DataSet from our initial DataFrame.
    case class TweetText(value: String)


    /** Grabs the top non-covid related words from covid related Tweets by performing the following actions:
      * - Grabs covid related terms from Terms.scala, which will be used to identify covid related Tweets.
      * - Grabs set of meaningless words from Terms.scala, and then unions them with covidTerms
      * to create a Set of words that will not be counted in our final results.
      * - Defines the data input path based on user input (see param "range" for more information).
      * - Reads from our selected path and converts the output to a DataSet of type TweetText.
      * - Filters out Tweets that are not covid related.
      * - Removes newline indicators, separates each line into a series of strings, filters out meaningless words 
      * (words in our Terms.otherTerms set as well as words less than three letters in length) and covid related words,
      * makes our strings lowercase for proper comparison, counts each word occurance, sorts the results in descending order,
      * takes the top 1000 results, and caches them.
      * - Writes cached results to a local csv file.
      * - Converts our cached results to a string that will be used for unit testing as well as showing output to the console.
      * - Prints output to console to show that data has been analyzed.
      * - Renames the file to something meaningful based on the user input.
      * - Sends our renamed results file to S3.
      * - Returns our results array for unit testing.
      * @param range Desired range of data analysis. Options are:
      * -- [1], which will analyze all data from December 11, 2020 to December 25, 2020.
      * -- [2], which will analyze all data from December 26, 2020 to January 5, 2020.
      * -- [test-s3], which will analyze our test data in our S3 bucket (for testing proper S3 data retrieval).
      * -- Typing in anything else as our first argument will run our analysis on our local test data for faster unit testing.
      * @param spark Our SparkSession, which will be declared in our "Runner" main method, as well as in our unit tests.
      * @return Returns a String Array containing the most commonly used meaningful non-covid related words from covid related Tweets.
      */
    def tweetCovid19Words(range: String, spark: SparkSession): Array[String] = {
        
        // Grabs covid related terms from Terms.scala, which will be used to identify covid related Tweets.
        val covidTerms = Terms.getCovidTerms

        // Grabs set of meaningless words from Terms.scala, and then unions them with covidTerms
        // to create a Set of words that will not be counted in our final results.
        val otherTerms = Terms.getOtherTerms
        val ignore = covidTerms.union(otherTerms)

        // Defines the data input path based on user input.
        val path = range match {
            case "1" => "s3a://covid-analysis-p3/datalake/twitter-general/dec_11-dec_25/*"
            case "2" => "s3a://covid-analysis-p3/datalake/twitter-general/dec_26-jan_05/*"
            case "3" => "s3a://covid-analysis-p3/datalake/twitter-general/feb_03-feb_14/*"
            case "test-s3" => "s3a://covid-analysis-p3/datalake/twitter-general/test-data/*"
            case _ => "test-data.txt"
        }

        // Needed to get our spark SQL working.
        import spark.implicits._

        // Reads from our selected path and converts the output to a DataSet of type TweetText.
        val input = spark.read.text(path).as[TweetText]

        // Filters out Tweets that are not covid related.
        val covidTweets = input.filter(text => covidTerms.exists(word => {text.value.toLowerCase.contains(word)}))

        // Removes newline indicators, separates each line into a series of strings, filters out meaningless words 
        // (words in our Terms.otherTerms set as well as words less than three letters in length) and covid related words,
        // makes our strings lowercase for proper comparison, counts each word occurance, sorts the results in descending order,
        // takes the top 1000 results, and caches them.
        val words = covidTweets
            .map(tweet => tweet.value.replace(raw"\n", ""))
            .select(explode(split(col("value"), "\\W+")))
            .filter(word => !ignore.exists(ignoreWord => {word.mkString.toLowerCase.equals(ignoreWord)}))
            .filter(word => word.mkString.length > 2)
            .map(word => word.mkString.toLowerCase)
            .groupBy("value").count
            .sort(desc("count"))
            .limit(1000).cache

        // Writes cached results to a local csv file.
        words.coalesce(1).write.mode("overwrite").option("header", "true").csv("Results")

        // Converts our cached results to a string that will be used for unit testing as well as showing output to the console.
        val results = words.map(row => " " + row.mkString(" ")).collect()

        // Prints output to console to show that data has been analyzed.
        for (i <- 0 to 10) println(results(i))
           
        // Match case that will be used to decide what the name of the file should be.
        val fileName = range match {
            case "1" => "WordCountResults-Dec_11-Dec_25"
            case "2" => "WordCountResults-Dec_26-Jan_05"
            case "3" => "WordCountResults-Feb_03-Feb_14"
            case "test-s3" => "S3ConnectionTestResults"
            case _ => "WordCountResults-TestData"
        }

        // Renames the file to something meaningful based on the user input.
        val fs = FileSystem.get(spark.sparkContext.hadoopConfiguration)
        val file = fs.globStatus(new Path("Results/part*"))(0).getPath().getName()
        fs.rename(new Path("Results/" + file), new Path(s"Results/$fileName.csv"))

        if (fileName.equals("WordCountResults-TestData")) {
            // Sends our renamed results file to S3 (for local use).
            val sendToS3Local = s"aws s3 mv Results/$fileName.csv s3://covid-analysis-p3/datawarehouse/twitter-general/word-count/$fileName.csv"
            sendToS3Local.!
        } else {
            // Sends our renamed results file to S3 from Hadoop.
            val sendToLocal = s"hdfs dfs -get Results/$fileName.csv $fileName.csv"
            val sendToS3 = s"aws s3 mv $fileName.csv s3://covid-analysis-p3/datawarehouse/twitter-general/word-count/$fileName.csv"
            sendToLocal.!
            sendToS3.!
        }
        

        

        // Returns our results array for unit testing.
        results
    }
}