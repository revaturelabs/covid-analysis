package com.revature.scalawags.project3.stockmarket

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.functions
import org.apache.log4j._
import org.apache.spark.sql.types.DoubleType
import org.apache.spark.sql.types.DateType
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.sql.{DataFrame, Dataset, Column}
import org.apache.spark.sql.expressions._

/**
  * QUESTION: Which regions handled COVID-19 the best, assuming our metrics are the percentage change in Stock Market Composite Index?
  */

object DailyChangeRunner{
    def main(args: Array[String]): Unit = {
        // Setting the log level to Error
        Logger.getLogger("org").setLevel(Level.ERROR)

        // Setting up a new SparkSession
        // Comment out .master("local[4]") because AWS EMR uses master yarn
        val spark = SparkSession.builder()
            .appName("daily_change")
            //.master("local[4]")
            .getOrCreate()

        import spark.implicits._

        // Calling dailyChangeRunnerByRegion
        dailyChangeRunnerByRegion(spark, "Africa").show(800, false)
        dailyChangeRunnerByRegion(spark, "Asia").show(800, false)
        dailyChangeRunnerByRegion(spark, "Europe").show(800, false)
        dailyChangeRunnerByRegion(spark, "South America").show(800, false)
        dailyChangeRunnerByRegion(spark, "North America").show(800, false)
        dailyChangeRunnerByRegion(spark, "Oceania").show(800, false)
        //spark.stop()
    }

  /** Takes in a current SparkSession and a region as parameters,
    * reads datasets from an AWS s3 bucket by each region,
    * converts each region's datasets into a dataframe,
    * and returns the dataframe.
    *
    * @param spark current SparkSession
    * @param region specific region
    */
    def dataFrameByRegion(spark: SparkSession, region: String): DataFrame = {
        val regionDF = spark
        .read
        .option("inferSchema", "true")
        .option("header", "true")
        .csv(s"s3://covid-analysis-p3/datalake/stockmarket-data/${region}_*.csv").cache()
        return regionDF
    }

  /** Takes in a dataframe, whose Date column contains both word dates and number dates, as a parameter,
    * makes a new dataframe that converts String word dates into a date object as a yyyy-MM-dd format,
    * makes another new dataframe that converts String number dates into a date object as a yyyy-MM-dd format,
    * drops any rows that contain a null in the Date column,
    * combines these two dataframe using a union function,
    * and returns the dataframe.
    *
    * @param df DataFrame
    */
    def dateColumnFormating(df: DataFrame): DataFrame ={
        val wordDateFormat = df.withColumn("Date", to_date(col("Date"), "MMM dd, yyyy")).select(col("Date"), col("Open"))
        val dropNullDF = wordDateFormat.na.drop()

        val numDataFormat = df.withColumn("Date", to_date(col("Date"), "MM/dd/yyyy")).select(col("Date"), col("Open"))
        val dropNullDF1 = numDataFormat.na.drop()

        val unionDF = dropNullDF.union(dropNullDF1)
        return unionDF
    }

  /** Takes in a dateColumnFormatted dataframe and a region as parameters,
    * changes all elements in the Data column into a uniform date format,
    * removes a comma from all elements in the Open price column,
    * casts the data type of the Open price column into Double Type,
    * counts how many Open price rows exist on each day,
    * uses the maximum number of Open price rows of each day as the total number of countries in each region,
    * filters out dates that contain fewer counts of Open price rows than the total number of countries in each region,
    * renames the Open column as [Region's name] Index
    * and returns the dataframe.
    *
    * @param df DataFrame
    * @param region Sspecific region
    */
    def sumOfOpenPrice(df: DataFrame, region: String): DataFrame = {
        val dataFormatChangeDF = df.withColumn("Date", to_date(col("Date"), "MM/dd/yyyy"))
        val removeCommaDF = dataFormatChangeDF.withColumn("Open", regexp_replace(col("Open"), ",", ""))
        val dataframe = removeCommaDF.withColumn("Open", col("Open").cast(DoubleType)).select("Date", "Open")
        val countDF = dataframe.groupBy(col("Date"), col("Open")).count()
        val sumDF = countDF.groupBy(col("Date")).agg(round(sum(col("Open")), 2).as(s"${region} Index"), sum(col("count")).as("Numb_Countries")).orderBy(asc("Date"))
        val numbOfCountriesEachRegion = sumDF.agg(max(col("Numb_Countries"))).take(1)(0)(0)
        val filterOutOutlierDF = sumDF.filter(col("Numb_Countries") === numbOfCountriesEachRegion).select(col("Date"), col(s"${region} Index"))

        return filterOutOutlierDF
    }

  /** Takes in a current SparkSession, a dataframe, whose Region Index column contains all the regional countries' Open prices, and a region as parameters,
    * creates a new column that calculates daily differences of the Region Index column,
    * creates a new column that calculates daily percentage changes of the Region Index column,
    * rounds all elements in the Percentage_Change column upto 2 decimal places,
    * and returns the dataframe.
    *
    * @param spark SparkSession
    * @param df DataFrame
    * @param region specific region
    */
    def dailyChangeCalculator(spark: SparkSession, df: DataFrame, region: String): DataFrame ={
        import spark.implicits._

        val windowSpec = Window.orderBy(col("Date"))
        val dailyDifferenceDF = df.withColumn("Daily_Diff", col(s"${region} Index") - lag(col(s"${region} Index"), 1).over(windowSpec))
        val percentageChangeDF = dailyDifferenceDF.withColumn("Percentage_Change", col("Daily_Diff") / lag(col(s"${region} Index"), 1).over(windowSpec) * 100)
        val roundUpto2Decimal = percentageChangeDF.select(col("Date"), col(s"${region} Index"), round(col("Percentage_Change"), 2).as("Percentage_Change"))

        return roundUpto2Decimal
    }

  /** Takes in a current SparkSession, a dailyChangeCalculated dataframe, and a region as parameters,
    * writes the dataframe into a csv file
    * and saves it into an AWS s3 bucket by each region.
    *
    * @param spark current SparkSession
    * @param df DataFrame
    * @param region specific region
    */
    def writeAsCSV(spark: SparkSession, df: DataFrame, region: String){
        df.write
            .mode("overwrite")
            .option("header", "true")
            .csv(s"s3://covid-analysis-p3/datawarehouse/stockmarket/${region}_CompositeIndexDailyChange")
    }

  /** Takes in a current SparkSession and a region as parameters,
    * checks if the region passed is Africa,
    * calls all the necessary methods in order,
    * and returns the dataframe that was created at the end of the process.
    *
    * @param spark current SparkSession
    * @param region specific region
    */
    def dailyChangeRunnerByRegion(spark: SparkSession, region: String): DataFrame ={
        if(region.equals("Africa")){
            val regionDF = dataFrameByRegion(spark, region)
            val dateFormatDF = dateColumnFormating(regionDF)
            val sumDF = sumOfOpenPrice(dateFormatDF, region)
            val indexDailyChangeDF = dailyChangeCalculator(spark, sumDF, region)
            writeAsCSV(spark, indexDailyChangeDF, region)
            return indexDailyChangeDF
        }else{
            val regionDF = dataFrameByRegion(spark, region)
            val sumDF = sumOfOpenPrice(regionDF, region)
            val indexDailyChangeDF = dailyChangeCalculator(spark, sumDF, region)
            writeAsCSV(spark, indexDailyChangeDF, region)
            return indexDailyChangeDF
        }
    }
}