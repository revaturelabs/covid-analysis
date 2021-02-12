package firstRegionPeaks

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import utilites.{DataFrameBuilder, s3DAO}

/** Question: Is there a significant relationship between a Region’s cumulative GDP and Infection Rate per capita?
 * queries:
 * uses Spark SQL and Spark ML with S3 buckets partitioned by region to query datasets and calculate the Pearson
 * Correlation Coefficient.
 *
 * Uses Spark ML to preform hypothesis testing on any conclusion drawn from the coefficient value.
 *
 */
object FirstRegionalPeaks {

  def main(args: Array[String]): Unit = {
    // Set the log level to only print errors
    Logger.getLogger("org").setLevel(Level.WARN)

    val db = s3DAO()
    val dfb = new DataFrameBuilder
    val calc = new Calculator
    db.setDownloadPath("FirstRegionalPeaks/src/main/resources")
    val fileNames = Map(
      "covidSrc" -> "daily_covid_stats.tsv",
      "regionSrc" -> "region_dictionary.json",
      "econSrc" -> "economic_data_2018-2021.tsv"
    )
    val spark = SparkSession.builder()
      .master("local[*]")
      .getOrCreate()

    val df = dfb.build(spark, fileNames, db).cache()

    println("\nRegional time elapsed in days before first major Covid-19 spikes:")
    calc.regionalFirstPeak(spark, df)

    spark.catalog.dropTempView("correlation")
    // TODO: call hypothesis test method when implemented
    //    calc.hypoTest(1.0d, 2.25d)
    spark.stop()
  }
}
