import com.johnsnowlabs.nlp.pretrained.PretrainedPipeline
import com.johnsnowlabs.nlp.SparkNLP
import org.apache.spark.sql.{SparkSession, DataFrame}
import org.apache.spark.sql.functions.{array_contains, col}

object SentimentRatio {

  //grabs tweets from text file and splits each tweet into a separate row
  def readTweets(spark: SparkSession): DataFrame = {
    import spark.implicits._
    val rawText = spark.read.text("sample-data/test-data.txt")
    val tweets = rawText
      .map(x => {
        val elements = x.getString(0).split("\"text\":")
        (elements(1))
      })
      .toDF()
    // tweets.printSchema()
    // tweets.show(false)
    tweets
  }

  //initialize JohnSnowLabs Spark NLP library pretrained sentiment analysis pipeline
  def setUpPipeline(df: DataFrame): DataFrame = {
    df
  }

  /** check twitter text fields to determine
    * if tweet is positive or negative sentiment.
    * Returns dataframe with counts of each sentiment.
    */
  def analyzeSentiment(spark: SparkSession, df: DataFrame): DataFrame = {
    df
  }

  //selects and returns count of only positive sentiments
  def positiveSentiments(spark: SparkSession, df: DataFrame): Int = {
    43
  }

  //selects and returns count of only negative sentiments
  def negativeSentiments(spark: SparkSession, df: DataFrame): Int = {
    43
  }

  //Returns percentage of positive to negative tweets with two decimal precision
  def positiveRatio(posSentiment: Int, negSentiment: Int): Double = {
    43.00
  }
}
