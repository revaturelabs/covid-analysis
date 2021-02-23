package emojis

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

object Runner {
  def main(args: Array[String]): Unit = {

    val cmdInput = args(0)

    //Create Spark session and context for processing data.
    val spark = SparkSession.builder
      .appName("Twitter-Emoji-Analysis")
      .master("yarn")
      .getOrCreate()
    val sc = spark.sparkContext
    sc.setLogLevel("INFO")

    sc.addJar("https://github.com/vdurmont/emoji-java/releases/download/v5.1.1/emoji-java-5.1.1.jar")

    val covidEmojiMap = Utilities.tweetcovid19emoji(cmdInput, sc)

    Utilities.printMap(covidEmojiMap)
    Utilities.writeOutputToCSV(covidEmojiMap)
    Utilities.writeOutputToS3(cmdInput, spark)

    
    //  - This section will count emojis in all tweets, not just covid. 
    //  - It will overwrite the Covid tracking output, do not run at the same time as tweetcovid19emojis
    
    // val nonCovidMap = Utilities.tweetnoncovid19emoji(cmdInput, sc)
    // Utilities.printMap(nonCovidMap)
    // Utilities.writeOutputToCSV(nonCovidMap)
    // Utilities.writeOutputToS3(cmdInput, spark)

    //Kill the Spark Context.
    sc.stop()
  }
}
