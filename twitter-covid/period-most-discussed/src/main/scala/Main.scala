import org.apache.spark.sql.{DataFrame, SparkSession}
import peakDiscussion._
object Main {
    def main(args: Array[String]): Unit = {
        val spark = SparkSession.builder()
        .appName("peakDiscussion")
        .master("yarn")         // Change "yarn" to "local[*]" if running locally.
        .getOrCreate()
        spark.sparkContext.setLogLevel("WARN")
        configureAWS(spark) //Only necessary when running locally.
        processData(spark)
        spark.stop()
    }
}