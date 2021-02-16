package firstRegionPeaks

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import utilites.{DataFrameBuilder, s3DAO}

/** Question: What is the average amount of time it took for each region to reach its first peak in infection rate
  * per capita?
  * queries:
  * uses Spark SQL for analytics with S3 buckets partitioned by region to query datasets and calculate the time elapsed.
  *
  */
object FirstRegionalPeaks {

  def main(args: Array[String]): Unit = {
    // Set logging level.
    Logger.getLogger("org").setLevel(Level.WARN)

    //Class dependencies and app config.
    val db = s3DAO()
    val dfb = new DataFrameBuilder
    val calc = new Calculator
    db.setLocalLakePath("FirstRegionalPeaks/src/main/resources/datalake")
    val fileNames = Map(
      "covidSrc" -> "owid-covid-data.csv",
      "regionSrc" -> "region_dictionary.json",
      "econSrc" -> "economic_data_2018-2021.tsv"
    )

    //Spark setup
    val spark = SparkSession
      .builder()
      .getOrCreate()

    //Build df
    val df = dfb.build(spark, fileNames, db)

    //Show Results.
    println(
      "\nRegional time elapsed in days before first major Covid-19 spikes:")
    calc.regionalFirstPeak(spark, df)

    spark.stop()
  }
}
