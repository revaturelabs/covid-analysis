import org.apache.spark.sql.{DataFrame, SparkSession}
import EUSpikes._
object Main {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("EUSpikes")
      .master("yarn")           // Change "yarn" to "local[*] if running locally"
      .getOrCreate()
    spark.sparkContext.setLogLevel("WARN")
    configureAWS(spark)
    processData(spark)
    spark.stop
  }
}
