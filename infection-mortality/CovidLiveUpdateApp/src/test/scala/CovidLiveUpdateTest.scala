package covid_live_update

import java.io.ByteArrayOutputStream
import org.scalatest.flatspec.AnyFlatSpec
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{SparkSession, Column}
import covid_live_updates.CovidLiveUpdates


class CovidLiveUpdateTest extends AnyFlatSpec {
  //Create spark session
  val spark = SparkSession
    .builder()
    .appName("CovidLiveUpdateTest")
    .master("local[4]")
    .getOrCreate()

  // Setting up spark
  spark.sparkContext.setLogLevel("ERROR")
  import spark.implicits._

  "africaTemp" should "read a json" in {
    val africaTemp = spark.read.option("multiLine", true).json("src/test/resources/africaTest.json")
  }

  "regionalTotal" should "have a length of 10" in {
    val africaTemp = spark.read.option("multiLine", true).json("src/test/resources/africaTest.json")
    val africaDF = CovidLiveUpdates.regionalTotal(spark, africaTemp, "Africa")

    assert(africaDF.first().length == 10)
  }
  it should "sum the entire dataframe" in {
    val africaTemp = spark.read.option("multiLine", true).json("src/test/resources/africaTest.json")
    val africaDF = CovidLiveUpdates.regionalTotal(spark, africaTemp, "Africa")

    assert(africaDF.first().toSeq == Seq("Africa", 20, 20, 100.0, 20, 20, 100.0, 20, 20, 100.0))
  }

  "dataProcessing" should "sum the entire dataframe" in {
    val africaTemp = spark.read.option("multiLine", true).json("src/test/resources/africaTest.json")
    val africaDF = CovidLiveUpdates.regionalTotal(spark, africaTemp, "Africa")
    val totalledDF = CovidLiveUpdates.dataProcessing(spark, africaDF.union(africaDF))

    assert(totalledDF.filter($"Region" === "Total").first.toSeq == Seq("Total", 40, 40, 100.0, 40, 40, 100.0, 40, 40, 100.0))
  }
}