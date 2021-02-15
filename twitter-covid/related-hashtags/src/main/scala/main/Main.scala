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
    
    // jsonPath points to your data source
    val jsonPath = "s3://covid-analysis-p3/datalake/twitter-covid/01-31-21-twitter_data.json"

    val spark = SparkSession
      .builder()
      .appName("Related-Hashtags")
      .master("yarn")               // Change "yarn" to "local[*]" if running the application locally.
      .getOrCreate()

    // Load in AWS credientials from environment, uncomment these lines if running locally
    // spark.sparkContext.hadoopConfiguration.set("fs.s3a.awsAccessKeyId", sys.env("AWS_ACCESS_KEY_ID"))
    // spark.sparkContext.hadoopConfiguration.set("fs.s3a.awsSecretAccessKey", sys.env("AWS_SECRET_ACCESS_KEY"))
    // spark.sparkContext.hadoopConfiguration.set("fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem")

    // twitterDF is the base DataFrame created from the contents of an input json file.
    val twitterDF = FileUtil.getDataFrameFromJson(spark, jsonPath)

    RelatedHashtags.getHashtagsWithCovid(spark, twitterDF)

    spark.stop
  }
}
    