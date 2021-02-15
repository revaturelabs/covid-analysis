package firstRegionPeaks
import java.text.SimpleDateFormat
import java.util.Calendar

import org.apache.spark.sql.{DataFrame, SparkSession}

import scala.collection.mutable.ArrayBuffer

/** Question8
  * Currently defunct, but meant to query:
  *     "first peak for each country in region and gdp"
  * using spark SQL on S3 buckets
  * 
  */
case class Calculator() {
  /**
   * Gives the first peak that satisfies the inputs and allows for ignoring noise
   * @param xArray the independent data series
   * @param yArray the dependent data series
   * @param neighbors number of data points to evaluate after a potential peak
   * @param percentDifference percentage to filter out noise where noise is difference between peak and avg value of neighbors
   * @param minCasePercent floor to start evaluating values with respect to maximum value percentage
   * @return a first peak coordinate that satisfies conditions
   */
  def firstMajorPeak(xArray: Array[Double], yArray: Array[Double], neighbors: Int, percentDifference: Double, minCasePercent: Double): (Double, Double) ={
    var avgSum: Double = 0.0
    var sum: Double = 0.0
    var minDifference: Double = 0.0
    val minCase = minCasePercent*.01*yArray.max
    val start = yArray.indexWhere(_ > minCase)
    if (start != -1) {
      for(i <- start until xArray.length - neighbors){
        sum = 0.0
        for(neighbor <- 1 to neighbors){
          sum += yArray(i + neighbor)
        }
        avgSum = sum/neighbors
        minDifference = .01*percentDifference*yArray(i)
        if(yArray(i) - avgSum > minDifference){
          return (xArray(i),yArray(i))
        }
      }
    }
    (-1, yArray(0))
  }

  def dayInYear(date: String, firstOfYear: Long = 1577865600000L): Int ={
    val dateFormat = new SimpleDateFormat("yyyy-MM-dd")
    ((dateFormat.parse(date).getTime - firstOfYear)/86400000).toInt
  }

/** regionFirstPeak
  * Calculates the first peak number of cases based on daily case numbers pulled from Spark
  * 
  *
  * @param spark - the spark session
  * @param df - the dataframe that the data will be loaded into
  * 
  * FIXME: refactor this function to either not include resultpath as a parameter, or find a use for it
  */
def regionalFirstPeak(spark: SparkSession, df: DataFrame): Unit = {
    import spark.implicits._
    val now = Calendar.getInstance()
    val time = now.getTimeInMillis
    val tableName = s"dfOptimize$time"

    df.write
      .partitionBy("region")
      .bucketBy(40, "country")
      .saveAsTable(tableName)

    val regionList = spark
      .sql(s"SELECT DISTINCT region FROM $tableName ORDER BY region")
      .rdd
      .map(_.get(0).toString)
      .collect()

    var tempDates: Array[Double] = null
    var tempCases: Array[Double] = null
    var tempFrame: DataFrame = null
    val firstPeakTimeAvg: ArrayBuffer[Double] = ArrayBuffer()
    val firstPeakForCountry: ArrayBuffer[Double] = ArrayBuffer()
    var countryList: Array[String] = Array()
    var peakTime: Double = 0.0d
    
    for (region <- regionList) {
      countryList = df
        .select($"country")
        .where($"region" === region)
        .distinct()
        .collect()
        .map(_.get(0).asInstanceOf[String])
        
      for (country <- countryList) {
        tempFrame = spark
          .sql(
          s"""SELECT DISTINCT country, date, new_cases
            |FROM $tableName
            |WHERE country = '$country'
            |AND date != 'NULL'
            |""".stripMargin)
          .sort($"date")
          .cache()
        tempCases = tempFrame
          .select($"new_cases")
          .collect()
          .map(_.get(0).toString.toDouble)
        tempDates = tempFrame
          .select($"date")
          .collect()
          .map(_.get(0).toString)
          .map(dayInYear(_).toDouble)
        peakTime = firstMajorPeak(tempDates, tempCases, 7, 10, 5)._1
        if (peakTime != -1) {
          firstPeakForCountry.append(peakTime)
          //          println(s"${country}, ${firstPeakForCountry.last}")
        }
      }
      firstPeakTimeAvg.append(
        firstPeakForCountry.sum / firstPeakForCountry.length
      )
      println(
        s"$region Average time before first major peak: ${firstPeakTimeAvg.last} (days)"
      )
      firstPeakForCountry.clear()
    }
    spark.sql(s"DROP TABLE IF EXISTS $tableName")
  }
}
