package com.revature.scalawags.project3.stockmarket

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.functions
import org.apache.log4j._
import org.apache.spark.sql.types.DoubleType
import org.apache.spark.sql.types.DateType
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.sql.{DataFrame, Dataset}


object WeeklyChangeRunner{
    def main(args: Array[String]): Unit = {
        Logger.getLogger("org").setLevel(Level.ERROR)

        val spark = SparkSession.builder()
            .appName("weekly_Chnage")
            .master("local[4]")
            .getOrCreate()

        import spark.implicits._

        val africaDF = dataFrameByRegion(spark, "Africa")
        //dataColumnFormating function is only needed for Africa region
        val dateFormatDF = dateColumnFormating(africaDF)
        val sumDFforAfrica = sumOfOpenPrice(dateFormatDF)
        sumDFforAfrica.show(1800, false)


        val asiaDF = dataFrameByRegion(spark, "Asia")
        val sumDFforAsia = sumOfOpenPrice(asiaDF)
        sumDFforAsia.show(1800, false)
    }

    def dataFrameByRegion(spark: SparkSession, region: String): DataFrame = {
        val regionDF = spark
        .read
        .option("inferSchema", "true")
        .option("header", "true")
        .csv(s"/datalake/datalake/${region}_*.csv").cache()
        return regionDF
    }

    def sumOfOpenPrice(df: DataFrame): DataFrame = {
        val dataFormatChangeDF = df.withColumn("Date", to_date(col("Date"), "MM/dd/yyyy"))
        val removeCommaDF = dataFormatChangeDF.withColumn("Open", regexp_replace(col("Open"), ",", ""))
        val dataframe = removeCommaDF.withColumn("Open", col("Open").cast(DoubleType)).select("Date", "Open")
        val sumDF = dataframe.groupBy(col("Date")).agg(sum(col("Open"))).orderBy(desc("Date"))
        return sumDF
    }

    def dateColumnFormating(df: DataFrame): DataFrame ={
        val wordDateFormat = df.withColumn("Date", to_date(col("Date"), "MMM dd, yyyy")).select(col("Date"), col("Open"))
        val dropNullDF = wordDateFormat.na.drop()

        val numDataFormat = df.withColumn("Date", to_date(col("Date"), "MM/dd/yyyy")).select(col("Date"), col("Open"))
        val dropNullDF1 = numDataFormat.na.drop()

        val unionDF = dropNullDF.union(dropNullDF1)
        return unionDF
    }
}