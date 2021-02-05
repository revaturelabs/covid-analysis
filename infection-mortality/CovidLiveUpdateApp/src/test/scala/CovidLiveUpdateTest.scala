package covid_live_updates

import CovidLiveUpdates._
import java.io.ByteArrayOutputStream
import org.scalatest.flatspec.AnyFlatSpec
import org.apache.spark.sql.functions._
import org.apache.spark.sql.{SparkSession, Column}


class CovidLiveUpdateTest extends AnyFlatSpec {
  //Create spark session
  val spark = SparkSession
    .builder()
    .appName("Infection-Rates")
    .master("local[4]")
    .getOrCreate()

  // Setting up spark
  spark.sparkContext.setLogLevel("ERROR")
  import spark.implicits._

  "africaTemp" should "read a json" in {
    val africaTemp = spark.read.json( "datalake/CovidLiveUpdate/africa.json" )
  }

  "africaTemp" should "be able to reach the bucket" in {
    val africaTemp = spark.read.json( "s3a://covid-analysis-p3/datalake/infection-mortality/CovidLiveUpdates/africa.json" )
  }

  "africaTemp" should "be queryable" in {
    val africaTemp = spark.read.json( "s3a://covid-analysis-p3/datalake/infection-mortality/CovidLiveUpdates/africa.json" )
    val africaDF = CovidLiveUpdates.settingRegionalDF(spark, africaTemp, "Africa")
  }
}