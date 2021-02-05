package util

import org.scalatest.flatspec.AnyFlatSpec
import org.apache.spark.sql.SparkSession

class FileUtilTest extends AnyFlatSpec {

  val spark = SparkSession
      .builder()
      .appName("Testing")
      .master("local[4]")
      .getOrCreate()

  // DF_Test.json is a small sample json file that contains data for 5 tweets.
  val jsonPath = "DF_Test.json"

  // Test that the resulting DataFrame contains a row for each of the 5 tweets in the json file.
  "Get DataFrame from JSON" should "return a DataFrame that has 5 rows" in {
    assert(FileUtil.getDataFrameFromJson(spark, jsonPath).count == 5)
    spark.stop()
  }
}