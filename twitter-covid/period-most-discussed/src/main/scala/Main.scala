import org.apache.spark.sql.{DataFrame, SparkSession}
import peakDiscussion._
object Main {
    def main(args: Array[String]): Unit = {
        val spark = SparkSession.builder()
        .appName("peakDiscussion")
        .master("yarn") //Switch to "yarn" for running on EMR, "*" for local.
        .getOrCreate()
        spark.sparkContext.setLogLevel("WARN")
        configureAWS(spark) //Only necessary when running locally.
        processData(spark)
        spark.stop()
    }
}