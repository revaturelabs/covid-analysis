package emojis

import com.vdurmont.emoji._
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession
import scala.collection.JavaConverters._

import java.io._

import scala.collection.Map

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import scala.sys.process._
import java.io.File
import java.io.PrintWriter
import java.io.FileOutputStream
import java.io.FileInputStream
import org.apache.hadoop.fs.FileSystem
import org.apache.hadoop.fs.Path
import scala.collection.immutable.ListMap
import org.apache.spark.sql.Row
import org.apache.spark.sql.Dataset

class Utilities()

object Utilities {

  // case class TweetText(value: String)
  case class TweetText(value: String)

  //Function for counting emojis in a text file. Consider splitting this into several smaller functions for readability.
  def tweetcovid19emoji(range: String): Map[String, Int] = {

    //Create Spark session and context for processing data.
    val spark = SparkSession
      .builder
      .appName("Twitter-Emoji-Analysis")
      .master("local[4]")
      .getOrCreate()
    val sc = spark.sparkContext
    sc.setLogLevel("OFF")

    //Everything below this is legacy code that actually works. 

    //Read input from text file, count emojis in it, and store as a Map in an RDD
    val map = sc
      .textFile("testdata/*")
      //Extract all emojis from each line of the file, and store in a Scala list instead of Java list.
      .flatMap(line => EmojiParser.extractEmojis(line).asScala)
      //map each emoji to a value of 1 per instance found.
      .map(emoji => (emoji, 1))

    //Consolidate each individual emoji count into one row and 'count' the number of instances
    var emojiCounts = map.reduceByKey(_ + _)

    val emojiCountMap = emojiCounts.collectAsMap()
    //Testing purposes: Collect and print the map of counts.
    emojiCounts.foreach(println)

    //Kill the Spark Context.
    sc.stop()

    //Return the map containing results from the RDD.
    return emojiCountMap
  }

  //Function to print contents of a map, for testing purposes.
  def printMap(counter: Map[String, Int]): Unit = {
    for ((key, value) <- counter) printf("%s: %d\n", key, value)
  }

  def writeOutputToCSV(outputMap: Map[String, Int]) = {

    val outputFileName = "emojiCountOutput.csv"
    val printwriter    = new PrintWriter(new File(outputFileName))

    //Write column headers to csv output file
    printwriter.append("Emoji,Count\n")

    //Format the map of emoji's and their counts into a csv string and write to output file.
    for ((key, value) <- outputMap) {
      val csvEntry = (key + "," + value + "\n")
      printwriter.append(csvEntry)
    }
    printwriter.close()
  }
}
