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

    //for potential skinny jar 
    // spark.sparkContext.addJar("https://repo1.maven.org/maven2/com/fasterxml/jackson/module/jackson-module-scala_2.11/2.8.1/jackson-module-scala_2.11-2.8.1.jar")
    // spark.sparkContext.addJar("https://repo1.maven.org/maven2/com/fasterxml/jackson/core/jackson-databind/2.8.1/jackson-databind-2.8.1.jar")
    // spark.sparkContext.addJar("https://repo1.maven.org/maven2/org/apache/hadoop/hadoop-aws/3.2.0/hadoop-aws-3.2.0.jar")
    // spark.sparkContext.addJar("https://repo1.maven.org/maven2/com/amazonaws/aws-java-sdk-bundle/1.11.375/aws-java-sdk-bundle-1.11.375.jar")
    // spark.sparkContext.addJar("https://repo1.maven.org/maven2/org/apache/hadoop/hadoop-hdfs/3.2.0/hadoop-hdfs-3.2.0.jar")
    // spark.sparkContext.addJar("https://repo1.maven.org/maven2/org/apache/hadoop/hadoop-common/3.2.0/hadoop-common-3.2.0.jar")
    // spark.sparkContext.addJar("https://repo1.maven.org/maven2/com/johnsnowlabs/nlp/spark-nlp_2.11/2.7.3/spark-nlp_2.11-2.7.3.jar")

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
          val filePath = "dec_11-dec_25/*"
          filePath
        case 2 =>
          val filePath = "dec_26-jan_05/*"
          filePath
        case 3 =>
          val filePath = "feb_03-feb_14/*"
          filePath
        case 4 =>
          val filePath = "test-data/*"
          filePath
        case _ =>
          val filePath = "not valid input.."
          filePath
      }
    }
    //for writing to s3 in the correct folder
    def getResultsPath: String = {
      val inputFile = args(0).toInt
      inputFile match {
        case 1 =>
          val resultsPath = "SentimentResults-dec_11-dec_25"
          resultsPath
        case 2 =>
          val resultsPath = "SentimentResults-dec_26-jan_05"
          resultsPath
        case 3 =>
          val resultsPath = "SentimentResults-feb_03-feb_14"
          resultsPath
        case 4 =>
          val resultsPath = "SentimentResults-test-data"
          resultsPath
        case _ =>
          val resultsPath = "not valid input.."
          resultsPath
      }
    }

    val filePath = getdates
    val resultsPath = getResultsPath
    val results = SentimentRatio.tweetPositiveNegative(
      spark,
      s"$s3Endpoint/datalake/twitter-general/$filePath"
    )
    //save results to local as csv and rename for more concise file name 
    //https://stackoverflow.com/questions/41990086/specifying-the-filename-when-saving-a-dataframe-as-a-csv
    val fs = FileSystem.get(spark.sparkContext.hadoopConfiguration)
    results
      .coalesce(1)
      .write
      .mode("overwrite")
      .option("header", "false")
      .csv("sentimentResults")
    val file =
      fs.globStatus(new Path("sentimentResults/part*"))(0).getPath().getName()

    fs.rename(
      new Path("sentimentResults/" + file),
      new Path(s"sentimentResults/$resultsPath.csv")
    )
    // fs.delete(new Path("testResults.csv-temp"), true)

    //to send copy file command to s3
    //https://medium.com/@mcamara89/executing-shell-commands-from-scala-7001f8868128
    // val sendToLocal = s"hdfs dfs -get resultsTesting/$resultsPath.csv $resultsPath.csv"
    val sendToS3 =
      s"aws s3 cp sentimentResults/$resultsPath.csv s3://covid-analysis-p3/datawarehouse/twitter-general/sentiment-analysis/$resultsPath.csv"
    // sendToLocal.!
    sendToS3.!

    spark.close()
  }
}
