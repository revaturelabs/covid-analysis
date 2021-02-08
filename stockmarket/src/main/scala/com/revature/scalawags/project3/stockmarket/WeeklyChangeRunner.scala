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
        val dateFormatDF = dateColumnFormating(africaDF)
        //dateFormatDF.show(1800, false)


        // val asiaDF = dataFrameByRegion(spark, "Asia")
        // val sumDF = sumOfOpenPrice(asiaDF)
        // sumDF.show(2000, false)
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

    def dateColumnFormating(df: DataFrame){
        val wordDateFormat = df.withColumn("Word_Date", to_date(col("Date"), "MMM dd, yyyy")).select(col("Word_Date"), col("Open"))
        // val dataFormat2 = df.withColumn("New Date", date_format(to_date(col("Date"), "MM/dd/yyyy"), "MM/dd/yyyy")).select("New Date", "Open")
        //wordDateFormat.printSchema
        //return wordDateFormat1

        val numDataFormat = df.withColumn("Num_Date", to_date(col("Date"), "MM/dd/yyyy")).select(col("Date"), col("Open"))
        //numDataFormat.printSchema
        return numDataFormats

        // val date_f1 = dateFormat1.as("df_form1")
        // val date_f2 = dataFormat2.as("df_form2")

        // val joined_df = date_f1.join(
        // date_f2
        // , col("df_form1.New Date") === col("df_form2.New Date")
        // , "outer")
        // joined_df.show(2000, false)

    }
}