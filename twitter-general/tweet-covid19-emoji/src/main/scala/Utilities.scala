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
  def tweetcovid19emoji(range: String, sc: SparkContext): Map[String, Int] = {

    // // Grabs covid related terms from Terms.scala, which will be used to identify covid related Tweets.
    val covidTerms = Terms.getCovidTerms

    // // Grabs set of meaningless words from Terms.scala, and then unions them with covidTerms
    // // to create a Set of words that will not be counted in our final results.
    // val otherTerms = Terms.getOtherTerms
    // val ignore     = covidTerms.union(otherTerms)

    // Defines the data input path based on user input.
    val path = range match {
      case "1"       => "s3a://covid-analysis-p3/datalake/twitter-general/dec_11-dec_25/*"
      case "2"       => "s3a://covid-analysis-p3/datalake/twitter-general/dec_26-jan_05/*"
      case "3"       => "s3a://covid-analysis-p3/datalake/twitter-general/feb_03-feb_14/*"
      case "test-s3" => "s3a://covid-analysis-p3/datalake/twitter-general/test-data/*"
      case _         => "test-data.txt"
    }

    

    //Everything below this is legacy code that actually works.

    //Read input from text file, count emojis in it, and store as a Map in an RDD
    val map = sc
      .textFile(path).filter(text => covidTerms.exists(word => {text.toLowerCase.contains(word)}))
      //Extract all emojis from each line of the file, and store in a Scala list instead of Java list.
      .flatMap(line => EmojiParser.extractEmojis(line).asScala)
      //map each emoji to a value of 1 per instance found.
      .map(emoji => (emoji, 1))

    //Consolidate each individual emoji count into one row and 'count' the number of instances
    var emojiCounts = map.reduceByKey(_ + _)

    val emojiCountMap = emojiCounts.collectAsMap()

    //Sort the map in Descending order.
    val sortedEmojiCountMap = ListMap(emojiCountMap.toSeq.sortWith(_._2 > _._2):_*)

    //Testing purposes: Collect and print the map of counts.
    sortedEmojiCountMap.foreach(println)

    

    // Prints output to console to show that data has been analyzed.
    // for (i <- 0 to 10) println(results(i))


    //Return the map containing results from the RDD.
    return sortedEmojiCountMap

    // import spark.implicits._
    // val s3Input = spark.read.text(path).as[TweetText]

    // val covidTweets = s3Input.filter(text => covidTerms.exists(word => {text.value.toLowerCase.contains(word)}))

    // //Read input from text file, count emojis in it, and store as a Map in an RDD
    // val map = sc.textFile(path)
    //   //Extract all emojis from each line of the file, and store in a Scala list instead of Java list.
    //   .flatMap(line => EmojiParser.extractEmojis(line).asScala)
    //   //map each emoji to a value of 1 per instance found.
    //   .map(emoji => (emoji, 1))

    // //Consolidate each individual emoji count into one row and 'count' the number of instances
    // var emojiCounts = map.reduceByKey(_ + _)

    // val emojiCountMap = emojiCounts.collectAsMap()
    // //Testing purposes: Collect and print the map of counts.
    // emojiCounts.foreach(println)

    // //Kill the Spark Context.
    // sc.stop()

    // //Return the map containing results from the RDD.
    // return emojiCountMap
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

  def writeOutputToS3(range: String, spark: SparkSession) = {
    // Match case that will be used to decide what the name of the file should be.
    val fileName = range match {
      case "1"       => "EmojiCountResults-Dec_11-Dec_25"
      case "2"       => "EmojiCountResults-Dec_26-Jan_05"
      case "3"       => "EmojiCountResults-Feb_03-Feb_14"
      case "test-s3" => "S3ConnectionTestResults"
      case _         => "EmojiCountResults-TestData"
    }

    // Renames the file to something meaningful based on the user input.
    // val fs   = FileSystem.get(spark.sparkContext.hadoopConfiguration)
    // val file = fs.globStatus(new Path("emojiCountOutput.csv"))(0).getPath().getName()

    val renamedFile = s"mv emojiCountOutput.csv $fileName.csv"
    renamedFile.!
    // fs.rename(new Path("emojiCountOutput.csv"), new Path(s"$fileName.csv"))

    // Sends our renamed results file to S3 (for local use).
    val sendToS3 =
      s"aws s3 mv $fileName.csv s3://covid-analysis-p3/datawarehouse/twitter-general/emoji-count/$fileName.csv"
    sendToS3.!

    // Sends our renamed results file to S3 from Hadoop.
    // val sendToLocal = s"hdfs dfs -get Results/$fileName.csv $fileName.csv"
    // val sendToS3 = s"aws s3 mv $fileName.csv s3://covid-analysis-p3/datawarehouse/twitter-general/emoji-count/$fileName.csv"
    // sendToLocal.!
    // sendToS3.!
  }
}
