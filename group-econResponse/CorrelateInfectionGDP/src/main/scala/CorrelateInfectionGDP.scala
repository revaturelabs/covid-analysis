package covidAndGDP

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{DataFrame, SparkSession}
import utilites.{DataFrameBuilder, s3DAO}

/** Question: Is there a significant relationship between a Region’s cumulative GDP and Infection Rate per capita?
  * queries:
  * uses Spark SQL and Spark ML with S3 buckets partitioned by region to query datasets and calculate the Pearson
  * Correlation Coefficient.
  *
  * TODO: Uses Spark ML to preform hypothesis testing on any conclusion drawn from the coefficient value.
  */
object CorrelateInfectionGDP {
  def getPearsonCoefficient(df: DataFrame): Double =
    df.stat.corr("infection_rate", "cumulative_gdp")

  def main(args: Array[String]): Unit = {
    // Set logging level.
    Logger.getLogger("org").setLevel(Level.WARN)

    //Class dependencies and app config.
    val s3 = s3DAO()
    val dfb = new DataFrameBuilder
    s3.setLocalLakePath("CorrelateInfectionGDP/src/main/resources/datalake")
    val fileNames = Map(
      "covidSrc" -> "owid-covid-data.csv",
      "regionSrc" -> "region_dictionary.json",
      "econSrc" -> "economic_data_2018-2021.tsv"
    )

    //Spark setup.
    val spark = SparkSession
      .builder()
      .getOrCreate()

    //Build DF
    val df = dfb.build(spark, fileNames, s3)
    df.createOrReplaceTempView("correlation")

    //format proper table.
    val correlateDF = spark
      .sql("""
        |SELECT AVG(total_cases_per_million) as infection_rate,
        |SUM(gdp_perCap_currentPrices_usd) as cumulative_gdp,
        |region
        |FROM correlation
        |GROUP BY region""".stripMargin)
      .cache()

    println("\nRegional infection rates and cumulative GDP:")
    correlateDF.show()

    println("\nPearson Correlation Coefficient:")
    val pearsonCorrelationCoefficient: Double = getPearsonCoefficient(
      correlateDF)

    println(pearsonCorrelationCoefficient)

    // TODO: Implement a method for hypo testing the correlation results.
    spark.catalog.dropTempView("correlation")
    spark.stop()
  }
}
