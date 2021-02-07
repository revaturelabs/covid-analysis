package hashtagByRegion

import org.scalatest.flatspec.AnyFlatSpec
import org.apache.spark.sql.SparkSession
import scala.collection.mutable.WrappedArray
import scribe.file._

import util.FileUtil

class HashtagByRegionTest extends AnyFlatSpec {

  // Log Spark info to logs/test/
  scribe.Logger.root
    .clearHandlers()
    .clearModifiers()
    .withHandler(writer = FileWriter(
      "logs" / "test" / ("app-" % year % "-" % month % "-" % day % ".log")
    ))
   .replace()
  
  val spark = SparkSession
    .builder()
    .appName("Hashtag-By-Region")
    .master("local[4]")
    .getOrCreate()

  // DF_Test.json is a small sample json file that contains data for 5 tweets.
  val jsonPath = "src/test/resources/DF_Test.json"

  val DF = FileUtil.getDataFrameFromJson(spark, jsonPath)

  // Test that the resulting DataFrame contains a row for each of the 2 entries in the DataFrame.
  "generateDF" should "return a DataFrame that has 2 rows" in {
    assert(HashtagByRegion.generateDF(spark, DF).count == 2)
  }

  // Test that the resulting DataFrame first row contains the hastag "china"
  it should "return a DataFrame in which the first row contains the hashtag \"china\"" in {
    val testDF = HashtagByRegion.generateDF(spark, DF)
    assert(testDF.first().getAs[WrappedArray[String]]("Hashtags").mkString == "china")
  }
}