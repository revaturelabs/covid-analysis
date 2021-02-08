import org.apache.spark.sql.{DataFrame, SparkSession, functions}

object EUSpikes {

  def processEUData(spark: SparkSession): DataFrame = {
    val df = pullData(spark)
    groupData(spark, filterAgeGroups(spark, df))

  }
  //read to dataframe from s3 bucket
  def pullData(spark: SparkSession): DataFrame = {
//    spark.sparkContext.hadoopConfiguration.set("fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem")
    spark.sparkContext.hadoopConfiguration.set("fs.s3a.endpoint", "s3.amazonaws.com")
    // Set up S3 with secret and access key with spark
    spark.sparkContext.hadoopConfiguration.set("fs.s3a.access.key", sys.env("AWS_ACCESS_KEY_ID"))
    spark.sparkContext.hadoopConfiguration.set("fs.s3a.secret.key", sys.env("AWS_SECRET_ACCESS_KEY"))

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


}
