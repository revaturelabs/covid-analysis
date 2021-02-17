import org.scalatest.flatspec.AnyFlatSpec
import org.apache.spark.sql.{SparkSession, DataFrame}

class SentimentRatioTest extends AnyFlatSpec {

  val spark = SparkSession
    .builder()
    .appName("sentimentAnalysis")
    .master("local[*]")
    .getOrCreate()

  import spark.implicits._
  //sample data to pass onto NLP pipeline
  val testData = spark
    .createDataFrame(
      Seq(
        (1, "I hate you."),
        (2, "I love you."),
        (3, "i love you....just kidding i hate you")
      )
    )
    .toDF("id", "value")
    .select($"value")

  //sample data to compare pipeline results
  val testData2 = spark
    .createDataFrame(
      Seq(
        (1, "negative"),
        (2, "negative"),
        (3, "positive")
      )
    )
    .toDF("id", "Sentiment Results")
    .select($"Sentiment Results")
    .groupBy($"Sentiment Results")
    .count()
    .orderBy($"count".desc)
    .cache()

  //library pipeline setup that is not for testing
  val df = SentimentRatio.setUpPipeline(testData)
  val sentiments = SentimentRatio.analyzeSentiment(spark, df)

  /** testData contains two sentences that should be of negative sentiment and 1 of positive sentiment.
    * testData2 has two direct mentions of negative sentiment and 1 of positive sentiment.
    * When compared with analyzeSentiment, sentiment counts should eqaul
    */
  "analyzeSentiment" should "return 2 negative count when compared to 2 negative sentiments" in {
    assert(
      sentiments.head.getAs[Long]("count") == testData2.head.getAs[Long](
        "count"
      )
    )
  }
  it should "return 1 positive count when compared to 1 positive sentiment" in {
    assert(
      sentiments.head(2)(1).getAs[Long]("count") == testData2
        .head(2)(1)
        .getAs[Long]("count")
    )
  }

  //based on testData, there should be 1 positive sentiment
  "positiveSentiments" should "return type int 1" in {
    assert(SentimentRatio.positiveSentiments(spark, sentiments) === 1)
  }
  //based on testData, there should be 2 negative sentiment
  "negativeSentiments" should "return type int 2" in {
    assert(SentimentRatio.negativeSentiments(spark, sentiments) === 2)
  }

  "positiveRatio" should "return type double 1" in {
    assert(SentimentRatio.positiveRatio(1, 0) === 100.0)
  }
  it should "return type double 0" in {
    assert(SentimentRatio.positiveRatio(0, 1) === 0)
  }
  it should "return type double .5" in {
    assert(SentimentRatio.positiveRatio(2, 2) === 50.0)
  }

}
