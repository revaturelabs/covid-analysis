import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.SparkSession

object TwitterCovidAnalysis {

  /** Simple function to read data from s3 bucket.
    *
    * @param spark
    * @param path
    */
  def readToDF(spark: SparkSession, path: String): DataFrame = {
    spark.read.csv(path).cache
  }

  def groupByDate(df: DataFrame): DataFrame = {
    df.show(3,false)
    // df.groupBy("Specimen Collection Date")
    // .count()
    df
  }

  /** Groups by age groups.
    * Returns DF of age groups and infection counts.
    *
    * @param df
    */
  def ageGroupsInfectionRate(df: DataFrame): DataFrame = {
    // TO DO
    df
  }

  /** Groups by day with highest spike.
    * Returned columns: Date, infection rate (age 5-30), and Twitter Volume.
    * @param df
    */
  def ageTwitterVolume(df: DataFrame): DataFrame = {
    // TO DO
    df
  }
}
