package com.revature.scalawags.project3.stockmarket

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.spark.sql.functions
import org.apache.log4j._
import org.apache.spark.sql.types.DoubleType

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

    }
    def dataFrameByRegion(spark: SparkSession, region: String){
        val regionDF = spark
        .read
        .option("inferSchema", "true")
        .option("header", "true")
        .csv(s"/datalake/datalake/${region}_*.csv").cache()

        val regionDF1 = asiaDF.withColumn("Open", $"Open".cast(DoubleType)).select("Date", "Open")
        asiaDF1.show(2000, false)
        asiaDF1.groupBy($"Date").agg(sum($"Open"))

    }
    
}