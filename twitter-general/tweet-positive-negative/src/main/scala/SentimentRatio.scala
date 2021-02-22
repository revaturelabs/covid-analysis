import com.johnsnowlabs.nlp.pretrained.PretrainedPipeline
import com.johnsnowlabs.nlp.SparkNLP
import org.apache.spark.sql.{SparkSession, DataFrame}
import org.apache.spark.sql.functions.{array_contains, col}
import scala.math.BigDecimal.RoundingMode

object SentimentRatio {

  //grabs tweets from text file and splits each tweet into a separate row
  def readTweets(filePath: String, spark: SparkSession): DataFrame = {
    import spark.implicits._
    val rawText = spark.read.text(filePath)
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
    val pipeline =
      PretrainedPipeline("analyze_sentimentdl_use_twitter", lang = "en")
    val results = pipeline.annotate(df, "value")
    //make it work with $"col1"
    results
  }

  /** check twitter text fields to determine
    * if tweet is positive or negative sentiment.
    * Returns dataframe with counts of each sentiment.
    */
  def analyzeSentiment(spark: SparkSession, df: DataFrame): DataFrame = {
    import spark.implicits._
    val sentimentResults = df
      .select($"sentiment" ("result").as("Sentiment Results"))
      .groupBy($"Sentiment Results")
      .count()
      .orderBy($"count".desc)
      .cache()

    sentimentResults
  }

  //selects and returns count of only positive sentiments
  def positiveSentiments(spark: SparkSession, df: DataFrame): Int = {
    import spark.implicits._
    val positiveSentiment = df
      .where(array_contains(($"Sentiment Results"), "positive"))
      .collect()

    // println(positiveSentiment(0)(1))
    positiveSentiment(0)(1).toString.toInt
  }

  //selects and returns count of only negative sentiments
  def negativeSentiments(spark: SparkSession, df: DataFrame): Int = {
    import spark.implicits._
    val negativeSentiment = df
      .where(array_contains(($"Sentiment Results"), "negative"))
      .collect()

    // println(negativeSentiment(0)(1))
    negativeSentiment(0)(1).toString.toInt
  }

  //Returns percentage of positive to negative tweets with two decimal precision
  def positiveRatio(posSentiment: Int, negSentiment: Int): Double = {
    val posRatio =
      posSentiment.toDouble / (posSentiment.toDouble + negSentiment.toDouble)
    val posPercent =
      BigDecimal(posRatio).setScale(2, RoundingMode.HALF_UP).toDouble
    // println(posPercent)
    posPercent * 100
  }

  //converts final results into a dataframe
  def createResultsDf(
      spark: SparkSession,
      negResults: Int,
      posResults: Int,
      finalResults: Int
  ): DataFrame = {
    import spark.implicits._
    val resultsDF = spark
      .createDataFrame(
        Seq(
          ("Number of Negative Sentiments", negResults),
          ("Number of Positive Sentiments", posResults),
          ("Percentage of Positive Sentiments", finalResults)
        )
      )
      .toDF()

    resultsDF
  }

  //runs all methods above
  def tweetPositiveNegative(spark: SparkSession, path: String): DataFrame = {
    val data = readTweets(path, spark)
    val df = setUpPipeline(data)
    val sentiments = analyzeSentiment(spark, df)
    val negSent = negativeSentiments(spark, sentiments)
    val posSent = positiveSentiments(spark, sentiments)
    val finalPosRatio = positiveRatio(posSent, negSent).toInt

    createResultsDf(spark, negSent, posSent, finalPosRatio)
  }
}
