import org.scalatest.flatspec.AnyFlatSpec

class SentimentRatioTest extends AnyFlatSpec {

  val spark = SparkSession
    .builder()
    .appName("sentimentAnalysis")
    .master("local[*]")
    .getOrCreate()

  val dummyData = spark
    .createDataFrame(
      Seq(
        (1, "I hate you."),
        (2, "I love you."),
        (3, "i love you....just kidding i hate you")
      )
    )
    .toDF("id", "value")

  //library pipeline setup that is not for testing
  val df = SentimentRatio.setUpPipeline(dummyData)
  val sentiments = SentimentRatio.analyzeSentiment(spark, df)

  "Positive sentiment" should "return type int 1" in {
    assert(SentimentRatio.positiveSentiments(spark, sentiments) === 1)
  }

  "Negative sentiment" should "return type int 2" in {
    assert(SentimentRatio.negativeSentiments(spark, sentiments) === 2)
  }

  "Positive ratio" should "return type double 1" in {
    assert(SentimentRatio.positiveRatio(1, 0) === 1)
  }

  it should "return type double 0" in {
    assert(SentimentRatio.positiveRatio(0, 1) === 0)
  }

  it should "return type double .5" in {
    assert(SentimentRatio.positiveRatio(2, 2) === .5)
  }

}
