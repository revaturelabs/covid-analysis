package util

import org.scalatest.flatspec.AnyFlatSpec
import org.apache.spark.sql.SparkSession
import scribe.file._


class FileUtilTest extends AnyFlatSpec {

  // Log Spark info to logs/test/
  scribe.Logger.root
    .clearHandlers()
    .clearModifiers()
    .withHandler(writer = FileWriter(
      "logs" / "test" / ("app-" % year % "-" % month % "-" % day % ".log")
    ))
   .replace()

  lazy val spark = SparkSession
    .builder()
    .appName("Testing")
    .master("local[1]")
    .getOrCreate()

  // DF_Test.json is a small sample json file that contains data for 5 tweets.
  val jsonPath = "src/test/resources/DF_Test.json"

  // Test that the resulting DataFrame contains a row for each of the 5 tweets in the json file.
  "getDataFrameFromJson" should "return a DataFrame that has 5 rows" in {
    assert(FileUtil.getDataFrameFromJson(spark, jsonPath).count == 5)
  }

  // Test that the resulting DataFrame's first row has an id of 1212470713338286081
  it should "return a DataFrame in which the first row has an id of 1212470713338286081" in {
    val df = FileUtil.getDataFrameFromJson(spark, jsonPath)
    assert(df.first.getAs[Long]("id") == 1212470713338286081L)
    spark.stop
  }
}
