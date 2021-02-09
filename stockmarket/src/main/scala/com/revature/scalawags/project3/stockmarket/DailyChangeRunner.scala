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


object DailyChangeRunner{
    def main(args: Array[String]): Unit = {
        Logger.getLogger("org").setLevel(Level.ERROR)

        val spark = SparkSession.builder()
            .appName("weekly_Chnage")
            .master("local[4]")
            .getOrCreate()

        import spark.implicits._

        dailyChangeRunnerByRegion(spark, "Africa").show(800, false)
        // dailyChangeRunnerByRegion(spark, "Asia").show(800, false)
        // dailyChangeRunnerByRegion(spark, "Europe").show(800, false)
        // dailyChangeRunnerByRegion(spark, "South America").show(800, false)
        // dailyChangeRunnerByRegion(spark, "North America").show(800, false)
        // dailyChangeRunnerByRegion(spark, "Oceania").show(800, false)

    }

    def dataFrameByRegion(spark: SparkSession, region: String): DataFrame = {
        val regionDF = spark
        .read
        .option("inferSchema", "true")
        .option("header", "true")
        .csv(s"/datalake/datalake/${region}_*.csv").cache()

        return regionDF
    }

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

    def dateColumnFormating(df: DataFrame): DataFrame ={
        val wordDateFormat = df.withColumn("Date", to_date(col("Date"), "MMM dd, yyyy")).select(col("Date"), col("Open"))
        val dropNullDF = wordDateFormat.na.drop()

        val numDataFormat = df.withColumn("Date", to_date(col("Date"), "MM/dd/yyyy")).select(col("Date"), col("Open"))
        val dropNullDF1 = numDataFormat.na.drop()

        val unionDF = dropNullDF.union(dropNullDF1)
        return unionDF
    }

    def dailyChangeCalculator(spark: SparkSession, df: DataFrame, region: String): DataFrame ={
        import spark.implicits._

        val windowSpec = Window.orderBy(col("Date"))
        df.withColumn("Daily_Comp_Diff", col(s"${region} Index") - when((lag(s"${region} Index", 1)
            .over(windowSpec)).isNull, 0)
            .otherwise(lag(s"${region} Index", 1)
            .over(windowSpec)))
    }

    def dailyChangeRunnerByRegion(spark: SparkSession, region: String): DataFrame ={
        if(region.equals("Africa")){
            val regionDF = dataFrameByRegion(spark, region)
            val dateFormatDF = dateColumnFormating(regionDF)
            val sumDF = sumOfOpenPrice(dateFormatDF, region)
            val indexDailyChangeDF = dailyChangeCalculator(spark, sumDF, region)
            return indexDailyChangeDF
        }else{
            val regionDF = dataFrameByRegion(spark, region)
            val sumDF = sumOfOpenPrice(regionDF, region)
            val indexDailyChangeDF = dailyChangeCalculator(spark, sumDF, region)
            return indexDailyChangeDF
        }
    }

}