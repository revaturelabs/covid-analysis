package emojis

import com.vdurmont.emoji._
import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

import scala.collection.Map

class Utilities()


/**
  * TODO: 
  * Process all lines of text at once, instead of running individual emojiCount functions on each line respectively.
  * All the counts need to be stored in a single collection, right now I'm getting an independent collection for each line.
  * Currently several half-finished methods, one that processes data the right way but gets the wrong results
  * One that processes data the wrong way but gets the right results.
  */

object Utilities {

  //Function for counting emojis in a text file. Consider splitting this into several smaller functions for readability. 
  def tweetcovid19emoji(path: String): Map[String, Int] = {

    //Create Spark session and context for processing data.
    val spark = SparkSession
      .builder()
      .appName("Twitter-Emoji-Analysis")
      .master("local[1]")
      .getOrCreate()
    val sc = spark.sparkContext
    sc.setLogLevel("OFF")

    //Read input from text file, count emojis in it, and store as a Map in an RDD
    // val map = sc.textFile("test-data.txt")
    //     .flatMap(line => line.split(" "))
    //     .map(word => (word,1))


    val lines = sc.textFile("test-data.txt")

    for(line <- lines) {
      println("Testing EmojiParser: " + EmojiParser.extractEmojis(line))
    }
    println("Testing sc output: " + lines)
    
    //Convert the RDD into a map
    // val emojiCounts = map.reduceByKey(_ + _).collectAsMap()

    //Kill the Spark Context.
    sc.stop()

    val emojiCounts = Map("test" -> 7)
    //Return the map containing results from the RDD. 
    return emojiCounts
  }


  //Function to print contents of a map, for testing purposes. 
  def printMap(counter: Map[String, Int]): Unit = {
    for ((key, value) <- counter) printf("%s: %d\n", key, value)
  }
}
