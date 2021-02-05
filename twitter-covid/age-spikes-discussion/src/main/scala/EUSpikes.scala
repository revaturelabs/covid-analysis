import org.apache.spark.sql.{DataFrame, SparkSession, functions}

object EUSpikes {

  def processEUData(spark: SparkSession) = {
    val df = pullData(spark)

  }
  //read to dataframe from s3 bucket
  def pullData(spark: SparkSession): Unit = {
//    spark.sparkContext.hadoopConfiguration.set("fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem")
    spark.sparkContext.hadoopConfiguration.set("fs.s3a.endpoint", "s3.amazonaws.com")
    // Set up S3 with secret and access key with spark
    spark.sparkContext.hadoopConfiguration.set("fs.s3a.access.key", sys.env("AWS_ACCESS_KEY_ID"))
    spark.sparkContext.hadoopConfiguration.set("fs.s3a.secret.key", sys.env("AWS_SECRET_ACCESS_KEY"))

    val df = spark.read.csv("s3a://covid-analysis-p3/datalake/twitter-covid/eu_cases_age.csv")
    df.show(50)
  }

  //filter to only include age groups <15 and 15-24
//  def filterAgeGroups(): DataFrame = {
//
//  }

  //group by year_week and sum(new_cases)
  //result will have columns: year_week, sum(new_cases)
//  def groupData(): DataFrame = {
//
//  }


}
