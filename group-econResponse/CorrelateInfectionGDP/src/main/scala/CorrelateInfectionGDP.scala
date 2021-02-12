package covidAndGDP

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{Row, SparkSession}
import utilites.{DataFrameBuilder, s3DAO}
import org.apache.spark.ml
import org.apache.spark.ml.linalg.Vectors
import org.apache.spark.mllib.stat.Statistics
import org.apache.spark.mllib.stat.test.ChiSqTestResult

/** Question: Is there a significant relationship between a Region’s cumulative GDP and Infection Rate per capita?
 * queries:
 * uses Spark SQL and Spark ML with S3 buckets partitioned by region to query datasets and calculate the Pearson
 * Correlation Coefficient.
 *
 * Uses Spark ML to preform hypothesis testing on any conclusion drawn from the coefficient value.
 *
 */
object CorrelateInfectionGDP {


  def main(args: Array[String]): Unit = {
    // Set the log level to only print errors
    Logger.getLogger("org").setLevel(Level.WARN)

    val db = s3DAO()
    val dfb = new DataFrameBuilder
    val calc = new Calculator
    db.setDownloadPath("CorrelateInfectionGDP/src/main/resources")
    val fileNames = Map(
      "covidSrc" -> "daily_covid_stats.tsv",
      "regionSrc" -> "region_dictionary.json",
      "econSrc" -> "economic_data_2018-2021.tsv"
    )
    val spark = SparkSession.builder()
      .master("local[*]")
      .getOrCreate()

    val df = dfb.build(spark, fileNames, db).cache()
    df.createOrReplaceTempView("correlation")

//    val correlateDF = spark.sql(
//      """
//        |SELECT AVG(total_cases_per_million) as infection_rate,
//        |SUM(gdp_perCap_currentPrices_usd) as cumulative_gdp,
//        |region
//        |FROM correlation
//        |GROUP BY region"""
//        .stripMargin)
//
//
//    println("\nRegional infection rates and cumulative GDP:")
//    correlateDF.show()

//    println("\nPearson Correlation Coefficient:")
//    val pearsonCorrelation: Double = correlateDF.stat.corr("infection_rate", "cumulative_gdp")
//    println(pearsonCorrelation)

//    val corrRDD = correlateDF.rdd
//    val infectionVector = correlateDF.select("infection_rate").rdd.map { case Row(v: Vector[Double]) => v }
//    val gdpVector = correlateDF.select("cumulative_gdp").rdd.map { case Row(v: Vector[Double]) => v }
//
//    val chiSqTestResult = Statistics.chiSqTest()

calc.regionFirstPeak(spark, df, "CorrelateInfectionGDP/src/main/resources")

    spark.catalog.dropTempView("correlation")
    // TODO: call hypothesis test method when implemented
//    calc.hypoTest(1.0d, 2.25d)
  }
}
