package utilites

import org.apache.spark.sql.functions.{col, explode}
import org.apache.spark.sql.types.{IntegerType, StructType}
import org.apache.spark.sql.{DataFrame, Encoders, SparkSession}

/** this object acquires needed dataframes from AWS s3.
 * It then filters, joins dataFrames together with the specific columns needed for
 * application consumption elsewhere in the project.
 */
class DataFrameBuilder {
/** Interfaces with the dao class to download three full datafromes from s3.
 * Prepares data with schema where needed.
 * It then filters & joins dataFrames together with specific columns needed for application consumption.
 * Returns full dataframe.
 *
 * @param spark   Spark Session
 * @param fileNames  Data object with key value pairs for needed file names on s3
 * @param db   Data access object for interfacing with AWS s3
 * @return full dataframe
 * */
  def build(spark: SparkSession, fileNames: Map[String, String], db: s3DAO): DataFrame = {
    //Build structype schemas from case classes.
    val covidSchema = Encoders.product[CountryStats].schema
    val econSchema = Encoders.product[EconomicsData].schema

    //Callback functions used here to create and return a spark dataframe after download from s3.
    val regionCB = (downloadPath: String) => spark.read.json(downloadPath)
    val covidCB = getCallbackFn(spark, covidSchema)()
    val econCB = getCallbackFn(spark, econSchema)()

    //Download dataframe from s3.
    val regionDF = db.loadDFFromBucket(fileNames("regionSrc"), regionCB)
    val rawCovidDF = db.loadDFFromBucket(fileNames("covidSrc"), covidCB)
    val rawEconDF = db.loadDFFromBucket(fileNames("econSrc"), econCB)

    //Combine with regional df and process for application consumption.
    val dailyCases = initDailyCasesDF(spark, rawCovidDF, regionDF)
    val economicsData = initEconDF(spark, rawEconDF, regionDF).cache()
    val fullDF = dailyCases.join(economicsData, Seq("country", "region"))

    castToInt(fullDF)
  }

/** returns a function that can be used as a callback
 * this callback fn will be used in the DAO to build a spark dataframe when tsv is loaded from s3
 *
 * @param spark    spark session
 * @param schema    schema used in df construction
 * @return callback function
*/
  def getCallbackFn(spark: SparkSession, schema: StructType): () => String => DataFrame = () =>  {
    downloadPath: String => {
      spark.read
        .option("delimiter", "\t")
        .option("header", "true")
        .format("csv")
        .schema(schema)
        .csv(downloadPath)toDF()
    }
  }

  /** Recast a couple columns from double to int
   * Encoding for csv cast Int to Null so we read in as double
   * And here, we recast them to ints.
   *
   * @param df    spark dataframe
   * @return spark dataframe
   */
  def castToInt(df: DataFrame): DataFrame = {
    df.withColumn("tmp", df("year").cast(IntegerType)).drop("year")
      .withColumnRenamed("tmp", "year")
      .withColumn("tmp", df("new_cases").cast(IntegerType)).drop("new_cases")
      .withColumnRenamed("tmp", "new_cases")
      .withColumn("tmp", df("total_cases").cast(IntegerType)).drop("total_cases")
      .withColumnRenamed("tmp", "total_cases")
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
      .na.drop(Seq("region"))
    //Specify the join column as an array type or string to avoid duplicate columns
    dF.join(tempRegion, Seq("country"))
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

  def initEconDF(
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