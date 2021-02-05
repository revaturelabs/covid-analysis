package covid_live_updates

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import org.apache.spark.sql.{SparkSession, DataFrame}
import org.apache.spark.sql.functions._

/**
  * 
  */
object CovidLiveUpdates {

  /** Process regional data from files that are updated every 10 minutes.
    * Call regional_live_update.sh every 10 minutes to update the regional files.
    *
    * @param args If s3 bucket is needed then: AccessKey SecretKey
    */
  def main(args: Array[String]): Unit = {

    // Set s3 bucket if it is there
    var datalakeFilePath = ""
    var datawarehouseFilePath = ""

    // Setting up spark
    val spark = SparkSession.builder()
        .appName("CovidLiveUpdates")
        .master("local[4]")
        .getOrCreate()

    // Setting path to local 
    if( args.length == 1 && ( args(0) == "-l" || args(0) == "-local" ) ){
      datalakeFilePath = "datalake/CovidLiveUpdates"
      datawarehouseFilePath = "datawarehouse/CovidLiveUpdates"
    
    // AWS Access and secret key as environment variables
    } else if(!sys.env.contains("AWS_ACCESS_KEY_ID") || !sys.env.contains("AWS_SECRET_ACCESS_KEY")){
      System.err.println("EXPECTED 2 ENVIRONMENT VARIABLES: AWS Access Key and AWS Secret Key")
      spark.close()
      System.exit(1)
    
    } else {
      // File path for datalake and datawarehouse to grab or put
      datalakeFilePath = "s3a://covid-analysis-p3/datalake/infection-mortality/"
      datawarehouseFilePath = "s3a://covid-analysis-p3/datawarehouse/infection-mortality/"

      // Configured needed for AWS s3a
      spark.sparkContext.hadoopConfiguration.set("fs.s3a.impl", "org.apache.hadoop.fs.s3a.S3AFileSystem")

      // Set up S3 with secret and access key with spark
      spark.sparkContext.hadoopConfiguration.set("fs.s3a.awsAccessKeyId", sys.env("AWS_ACCESS_KEY_ID"))
      spark.sparkContext.hadoopConfiguration.set("fs.s3a.awsSecretAccessKey", sys.env("AWS_SECRET_ACCESS_KEY"))
    }
    
    // Set log level for sbt shell
    spark.sparkContext.setLogLevel("ERROR")

    // Set Variable Timestamp for Batch Number
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    var x = LocalDateTime.now().format(formatter)

    // Grabs current time
    val milliseconds = System.currentTimeMillis();
    x = LocalDateTime.now().format(formatter)

    // Prints the current time
    println(s"\nLast Updated: ${x}")
    println("==================================")

    // Grab data from path here
    val africaTemp = spark.read.json( datalakeFilePath + "CovidLiveUpdates/africa.json" )
    val asiaTemp = spark.read.json( datalakeFilePath + "CovidLiveUpdates/asia.json" )
    val caribbeanTemp = spark.read.json( datalakeFilePath + "CovidLiveUpdates/caribbean.json")
    val centralAmericaTemp = spark.read.json( datalakeFilePath + "CovidLiveUpdates/central_america.json")
    val europeTemp = spark.read.json( datalakeFilePath + "CovidLiveUpdates/europe.json")
    val northAmericaTemp = spark.read.json( datalakeFilePath + "CovidLiveUpdates/north_america.json")
    val oceaniaTemp = spark.read.json( datalakeFilePath + "CovidLiveUpdates/oceania.json")
    val southAmericaTemp = spark.read.json( datalakeFilePath + "CovidLiveUpdates/south_america.json")

    // Creates the DFs for the Regions
    val africaDF = regionalTotal(spark, africaTemp, "Africa")
    val asiaDF = regionalTotal(spark, asiaTemp, "Asia")
    val caribbeanDF = regionalTotal(spark, caribbeanTemp, "Caribbean")
    val centralAmericaDF = regionalTotal(spark, centralAmericaTemp, "Central America")
    val europeDF = regionalTotal(spark, europeTemp, "Europe")
    val northAmericaDF = regionalTotal(spark, northAmericaTemp, "North America")
    val oceaniaDF = regionalTotal(spark, oceaniaTemp, "Oceania")
    val southAmericaDF = regionalTotal(spark, southAmericaTemp, "South America")

    // Union to combine all regional DataFrames into global DataFrame
    val regionsDF = africaDF.union(asiaDF)
      .union(caribbeanDF)
      .union(centralAmericaDF)
      .union(europeDF)
      .union(northAmericaDF)
      .union(oceaniaDF)
      .union(southAmericaDF)
      .sort(desc("Cases Percent Change"))

    // Process the DF into a single Region
    val regionTotalDF = dataProcessing(spark, regionsDF)

    // Write to CLI
    regionTotalDF.show()

    // Write to path link using datawarehouse totals
    regionTotalDF.coalesce(1).write.mode("overwrite").option("header","true").csv(datawarehouseFilePath + "/CovidLiveUpdateApp/total")

    // Closes the spark session
    spark.close()
  }



  /** Creates and returns a dataframe after it calculates it's countries in it's regions.
    *
    * @param spark SparkSession for this application.
    * @param jsonTemp DataFrame of the region with all the countries in the region.
    * @param regional String specifying the region it is coming from.
    */
  def regionalTotal(spark: SparkSession, jsonTemp: DataFrame, regional: String ):DataFrame = {

    // Spark SQL to select proper columns from each DF and save as new DF
    jsonTemp.select(
      lit( regional ).as("Region"),
      sum("cases") as "Total Cases",
      sum("todayCases") as "Today's Cases",
      bround(sum("todayCases") / sum("cases") * 100, 2) as "Cases Percent Change",
      sum("deaths") as "Total Deaths",
      sum("todayDeaths") as "Today's Deaths",
      bround(sum("todayDeaths") / sum("deaths") * 100, 2) as "Death Percent Change",
      sum("recovered") as "Total Recoveries",
      sum("todayRecovered") as "Today's Recoveries",
      bround(sum("todayRecovered") / sum("recovered") * 100, 2) as "Recoveries Percent Change"
    )
  }



  /** Processes data and performs union to create single regions DataFrame.
    *
    * @param spark SparkSession for this application.
    * @param regionsDF DataFrame used to find total values
    */
  def dataProcessing(spark: SparkSession, regionsDF: DataFrame ):DataFrame = {

    //Totals all the DFs of their region
    val totals = regionsDF.select(
      lit("Total").as("Region"),
      sum("Total Cases"),
      sum("Today's Cases"),
      bround(avg("Cases Percent Change"), 2),
      sum("Total Deaths"),
      sum("Today's Deaths"),
      bround(avg("Death Percent Change"), 2),
      sum("Total Recoveries"),
      sum("Today's Recoveries"),
      bround(avg("Recoveries Percent Change"), 2)
    )

    //Adds totals to the bottom and returns the DataFrame
    regionsDF.union(totals)
  }

}
