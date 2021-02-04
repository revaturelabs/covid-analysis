import org.scalatest.flatspec.AnyFlatSpec

class WordCountSpec extends AnyFlatSpec {
    "WordCount" should "count meaningful non-covid words" in {
        assert(WordCount.tweetCovid19Words("test-data.json")("Test").equals(1))
    }
}