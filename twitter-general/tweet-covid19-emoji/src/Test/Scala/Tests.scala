package emojis

import org.scalatest.flatspec.AnyFlatSpec
import com.vdurmont.emoji._

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.sql.SparkSession

/**
  * All tests are designed to fail as of now.
  *
  */
class SetSpec extends AnyFlatSpec {

  val spark = SparkSession.builder
      .appName("Tester")
      .master("local[4]")
      .getOrCreate()
    val sc = spark.sparkContext
    sc.setLogLevel("INFO")


  val testPath = "asgdhjasdlaejf"

  //Store the computations to be tested beforehand so we're not re-computing for every single test case.
  val mapToTest = Utilities.tweetcovid19emoji(testPath, sc)

  //The Emoji Count function should be a Map populated with all emojis found in the data, and their respective counts.
  "Utilities" should "Return a non-empty map of emojis and counts" in {
    assert(!mapToTest.isEmpty)
  }

  //The Key values for the EmojiCount map should all be emojis.
  it should "Only contain emojis as keys" in{
    for ((key,value) <- mapToTest) {
      assert(EmojiManager.isEmoji(key))
    }
  }

  it should "Only contain integers as values" in{
    for ((key,value) <- mapToTest) {
      assert(value.isInstanceOf[Int])
    }
  }

  //The EmojiCount Map shold not contain any non-Emoji characters in the Keys. 
  it should "omit any non-emoji characters" in {
    assert(!mapToTest.contains("covid")) //Replace this with a regex encompassing unicode outside Emojis?
  }
}