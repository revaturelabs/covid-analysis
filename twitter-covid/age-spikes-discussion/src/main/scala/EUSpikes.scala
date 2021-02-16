import org.apache.spark.sql.{DataFrame, SparkSession, functions}

object EUSpikes {

  /**
   * Pulls from s3 and analyzes EU young demographic weekly cases and Twitter Covid discussion data.
   * @param spark The spark session input
   * @return Unit
   */
  def processData(spark: SparkSession): Unit = {
    val twitter = processTwitterData(spark)
    val eu = processEUData(spark)
    val joined = joinTables(spark, twitter, eu)

    twitter.show()
    eu.show()
    joined.show();
    joined.coalesce(1).write.mode("overwrite").option("header", "true").csv("s3a://covid-analysis-p3/datawarehouse/twitter-covid/eu-twitter-results")
  }

  /**
   * Alternative to processData that pulls smaller dataset for development purposes.
   * @param spark The spark session input
   * @return Unit
   */
  def processDataDev(spark: SparkSession): Unit = {
    val twitter = processTwitterDataDevelopment(spark)
    val eu = processEUData(spark)
    val joined = joinTables(spark, twitter, eu)

    twitter.show()
    eu.show()
    joined.show();

    joined.coalesce(1).write.mode("overwrite").option("header", "true").csv("s3a://covid-analysis-p3/datawarehouse/twitter-covid/eu-twitter-results")
  }

  /**
   * Configure AWS to enable Scala program to read from s3.
   * @param spark The spark session input
   * @return Unit
   */
  def configureAWS(spark: SparkSession) = {
    spark.sparkContext.hadoopConfiguration.set("fs.s3a.endpoint", "s3.amazonaws.com")
    // Set up S3 with secret and access key in spark
    spark.sparkContext.hadoopConfiguration.set("fs.s3a.access.key", sys.env("AWS_ACCESS_KEY_ID"))
    spark.sparkContext.hadoopConfiguration.set("fs.s3a.secret.key", sys.env("AWS_SECRET_ACCESS_KEY"))
  }

  /**
   * Joins input dataframes on columns week and year.
   * @param spark The spark session input
   * @param df1 The first dataframe
   * @param df2 The second dataframe
   * @return Resulting joined dataframe
   */
  def joinTables(spark: SparkSession, df1: DataFrame, df2: DataFrame): DataFrame = {
    df1.join(df2, Seq("week", "year"))
  }



// EU data functions
  /**
   * Pulls from s3 and analyzes EU young demographic weekly cases data.
   * @param spark The spark session input
   * @return Dataframe grouped by... NOT DONE FINISH THIS
   */
  def processEUData(spark: SparkSession): DataFrame = {
    val df = pullEUData(spark)
    val grouped = groupData(spark, filterAgeGroups(spark, df))
    val split = splitYearWeek(spark, grouped)
    val groupYearWeek = EUGroupByWeekYear(spark, split)
    groupYearWeek
  }

  /**
   * Pulls EU data from s3.
   * @param spark The spark session input
   * @return Dataframe of EU data
   */
  def pullEUData(spark: SparkSession): DataFrame = {
    val df = spark.read.option("header", "true").option("inferSchema", "true").csv("s3a://covid-analysis-p3/datalake/twitter-covid/eu_cases_age.csv")
    df
  }

  //filter to only include age groups <15 and 15-24
  /**
   * Filter input dataframe to only include age groups <15yr and 15-24yr.
   * @param spark The spark session input
   * @param df The input dataframe
   * @return Dataframe filtered by <15yr and 15-24yr only
   */
  def filterAgeGroups(spark: SparkSession, df: DataFrame ): DataFrame = {
    df.filter(df("age_group") === "<15yr" || df("age_group") === "15-24yr")
    df
  }

  /**
   * Group input dataframe by "year_week" and sum "new_cases" per "year_week", ordered by "year_week".
   * @param spark The spark session input
   * @param df The input dataframe
   * @return Dataframe grouped by "year_week" with sum of "new_cases" per "year_week"
   */
  def groupData(spark: SparkSession, df: DataFrame): DataFrame = {
    import spark.implicits._
    df.groupBy($"year_week").sum("new_cases").orderBy($"year_week")
    df
  }

  /**
   * Split "year_week" field into "year" and "week" in dataframe.
   * @param spark The spark session input
   * @param df The input dataframe
   * @return Dataframe with separate columns for "year" and "week"
   */
  def splitYearWeek(spark: SparkSession, df: DataFrame): DataFrame = {
    import spark.implicits._
    import org.apache.spark.sql.functions._
    import org.apache.spark.sql.types._

    df.withColumn("_tmp", split($"year_week", "-")).withColumn("year",
      $"_tmp".getItem(0)).withColumn("week", $"_tmp".getItem(1).cast(IntegerType).cast(StringType))
  }

  def EUGroupByWeekYear(spark: SparkSession, df: DataFrame): DataFrame = {
    import spark.implicits._
    import org.apache.spark.sql.types._
    import org.apache.spark.sql.functions._

    val intDF = df.withColumn("new_cases", $"new_cases".cast(IntegerType))
    val grouped = intDF.groupBy($"week", $"year").agg(sum("new_cases").as("new_cases"))
    grouped
  }



// Twitter Functions
  def processTwitterData(spark: SparkSession): DataFrame = {
    val df = pullTwitterData(spark)
    val dfWeeks = splitYearWeekTwitter(spark, df)
    val grouped = twitterGroupByWeekYear(spark, dfWeeks)
    grouped
  }

  def processTwitterDataDevelopment(spark: SparkSession): DataFrame = {
    val df = pullTwitterDataDevelopment(spark)
    val dfWeeks = splitYearWeekTwitter(spark, df)
    val grouped = twitterGroupByWeekYear(spark, dfWeeks)
    grouped
  }

  def pullTwitterData(spark: SparkSession): DataFrame = {
    val df = spark.read.option("header", "true").option("inferSchema", "true").option("sep", "\t").csv("s3a://covid-analysis-p3/datalake/twitter-covid/full_dataset_clean.tsv").cache()
    df
  }

  def pullTwitterDataDevelopment(spark: SparkSession): DataFrame = {
    val df = spark.read.option("header", "true").option("inferSchema", "true").option("sep", "\t").csv("s3a://covid-analysis-p3/datalake/twitter-covid/twitter-1000.tsv")
    df
  }

  def splitYearWeekTwitter(spark: SparkSession, df: DataFrame): DataFrame = {
    import spark.implicits._
    import org.apache.spark.sql.functions._
    import org.apache.spark.sql.types.IntegerType

    spark.conf.set("spark.sql.legacy.timeParserPolicy","LEGACY")
    val dfWithWeek = df.withColumn("input_date", to_date($"date")).withColumn("year", year($"date")).withColumn("week", date_format($"date", "w").cast(IntegerType))
    dfWithWeek.orderBy(desc("date"))
    dfWithWeek
  }

  def twitterGroupByWeekYear(spark: SparkSession, df: DataFrame): DataFrame = {
    import spark.implicits._
    val grouped = df.groupBy($"week", $"year").count().withColumnRenamed("count", "covid_tweets")
    grouped
  }
}
