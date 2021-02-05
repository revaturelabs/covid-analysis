import com.johnsnowlabs.nlp.pretrained.PretrainedPipeline
import com.johnsnowlabs.nlp.SparkNLP
import org.apache.spark.sql.{SparkSession, DataFrame}
import org.apache.spark.sql.functions.{array_contains, col}

//grab tweets from text file
object SentimentRatio {
  def readTweets(spark: SparkSession): Dataframe = {}

  //initialize NLP library sentiment analysis pipeline
  def setUpPipeline(df: DataFrame): DataFrame = {}

  /** check twitter text fields to determine
    * if tweet is positive or negative sentiment.
    * Returns dataframe with counts of each sentiment.
    */
  def analyzeSentiment(spark: SparkSession, df: DataFrame): DataFrame = {}

  //selects and returns only positive sentiments
  def positiveSentiments(spark: SparkSession, df: DataFrame): Int = {
    0
  }

  //selects and returns only negative sentiments
  def negativeSentiments(spark: SparkSession, df: DataFrame): Int = {
    0   
  }

  //Returns percentage of positive to negative tweets
  def positiveRatio(posSentiment: Int, negSentiment: Int): Double = {
    0.00
  }
}
