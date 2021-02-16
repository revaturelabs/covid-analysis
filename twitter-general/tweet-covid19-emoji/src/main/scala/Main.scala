package emojis

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

object Main extends App {
  println("Hello, World!")

  //Create Spark session and context for processing data.
    val spark = SparkSession.builder
      .appName("Twitter-Emoji-Analysis")
      .master("yarn")
      .getOrCreate()
    val sc = spark.sparkContext
    sc.setLogLevel("INFO")

  sc.addJar("https://github.com/vdurmont/emoji-java/releases/download/v5.1.1/emoji-java-5.1.1.jar")
  spark.sparkContext.addJar("https://github.com/vdurmont/emoji-java/releases/download/v5.1.1/emoji-java-5.1.1.jar")

  val cmdInput = args(0)

  val map = Utilities.tweetcovid19emoji(cmdInput, sc)

  Utilities.printMap(map)

  Utilities.writeOutputToCSV(map)

  Utilities.writeOutputToS3(cmdInput, spark)

  //Kill the Spark Context.
  sc.stop()
}