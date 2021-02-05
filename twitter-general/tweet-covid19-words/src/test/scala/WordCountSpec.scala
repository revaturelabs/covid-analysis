import org.scalatest.flatspec.AnyFlatSpec
import org.apache.spark.sql.SparkSession

class WordCountSpec extends AnyFlatSpec {

    val spark = SparkSession
        .builder
        .appName("Tester")
        .master("local[*]")
        .getOrCreate()

    // Here, we know the word "netflix" is located three times in our test data in one of our Covid-19 related Tweets
    // As such, it should be returned in our map with a count of one
    "WordCount" should "count meaningful non-covid words" in {
        assert(WordCount.tweetCovid19Words("test-data.json", spark)("netflix").equals(3))
    }

    // Here, we know the word "pandemic" is present in our test data, as well as one of our Covid-19 related Tweets
    // In fact, the presence of this word is WHY the Tweet is Covid-19 related
    // However, Covid-19 related words are used to identify Tweets, but are omitted from actually being counted themselves
    // As such, the word "pandemic" should not be returned in our map
    it should "omit words directly related to covid-19" in {
        assert(!WordCount.tweetCovid19Words("test-data.json", spark).contains("pandemic"))
    }

    // Here, we know the word "the" is present in our test data, as well as one of our Covid-19 related Tweets
    // However, it is in our list of non-meaningful words, so it should not be returned in our map
    it should "omit words that are not meaningful" in {
        assert(!WordCount.tweetCovid19Words("test-data.json", spark).contains("the"))
    }

    // Here, we know "Belfast" is present in our test data, but is in a non-Covid-19 related Tweet
    // As such, it should not be returned in our map
    it should "omit entire tweets that are not related to covid-19" in {
        assert(!WordCount.tweetCovid19Words("test-data.json", spark).contains("belfast"))
    }
}