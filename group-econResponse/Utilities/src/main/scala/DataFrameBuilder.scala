package utilites

import org.apache.spark.sql.functions.{col, explode}
import org.apache.spark.sql.{DataFrame, Encoders, SparkSession}

/** this object filters/joins dataFrames together
 * based on specific columns that are established in the input DF's
 */
class DataFrameBuilder {

  def build(
             spark: SparkSession,
             econPath: String,
             casePath: String
           ): DataFrame = {
    import spark.implicits._

    // val regionDF = spark.read.json("s3a://adam-king-848/data/regionDict.json")
    val regionDF = spark.read.json("src/test/scala/testData/regionDict")

    val dailyCasesSchema = Encoders.product[CountryStats].schema
    val rawDailyCasesData = spark.read
      .format("csv")
      //.option("delimiter", "\t")
      .option("header", "true")
      .schema(dailyCasesSchema)
      .load(casePath)
      .as[CountryStats] //case class for daily cases by country dataset
      .toDF()

    val economicsDataSchema = Encoders.product[EconomicsData].schema
    val rawEconomicsData = spark.read
      .format("csv")
      //.option("delimiter", "\t")
      .option("header", "true")
      .schema(economicsDataSchema)
      .load(econPath)
      .as[EconomicsData] //case class for economy data by country dataset
      .toDF()

    val dailyCases = initDailyCasesDF(spark, rawDailyCasesData, regionDF)
    val economicsData = initEconomicsDF(spark, rawEconomicsData, regionDF)

    val fullDataDF = dailyCases.join(economicsData, Seq("country", "region"))
    fullDataDF
  }

  /** returns a new dataFrame with an appended Region
   * column that maps each 'country' in dF to it's region
   *
   * @param spark    spark session
   * @param regionDF json that contain a map of region- > countries
   * @param dF       dataFrame to append region
   * @return
   */
  def addRegion(
                 spark: SparkSession,
                 regionDF: DataFrame,
                 dF: DataFrame
               ): DataFrame = {
    import spark.implicits._

    val tempRegion = regionDF
      .select($"name" as "region", explode($"countries") as "country")
    //Specify the join column as an array type or string to avoid duplicate columns
    val newDF = dF.join(tempRegion, Seq("country"))
    newDF
  }

  /** filters a dataFrame by sequence of column names provided
   * and replaces null values in numeric columns with 0
   *
   * @param spark spark session
   * @param dF    dataFrame to be filtered
   * @param cols  sequence of column names to filter
   * @return filtered dataFrame
   */
  def filterColumns(
                     spark: SparkSession,
                     dF: DataFrame,
                     cols: Seq[String]
                   ): DataFrame = {
    val newDF = dF.select(cols.map(col): _*)
    newDF.na.fill(0) // replace null with 0
  }

  def initDailyCasesDF(
                        spark: SparkSession,
                        rawCasesDF: DataFrame,
                        regionDF: DataFrame
                      ): DataFrame = {
    val tempDF = filterColumns(
      spark,
      rawCasesDF,
      Seq(
        "date",
        "country",
        "total_cases",
        "total_cases_per_million",
        "new_cases",
        "new_cases_per_million"
      )
    ).na.drop(Seq("date", "country")) // drop rows w/ a null date or country
    addRegion(spark, regionDF, tempDF)
  }

  def initEconomicsDF(
                       spark: SparkSession,
                       rawEconDF: DataFrame,
                       regionDF: DataFrame
                     ): DataFrame = {
    val tempDF = filterColumns(
      spark,
      rawEconDF,
      Seq(
        "name",
        "year",
        "gdp_currentPrices_usd",
        "gdp_perCap_currentPrices_usd",
        "population"
      )
    )
      .withColumnRenamed("name", "country") //rename 'name' field to 'country'
      .filter(col("year") === 2020) //only include annual gdp data from 2020
    addRegion(spark, regionDF, tempDF)
  }
}