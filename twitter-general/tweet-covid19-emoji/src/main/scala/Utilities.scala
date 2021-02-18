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

  /**
    * Function for tallying unicode emojis found in Covid-19 related tweets. 
    * 
    *
    * @param range An integer (1-3) to select one of the three available date ranges for analysis.
    * @param sc Spark context, created in the main method.
    * @return A map of Covid-19 related emojis and their respective counts. 
    */
  def tweetcovid19emoji(range: String, sc: SparkContext): Map[String, Int] = {

    // Grabs covid related terms from Terms.scala, which will be used to identify covid related Tweets.
    val covidTerms = Terms.getCovidTerms

    // Defines the data input path based on user input.
    val path = range match {
      case "1"       => "s3a://covid-analysis-p3/datalake/twitter-general/dec_11-dec_25/*"
      case "2"       => "s3a://covid-analysis-p3/datalake/twitter-general/dec_26-jan_05/*"
      case "3"       => "s3a://covid-analysis-p3/datalake/twitter-general/feb_03-feb_14/*"
      case "test-s3" => "s3a://covid-analysis-p3/datalake/twitter-general/test-data/*"
      case _         => "test-data.txt"
    }

    //Read input(tweets) from text file, count emojis in all tweets, and store emojis mapped to counts as a Map in an RDD
    val map = sc
      //Filter the covid related tweets using Terms.scala 
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

    //Return the map containing results from the RDD.
    return sortedEmojiCountMap
  }

  //Function to print contents of a map, for testing purposes.
  def printMap(counter: Map[String, Int]): Unit = {
    for ((key, value) <- counter) printf("%s: %d\n", key, value)
  }

  /**
    * Function for writing the output Map collection to a csv file
    *
    * @param outputMap The map of emojis and their counts processed be tweetcovid19emoji
    */
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

  /**
    * Function for sending saved CSV file to S3. 
    * TODO: Refactor code so this takes the filename as a parameter. Hardcoding the path is bad practice. 
    *
    * @param range  The range of dates processed, to be used as a filename
    * @param spark  The spark session for communicating with S3(Is this needed?)
    * @return N/A
    */
  def writeOutputToS3(range: String, spark: SparkSession) = {
    // Match case that will be used to decide what the name of the file should be.
    val fileName = range match {
      case "1"       => "EmojiCountResults-Dec_11-Dec_25"
      case "2"       => "EmojiCountResults-Dec_26-Jan_05"
      case "3"       => "EmojiCountResults-Feb_03-Feb_14"
      case "test-s3" => "S3ConnectionTestResults"
      case _         => "EmojiCountResults-TestData"
    }

    val renamedFile = s"mv emojiCountOutput.csv $fileName.csv"
    renamedFile.!

    // Sends our renamed results file to S3 (for local use).
    val sendToS3 =
      s"aws s3 mv $fileName.csv s3://covid-analysis-p3/datawarehouse/twitter-general/emoji-count/$fileName.csv"
    sendToS3.!

  }


    /**
    * Function for tallying unicode emojis found in Covid-19 related tweets. 
    * 
    *
    * @param range An integer (1-3) to select one of the three available date ranges for analysis.
    * @param sc Spark context, created in the main method.
    * @return A map of Covid-19 related emojis and their respective counts. 
    */
  def tweetnoncovid19emoji(range: String, sc: SparkContext): Map[String, Int] = {

    // Defines the data input path based on user input.
    val path = range match {
      case "1"       => "s3a://covid-analysis-p3/datalake/twitter-general/dec_11-dec_25/*"
      case "2"       => "s3a://covid-analysis-p3/datalake/twitter-general/dec_26-jan_05/*"
      case "3"       => "s3a://covid-analysis-p3/datalake/twitter-general/feb_03-feb_14/*"
      case "test-s3" => "s3a://covid-analysis-p3/datalake/twitter-general/test-data/*"
      case _         => "test-data.txt"
    }

    //Read input from text file, count emojis in it, and store as a Map in an RDD
    val map = sc
      .textFile(path)
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


    //Return the map containing results from the RDD.
    return sortedEmojiCountMap

  }
}
