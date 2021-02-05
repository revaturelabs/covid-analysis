package TweetCovid19Percentage
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.{DataFrameReader,DataFrame}

object Runner {
    // A case class to hold the tweet text data for data set
    case class Tweet(text: String)
    
    def main(args: Array[String]): Unit = {
        // Grab the Spark Session object, set the app Name option, EMR will handle the rest of the config
        val spark = SparkSession.builder().appName("TweetCovid19Percentage").getOrCreate()
        // TODO: Learn more about spark implicits because you know nothing atm 
        import spark.implicits._
        // TODO: Hardcode for now, replace with s3 at some point
        val filePath = "/test-data.txt"
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
        // Grab the data from the input file and store in a dataframe
        val tweetDataFrame = ReadInputFileToDF(path, spark) 
        // TODO: Read in list of covid related words
        // Im thinking this will be a dataframe, could be a DataSet of type Tweet
        // TODO: Call a function that will read in the lexicon of covid words
        // May be able to use the previous groups, not sure if I will need a DF for comparison
        // TODO: Loop through dataframe and send each text to the comparison function
        // Thinking this can just be a .map with the function call on a dataset
        // Call function to take results however they are stored and aggregate over to calculate percentage
        return 0
    }
    /**
      * A function that takes a string filepath and parses the data from the file,
      * returns a data frame with each row as the text of a tweet
      * @param path The path to the input data file
      * @return A dataframe containing the text of a tweet in each row
      */
    def ReadInputFileToDF(path: String, spark: SparkSession): DataFrame = {
        import spark.implicits._
        //TODO implement dataframe read
        val tweetDataFrame = spark.read
            .option("inferSchema", "true")
            .textFile(path).cache().toDF()
        return tweetDataFrame
    }

    /**
      * This function takes the text from a given tweet, 
      * checks the lexicon of covid related words for any matches
      * @param text the text of a given tweet
      * @return True if any matches to covid words in the tweet, false if none
      */
    def IsCovidRelatedText(text: String): Boolean = {
        // TODO: Implement comparison of input text with lexicon, not sure of data structures involved yet
        // Return true at the first match to avoid further processing
        return false
    }
}
