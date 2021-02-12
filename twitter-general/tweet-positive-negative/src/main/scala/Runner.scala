import org.apache.spark.sql.{SparkSession, DataFrame}
import org.apache.hadoop.fs._
import com.amazonaws.services.s3.{AmazonS3, AmazonS3ClientBuilder}
import scala.sys.process._

object Runner {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession
      .builder()
      .appName("sentimentAnalysis")
      .master("local[*]")
      .getOrCreate()

    //Connect to s3
    val accessKeyId = sys.env("AWS_ACCESS_KEY_ID")
    val secretAccessKey = sys.env("AWS_SECRET_ACCESS_KEY")
    val s3Endpoint = "s3a://covid-analysis-p3"
    spark.sparkContext.hadoopConfiguration
      .set("fs.s3n.awsAccessKeyid", accessKeyId)
    spark.sparkContext.hadoopConfiguration
      .set("fs.s3n.awsSecretAccessKey", secretAccessKey)

    //grabbing date ranges for analysis
    def getdates: String = {
      val inputFile = args(0).toInt
      inputFile match {
        case 1 =>
          val textFile = "dec_11-dec_25/*"
          textFile
        case 2 =>
          val textFile = "dec_26-jan_05/*"
          textFile
        case 3 =>
          val textFile = "feb_03-feb_14/*"
          textFile
        case 4 =>
          val textFile = "test-data/*"
          textFile
        case _ =>
          val textFile = "not valid input.."
          textFile
      }
    }

    val dateRange = getdates
    val results = SentimentRatio.tweetPositiveNegative(
      spark,
      s"$s3Endpoint/datalake/twitter-general/$dateRange"
    )
    //save results to local as csv and rename for more concise file name 
    //https://stackoverflow.com/questions/41990086/specifying-the-filename-when-saving-a-dataframe-as-a-csv
    val fs = FileSystem.get(spark.sparkContext.hadoopConfiguration)
    results
      .coalesce(1)
      .write
      .mode("overwrite")
      .option("header", "false")
      .csv("resultsTesting")
    val file =
      fs.globStatus(new Path("resultsTesting/part*"))(0).getPath().getName()

    fs.rename(
      new Path("resultsTesting/" + file),
      new Path("resultsTesting/testResults.csv")
    )
    fs.delete(new Path("testResults.csv-temp"), true)

    //to send copy file command to s3
    //https://medium.com/@mcamara89/executing-shell-commands-from-scala-7001f8868128
    val sendToS3 =
      s"aws s3 cp resultsTesting/testResults.csv s3://covid-analysis-p3/datawarehouse/twitter-general/sentiment-analysis/testResults.csv"
    sendToS3.!

    spark.close()
  }
}
