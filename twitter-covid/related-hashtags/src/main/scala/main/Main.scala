package main

import org.apache.spark.sql.SparkSession
import scribe.file._

import relatedHashtags.RelatedHashtags
import util.FileUtil


object Main {
  def main(args: Array[String]) = {

    // Log Spark info to logs/app/
    scribe.Logger.root
      .clearHandlers()
      .clearModifiers()
      .withHandler(writer = FileWriter(
        "logs" / "app" / ("app-" % year % "-" % month % "-" % day % ".log")
      ))
    .replace()
    
    // jsonPath currently points to test data
    val jsonPath = "s3://covid-analysis-p3/datalake/twitter-covid/test_twitter_data.json"

    val spark = SparkSession
      .builder()
      .appName("Related-Hashtags")
      .master("yarn")               // Change "yarn" to "local[*]" if running the applicaiton locally.
      .getOrCreate()

    // twitterDF is the base DataFrame created from the contents of an input json file.
    val twitterDF = FileUtil.getDataFrameFromJson(spark, jsonPath)

    RelatedHashtags.getHashtagsWithCovid(spark, twitterDF)
    }
  }