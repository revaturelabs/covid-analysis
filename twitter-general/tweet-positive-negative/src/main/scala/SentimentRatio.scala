import com.johnsnowlabs.nlp.pretrained.PretrainedPipeline
import com.johnsnowlabs.nlp.SparkNLP
import org.apache.spark.sql.{SparkSession, DataFrame}
import org.apache.spark.sql.functions.{array_contains, col}

object SentimentRatio {
  
  //grab tweets from text file
  def readTweets(spark: SparkSession): Dataframe = {
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

  //initialize NLP library sentiment analysis pipeline
  def setUpPipeline(df: DataFrame): DataFrame = {
    val pipeline =
      PretrainedPipeline("analyze_sentimentdl_use_twitter", lang = "en")
    val results = pipeline.annotate(df, "value")
    results
  }

  /** check twitter text fields to determine
    * if tweet is positive or negative sentiment.
    * Returns dataframe with counts of each sentiment.
    */
  def analyzeSentiment(spark: SparkSession, df: DataFrame): DataFrame = {
    val sentimentResults = df
      .select($"sentiment" ("result").as("Sentiment Results"))
      .groupBy($"Sentiment Results")
      .count()
      .cache()

    sentimentResults
  }

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
