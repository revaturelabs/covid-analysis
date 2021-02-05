package TweetCovid19Percentage

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.{DataFrameReader,DataFrame}
import org.scalatest.flatspec.AnyFlatSpec

/**
  * A class full of unit tests for testing the functionality of the various functions
  * in the Runner.scala class
  */
class RunnerSpec extends AnyFlatSpec{
    // Grab the Spark Session object, set the app Name option, EMR will handle the rest of the config
    val spark = SparkSession.builder().appName("TweetCovid19Percentage").getOrCreate()
    // TODO: Learn more about spark implicits because you know nothing atm 
    import spark.implicits._
    // The path to the input test data file
    val testFilePath = "/test-data.txt"
    // A test DataFrame for comparison to DataFrame returned by reader function
    val testDF = spark.read.format("text").load("test-data.txt")
    // Some test strings for testing the covid related terms matching function
    val testString1 = "Testing"
    val testString2 = "Covid"

    // The ReadInputFileToDF function should parse the input data into a DataFrame for processing
    // The DataFrame should have a single column with values being the text of the individual tweets
    "ReadInputFileToDF case 0" should "return a DataFrame containing the input test data" in {
        assert(Runner.ReadInputFileToDF(testFilePath, spark).collect().equals(testDF.collect()))
    }

    // The input test data contains half covid related and half non-covid related tweets
    // The tweetCovid19Percentage function should return an integer value of 50 represeting the percentage
    "tweetCovid19Percentage case 0" should "return 50 percent with the test input data" in {
        assert(Runner.tweetCovid19Percentage(testFilePath, spark) == 50)
    }

    // The IsCovidRelatedText function takes a tweet text string as input and loops through
    // the list of Covid terms. The test string here is not a covid term so it should return false
    "IsCovidRelatedText case 0" should "return false for input test string 1 (english noncovid single word)" in {
        assert(Runner.IsCovidRelatedText(testString1) == false)
    }

    // The IsCovidRelatedText function takes a tweet text string as input and loops through
    // the list of Covid terms. The test string here is a covid term so it should return true
    "IsCovidRelatedText case 1" should "return true for input test string 2 (english covid single word)" in {
        assert(Runner.IsCovidRelatedText(testString2) == true)
    }
    
}