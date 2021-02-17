import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.sql.functions.{count, asc, desc, date_format, concat, lit}
object peakDiscussion {

  /**
    * Simple method for configuring the AWS connection. Only necesarry when running locally.
    *
    * @param spark
    */
  def configureAWS(spark: SparkSession) = {
    spark.sparkContext.hadoopConfiguration.set("fs.s3a.endpoint", "s3.amazonaws.com")
    spark.sparkContext.hadoopConfiguration.set("fs.s3a.access.key", sys.env("AWS_ACCESS_KEY_ID"))
    spark.sparkContext.hadoopConfiguration.set("fs.s3a.secret.key", sys.env("AWS_SECRET_ACCESS_KEY"))
  }

  /**
    * Reads batch data from s3 bucket into dataframe.
    *
    * @param spark
    */
  def pullData(spark: SparkSession): DataFrame = {
    val df = spark.read.option("header", "true").option("delimiter", "\t").option("inferSchema", "true")
    .csv("s3a://covid-analysis-p3/datalake/twitter-covid/full_dataset_clean.tsv")
    df
  }

  /**
    * Takes data from s3 bucket and processes it to create and show 3 dataframes. The first groups by date and displays
    * tweet count for each date. The second groups by month-year and displays tweet count for each month-year. 
    * The third groups by hour and displays tweet count for each hour of the day. Also writes these files to the s3 bucket.
    *
    * @param spark
    */
  def processData(spark: SparkSession): Unit = {
    //Create DF from data on s3 then use methods to create new DF's.
    val df = pullData(spark)
    val dailyCounts = dailyCountsRanked(df, spark)
    val monthlyCounts = monthlyCountsRanked(df, spark)
    val hourlyCounts = hourlyCountsRanked(df, spark)

    //Print DF's to console.
    dailyCounts.show()
    monthlyCounts.show()
    hourlyCounts.show()

    //Save DF's to s3.
    dailyCounts.coalesce(1).write.mode("overwrite").option("header", "true").csv("s3a://covid-analysis-p3/datawarehouse/twitter-covid/period-most-discussed-results/dailyCounts")
    monthlyCounts.coalesce(1).write.mode("overwrite").option("header", "true").csv("s3a://covid-analysis-p3/datawarehouse/twitter-covid/period-most-discussed-results/monthlyCounts")
    hourlyCounts.coalesce(1).write.mode("overwrite").option("header", "true").csv("s3a://covid-analysis-p3/datawarehouse/twitter-covid/period-most-discussed-results/hourlyCounts")
  }

    /**
      * Returns a DataFrame containing the tweet count from each day in the
      * dataframe, sorted in descending order by number of tweets.
      *
      * @param df 
      * @param spark
      */
    def dailyCountsRanked(df: DataFrame, spark: SparkSession): DataFrame = {
        df.groupBy("date")
        .agg(count("*") as "num_tweets")
        .orderBy(desc("num_tweets"))
    }

    /**
      * Returns a DataFrame containing the tweet count from each month in the
      * dataframe, sorted in descending order by number of tweets.
      *
      * @param df
      * @param spark
      */
    def monthlyCountsRanked(df: DataFrame, spark: SparkSession): DataFrame = {
        val newDf = df.withColumn("month", date_format(df.col("date"), "M")).withColumn("year", date_format(df.col("date"), "y")).drop("date")
        newDf.withColumn("month-year", concat(newDf.col("month"), lit('-'), newDf.col("year"))).drop("month").drop("year")
        .groupBy("month-year")
        .agg(count("*") as "num_tweets")
        .orderBy(desc("num_tweets"))
    }

    /**
      * Returns a DataFrame containing the tweet count from each hour in the
      * DataFrame, sorted in descending order by number of tweets.
      *
      * @param df
      * @param spark
      */
    def hourlyCountsRanked(df: DataFrame, spark: SparkSession): DataFrame = {
        df.withColumn("hour", date_format(df.col("time"), "k")).drop("time")
        .groupBy("hour")
        .agg(count("*") as "num_tweets")
        .orderBy(desc("num_tweets"))
    }
}