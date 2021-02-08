import org.apache.spark.sql.{DataFrame, SparkSession}
import EUSpikes._
object Main {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .appName("EUSpikes")
      .master("local[4]")
      .getOrCreate()
    spark.sparkContext.setLogLevel("WARN")
    configureAWS(spark)
//    processEUData(spark)
    pullTwitterDataDevelopment(spark)
//    makeTestFile(spark)
  }
}
