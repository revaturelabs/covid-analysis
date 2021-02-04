package infection_rates

import java.io.ByteArrayOutputStream
import org.scalatest.flatspec.AnyFlatSpec
import org.apache.spark.sql.functions.{bround, count, desc, when}
import org.apache.spark.sql.{SparkSession, Column}

//import RegionalInfectionRates/src/main/scala/infection_rates/InfectionRates.scala

class practice_test extends AnyFlatSpec {
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
  InfectionRates.createTodayTable(spark)
  InfectionRates.createYesterdayTable(spark)
  var allData = spark.sql("SELECT * FROM today")

  //checks if json file is created
  // "createJsonFile" should "grab json data from connection and write to file" in {
  //   assert(scala.io.Source("datalake/InfectionRates/today.json").exists)
  // }

  //createTodayTable tests
  "createTodayTable" should "contain Region" in {   
    assert(allData.columns.contains("Region")) 
  }
  "createTodayTable" should "contain country" in {  
    assert(allData.columns.contains("country"))
  }

  //createYesterdayTable tests
  

  //getRegionInfectionRate tests, essentially covidRegionalInfectionRate tests
  // CREATE TEST DATA and assert that our function computes what we know to be right answers
  // "getRegionInfectionRate" should "calculate correct Africa infection rate" in {
  //   val dfAfrica = getRegionInfectionRate(spark, "Africa")
  //   assert(dfAfrica.select("Infection_Rate_Change") == desiredValue)
  // } 
  // "getRegionInfectionRate" should "calculate correct Asia infection rate" in {
  //   val dfAsia = getRegionInfectionRate(spark, "Asia")
  // }
  // "getRegionInfectionRate" should "calculate correct Caribbean infection rate" in {
  //   val dfCaribbean = getRegionInfectionRate(spark, "Caribbean")
  // }
  // "getRegionInfectionRate" should "calculate correct Central America infection rate" in {
  //   val dfCentralAmerica = getRegionInfectionRate(spark, "Central America")
  // }
  // "getRegionInfectionRate" should "calculate correct Europe infection rate" in {
  //   val dfEurope = getRegionInfectionRate(spark, "Europe")
  // }
  // "getRegionInfectionRate" should "calculate correct North America infection rate" in {
  //   val dfNorthAmerica = getRegionInfectionRate(spark, "North America")
  // }
  // "getRegionInfectionRate" should "calculate correct Oceania infection rate" in {
  //   val dfOceania = getRegionInfectionRate(spark, "Oceania")
  // }
  // "getRegionInfectionRate" should "calculate correct South America infection rate" in {
  //   val dfSouthAmerica = getRegionInfectionRate(spark, "South America")
  // }
 

  //covidCountryInfectionRate tests
}
