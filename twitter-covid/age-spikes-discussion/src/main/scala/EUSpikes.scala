import org.apache.spark.sql.{DataFrame, SparkSession, functions}

object EUSpikes {

  def processData(spark: SparkSession): Unit = {
    val twitter = processTwitterData(spark)
    val eu = processEUData(spark)
    twitter.join(eu, twitter("week") === eu(""))
  }

  def processEUData(spark: SparkSession): DataFrame = {
    val df = pullEUData(spark)
    groupData(spark, filterAgeGroups(spark, df))
  }

  def configureAWS(spark: SparkSession) = {
    spark.sparkContext.hadoopConfiguration.set("fs.s3a.endpoint", "s3.amazonaws.com")
    // Set up S3 with secret and access key with spark
    spark.sparkContext.hadoopConfiguration.set("fs.s3a.access.key", sys.env("AWS_ACCESS_KEY_ID"))
    spark.sparkContext.hadoopConfiguration.set("fs.s3a.secret.key", sys.env("AWS_SECRET_ACCESS_KEY"))
  }

  //read to dataframe from s3 bucket
  def pullEUData(spark: SparkSession): DataFrame = {
//    spark.sparkContext.hadoopConfiguration.set("fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem")
//    spark.sparkContext.hadoopConfiguration.set("fs.s3a.endpoint", "s3.amazonaws.com")
//    // Set up S3 with secret and access key with spark
//    spark.sparkContext.hadoopConfiguration.set("fs.s3a.access.key", sys.env("AWS_ACCESS_KEY_ID"))
//    spark.sparkContext.hadoopConfiguration.set("fs.s3a.secret.key", sys.env("AWS_SECRET_ACCESS_KEY"))

    val df = spark.read.option("header", "true").option("inferSchema", "true").csv("s3a://covid-analysis-p3/datalake/twitter-covid/eu_cases_age.csv")
    df.show(100)
    df
  }

  //filter to only include age groups <15 and 15-24
  def filterAgeGroups(spark: SparkSession, df: DataFrame ): DataFrame = {
    import spark.implicits._
    df.filter(df("age_group") === "<15yr" || df("age_group") === "15-24yr").show(100)
//    and df("age_group") === "15-24yr"
    df
  }

  //group by year_week and sum(new_cases)
  //result will have columns: year_week, sum(new_cases)
  def groupData(spark: SparkSession, df: DataFrame): DataFrame = {
    import spark.implicits._
    df.groupBy($"year_week").sum("new_cases").orderBy($"year_week").show()
    df
  }

  def processTwitterData(spark: SparkSession): DataFrame = {
    val df = pullTwitterDataDevelopment(spark)
    val dfWeeks = groupByWeeks(spark, df)
    dfWeeks
  }

  def pullTwitterData(spark: SparkSession): DataFrame = {
    val df = spark.read.option("header", "true").option("inferSchema", "true").option("sep", "\t").csv("s3a://covid-analysis-p3/datalake/twitter-covid/full_dataset_clean.tsv").cache()
    df.show(100)
    df
  }

  def pullTwitterDataDevelopment(spark: SparkSession): DataFrame = {
    val df = spark.read.option("header", "true").option("inferSchema", "true").option("sep", "\t").csv("s3a://covid-analysis-p3/datalake/twitter-covid/twitter-1000.tsv")
    df.show()
    df.printSchema()
    df
  }

  def groupByWeeks(spark: SparkSession, df: DataFrame): DataFrame = {
    import spark.implicits._
    import org.apache.spark.sql.functions._
    spark.conf.set("spark.sql.legacy.timeParserPolicy","LEGACY")
    val dfWithWeek = df.withColumn("input_date", to_date($"date")).withColumn("week", date_format($"date", "w"))
    dfWithWeek.orderBy(desc("date")).show()
    dfWithWeek.printSchema()
    dfWithWeek
  }
}
