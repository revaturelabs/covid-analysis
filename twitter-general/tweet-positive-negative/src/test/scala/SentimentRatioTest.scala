import org.scalatest.flatspec.AnyFlatSpec

class SentimentRatioTest extends AnyFlatSpec {
    "Positive ratio" should "return 1" in {
        assert(SentimentRatio.positiveRatio(1,0) == 1)
    }

    it should "return 0" in {
        assert(SentimentRatio.positiveRatio(0,1) == 0)
    }

    //TODO
    "Analyze Sentiment" should "return 1" in {
        assert(SentimentRatio.analyzeSentiments == 1)
    }

}