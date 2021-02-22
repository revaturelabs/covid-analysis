package infection_rates

import java.io.ByteArrayOutputStream
import org.scalatest.flatspec.AnyFlatSpec
import org.apache.spark.sql.functions.{bround, count, desc, when}
import org.apache.spark.sql.{SparkSession, Column}

class practice_test extends AnyFlatSpec {
  //Create spark session
  val spark = SparkSession
    .builder()
    .appName("RegionalInfectionRatesTest")
    .master("local[4]")
    .getOrCreate()

  // Setting up spark
  spark.sparkContext.setLogLevel("ERROR")
  import spark.implicits._
  
  //Create data resources
  InfectionRates.createTodayTable(spark, "src/test/resources/")
  InfectionRates.createYesterdayTable(spark, "src/test/resources/")
  var allData = spark.sql("SELECT * FROM today")
  var allData2 = spark.sql("SELECT * FROM yesterday")

  //createTodayTable tests
  "createTodayTable" should "contain Region" in {   
    assert(allData.columns.contains("Region")) 
  }
  "createTodayTable" should "contain country" in {  
    assert(allData.columns.contains("country"))
  }

  //createYesterdayTable tests
  "createYesterdayTable" should "contain Region" in {   
    assert(allData2.columns.contains("Region")) 
  }
  "createYesterdayTable" should "contain country" in {  
    assert(allData2.columns.contains("country"))
  }


}
