import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._

object TwitterCovidAnalysis {

  /** Simple function to read data from s3 bucket.
    *
    * @param spark
    * @param path
    */
  def readToDF(spark: SparkSession, path: String): DataFrame = {
    spark.read
      .format("csv")
      .option("delimiter", ",")
      .option("quote", "")
      .option("header", "true")
      .option("inferSchema", "true")
      .load(path)
      .cache
  }

  /**Groups dataframe by day
   * 
   * @param df
   */  
  def groupByDate(df: DataFrame): DataFrame = {
    val spark = SparkSession.builder().getOrCreate()
    import spark.implicits._ 
    df.select("Specimen Collection Date", "New Confirmed Cases")
      .groupBy("Specimen Collection Date")
      .sum("New Confirmed Cases")
      .orderBy($"Specimen Collection Date".asc)
  }

  /** Groups dataframe by age groups.
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
