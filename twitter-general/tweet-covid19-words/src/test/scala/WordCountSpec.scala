import org.scalatest.flatspec.AnyFlatSpec
import org.apache.spark.sql.SparkSession
import scala.sys.process._
import org.apache.commons.io.FileUtils
import java.io.File

class WordCountSpec extends AnyFlatSpec {

    val spark = SparkSession
        .builder
        .appName("Tester")
        .master("local[*]")
        .getOrCreate()
    
    val results = WordCount.tweetCovid19Words("test", spark)

    // Here, we know the word "netflix" is located three times in our test data in our Covid-19 related Tweets.
    // As such, it should be returned in our map with a count of one.
    "WordCount" should "count meaningful non-covid words" in {
        assert(results(0).equals(" netflix 3"))
        assert(results.mkString.contains(" netflix "))
    }

    // Here, we know the word "pandemic" is present in our test data, as well as one of our Covid-19 related Tweets.
    // In fact, the presence of this word is WHY the Tweet is Covid-19 related.
    // However, Covid-19 related words are used to identify Tweets, but are omitted from actually being counted themselves.
    // As such, the word "pandemic" should not be returned in our map.
    it should "omit words directly related to covid-19" in {
        assert(!results.mkString.contains(" pandemic "))
    }

    // Here, we know the word "the" is present in our test data, as well as one of our Covid-19 related Tweets.
    // However, it is in our list of non-meaningful words, so it should not be returned in our map.
    it should "omit words that are not meaningful" in {
        assert(!results.mkString.contains(" the "))
    }

    // Here, we know "Belfast" is present in our test data, but is in a non-Covid-19 related Tweet.
    // As such, it should not be returned in our map.
    it should "omit entire tweets that are not related to covid-19" in {
        assert(!results.contains(" belfast "))
    }

    // This test is just checking to make sure that our data was saved to S3.
    // It does this by grabbing the data that should have been saved and checking the first line to ensure that it is in good order.
    it should "save the results of its analysis to the target S3 bucket" in {
        FileUtils.deleteQuietly(new File("test-retrieve.json"))
        val grabFromS3 = "aws s3 cp s3://covid-analysis-p3/datawarehouse/twitter-general/word-count/WordCountResults-TestData.json test-retrieve.json"
        grabFromS3.!
        val firstLineOfFile = scala.io.Source.fromFile("test-retrieve.json").getLines().take(1).mkString
        assert(firstLineOfFile.equals("{\"value\":\"netflix\",\"count\":3}"))
    }
}