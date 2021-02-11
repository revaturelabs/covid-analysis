package com.revature.scalawags.project3.stockmarket

import org.scalatest.flatspec.AnyFlatSpec
import org.apache.spark.sql.SparkSession
import java.io.File
import org.apache.log4j._
import org.apache.spark.sql.types.DateType
import org.apache.spark.sql.{DataFrame, Dataset, Column, Row}
import org.apache.spark.sql.functions._
import org.apache.spark.sql.expressions._

class DailyChangeRunnerTest extends AnyFlatSpec {
    Logger.getLogger("org").setLevel(Level.ERROR)
    val spark = SparkSession.builder()
            .appName("composite_test")
            .master("local[4]")
            .getOrCreate()
    import spark.implicits._

    // We need our data source to contain data
    "datalake" should "not be empty" in {
        val datalakeDir =  new File("../stockmarket-data/datalake")
        assert(datalakeDir.isDirectory() && datalakeDir.list().length > 0)
    }

    // We need the dataframes to contain data to function as intended
    "dataFrameByRegion" should "return a nonempty dataframe" in {
        assert(DailyChangeRunner.dataFrameByRegion(spark, "Europe").rdd.isEmpty == false)
    }
    
    //brutal practice
    // Make sure the dates are converted into date objects
    "dateColumnFormating" should "cause different date strings to end as a Date with africa data" in {
        DailyChangeRunner.dateColumnFormating(DummyData.africaDF).select($"Date").take(6).map((x) => assert(x(0).isInstanceOf[java.sql.Date]))
    }

    // Make sure the dates are converted into date objects
    "dateColumnFormating" should "cause different date strings to end as a Date with all data" in {
        DailyChangeRunner.dateColumnFormating(DummyData.theRestRegionsDF).select($"Date").take(6).map((x) => assert(x(0).isInstanceOf[java.sql.Date]))
    }

    // Make sure that the number of rows is as expected
    "sumOfOpenPrice" should "remove duplicate rows in the Date column" in {
        val df = DailyChangeRunner.sumOfOpenPrice(DailyChangeRunner.dateColumnFormating(DummyData.africaDF), "Africa")
        assert(DailyChangeRunner.dateColumnFormating(DummyData.africaDF).count() == 6 && df.count() == 3)
    }

    // Ensure the percent is calculated as expected
    "dailyChangeCalculator" should "calculate daily percentage change in a Region Index column" in {
        val df = DailyChangeRunner.sumOfOpenPrice(DailyChangeRunner.dateColumnFormating(DummyData.africaDF), "Africa")
        assert(DailyChangeRunner.dailyChangeCalculator(spark, df, "Africa").select($"Percentage_Change").take(3)(1)(0) == 0.96)
    }
    
    // Runs the vast majority of the program
    "dailyChangeRunnerByRegion" should "not crash" in {
        DailyChangeRunner.dailyChangeRunnerByRegion(spark, "Europe")
    }
}


object DummyData {
  val spark = SparkSession.builder()
            .appName("composite_test")
            .master("local[4]")
            .getOrCreate()
  import spark.implicits._
  val africaDF = Seq(
    new TestSchemaForAfrica("Feb 03, 2021","135.15","137.77","135.15","137.20","-","1.52%"),
    new TestSchemaForAfrica("Feb 02, 2021","134.94","135.53","134.94","135.15","-","0.16%"),
    new TestSchemaForAfrica("Feb 01, 2021","133.82","135.72","133.82","134.94","-","0.84%"),
    new TestSchemaForAfrica("02/03/2021","343.82","343.82","343.82","343.82", null, null),
    new TestSchemaForAfrica("02/02/2021","341.02","341.02","341.02","341.02", null, null),
    new TestSchemaForAfrica("02/01/2021","337.62","337.62","337.62","337.62", null, null)
  ).toDF()
  val theRestRegionsDF = Seq(
    new TestSchemaForTheRest("02/03/2021","3,531.15","3,544.01","3,508.51","3,517.31"),
    new TestSchemaForTheRest("02/02/2021","3,510.81","3,535.50","3,495.57","3,533.68"),
    new TestSchemaForTheRest("02/01/2021","3,477.17","3,506.39","3,469.88","3,505.28"),
    new TestSchemaForTheRest("02/03/2021","658.22","659.16","651.49","654.89"),
    new TestSchemaForTheRest("02/02/2021","651.34","654.58","650.88","652.87"),
    new TestSchemaForTheRest("02/01/2021","642.66","648.88","642.22","647.34")
  ).toDF()
}

case class TestSchemaForAfrica(date: String, open: String, low: String, high: String, close: String, vol: String, change: String)
case class TestSchemaForTheRest(date: String, open: String, low: String, high: String, close: String)