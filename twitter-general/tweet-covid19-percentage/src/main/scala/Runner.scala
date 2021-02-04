package TweetCovid19Percentage
import org.apache.spark.sql.SparkSession

object Runner {
    
    def main(args: Array[String]): Unit = {
        // Grab the Spark Session object, set the app Name option, EMR will handle the rest of the config
        val spark = SparkSession.builder().appName("TweetCovid19Percentage").getOrCreate()
    }

    /**
      * A function to calculate the percentage of Covid19 related tweets from the batch data indicated
      * by the input file path parameter. It returns the percentage itself as an integer.
      * @param path The path to the input data file
      * @return The percentage as an Integer
      */
    def tweetCovid19Percentage(path: String): Int = {
        return 0
    }
}
