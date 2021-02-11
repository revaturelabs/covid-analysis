package covid_live_update

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

  //Create data resources

  "africaTemp" should "read a json" in {
    val africaTemp = spark.read.json("src/test/resources/africaTest.json")
    assert(false)
  }

  "africaTemp" should "be queryable" in {
    val africaTemp = spark.read.json("src/test/resources/africaTest.json")
    val africa = africaTemp.select(
            lit("Africa").as("Region"),
            sum("cases") as "Total Cases",
            sum("todayCases") as "Today's Cases",
            bround(sum("todayCases") / sum("cases") * 100, 2) as "Cases Percent Change",
            sum("deaths") as "Total Deaths",
            sum("todayDeaths") as "Today's Deaths",
            bround(sum("todayDeaths") / sum("deaths") * 100, 2) as "Death Percent Change",
            sum("recovered") as "Total Recoveries",
            sum("todayRecovered") as "Today's Recoveries",
            bround(sum("todayRecovered") / sum("recovered") * 100, 2) as "Recoveries Percent Change"
        )
  }
}