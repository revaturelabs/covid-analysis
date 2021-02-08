package covidAndGDP

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
object CorrelateInfectionGDP {

  def main(args: Array[String]): Unit = {
    val db = s3DAO()
    val result = Results()
    val dfb = new DataFrameBuilder()
    val (covidPath, econPath) = (db.getCovidPath, db.getEconPath)

    val spark = SparkSession.builder()
      .master("local[*]")
      .getOrCreate()

    spark.sparkContext.setLogLevel("WARN")

    val df = dfb.build(db = )

    result.regionalCorrelation(spark, df)

    // TODO: call hypothesis test method when implemented
    result.hypoTest(1.0d, 2.25d)
  }
}
