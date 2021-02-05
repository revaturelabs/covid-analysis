import org.apache.spark.sql.SparkSession
import EUSpikes._
object Main {
  def main(args: Array[String]) = {
    val spark = SparkSession.builder()
      .appName("EUSpikes")
      .master("local[4]")
      .getOrCreate()
    spark.sparkContext.setLogLevel("WARN")
    processEUData(spark)
  }
}
