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

    val africaDF = spark
    .read
    .option("inferSchema", "true")
    .option("header", "true")
    .csv("/datalake/datalake/Africa_*.csv").cache()

    africaDF.show(2000, false)

    val dateFormat1 = africaDF.withColumn("New Date", date_format(to_date($"Date", "MMM dd, yyyy"),"MM/dd/yyyy")).select($"New Date", $"Open")
    val dataFormat2 = africaDF.withColumn("New Date", date_format(to_date($"Date", "MM/dd/yyyy"), "MM/dd/yyyy")).select($"New Date", $"Open")

    val date_f1 = dateFormat1.as("df_form1")
    val date_f2 = dataFormat2.as("df_form2")

    val joined_df = date_f1.join(
        date_f2
        , col("df_form1.New Date") === col("df_form2.New Date")
        , "outer")
    joined_df.show(2000, false)

    val asiaDF = dataFrameByRegion(spark, "Asia")
    dfCalcuation(asiaDF)

    }
    def dataFrameByRegion(spark: SparkSession, region: String): DataFrame = {
        val regionDF = spark
        .read
        .option("inferSchema", "true")
        .option("header", "true")
        .csv(s"/datalake/datalake/${region}_*.csv").cache()
    
        return regionDF
    }

    def dfCalcuation(df: DataFrame){
        //df.printSchema
        // df.show(200, false)
        val dataFormatChangeDF = df.withColumn("Date", to_date(col("Date"), "MM/dd/yyyy"))
        val removeCommaDF = dataFormatChangeDF.withColumn("Open", regexp_replace(col("Open"), ",", ""))
        val dataframe = removeCommaDF.withColumn("Open", col("Open").cast(DoubleType)).select("Date", "Open")
        val sumDF = dataframe.groupBy(col("Date")).agg(sum(col("Open"))).orderBy(desc("Date"))
        sumDF.printSchema
        sumDF.show(2000, false)
    }
    
}