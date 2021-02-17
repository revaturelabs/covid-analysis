import org.scalatest.flatspec.AnyFlatSpec
import org.apache.spark.sql.{DataFrame, Row, SparkSession}
import org.apache.spark.sql.functions.{count, asc, desc}

class Test extends AnyFlatSpec {

    val spark = SparkSession.builder()
    .appName("Testing")
    .master("local[4]")
    .getOrCreate()
    
    val path = "src/test/resources/TestData.tsv"
    val df = spark.read.option("header", "true").option("delimiter", "\t").option("inferSchema", "true").csv(path)

    // Tests that dailyCountsRanked returns correct nuber of rows.
    "dailyCountsRanked" should "return 4 rows" in {
        assert(peakDiscussion.dailyCountsRanked(df, spark).count == 4)
    }

    // Tests that the most common day is correct.
    "dailyCountsRanked" should "return '2020-01-03' as the top daily count" in {
        assert(peakDiscussion.dailyCountsRanked(df, spark).select("date").first.getString(0) == "2020-01-03")
    }

    // Tests that monthlyCountsRanked returns correct number of rows.
    "monthlyCountsRanked" should "return 1 row" in {
         assert(peakDiscussion.monthlyCountsRanked(df, spark).count == 1)
    }

    // Tests that the most common month is correct.
    "monthlyCountsRanked" should "return '1-2020' as the top monthly count" in {
        assert(peakDiscussion.monthlyCountsRanked(df, spark).select("month-year").first.getString(0) == "1-2020")
    }

    // Tests that hourlyCountsRanked returns correct number of rows.
    "hourlyCountsRanked" should "return 6 rows" in {
         assert(peakDiscussion.hourlyCountsRanked(df, spark).count == 6)
    }

    //Tests that the most common hour is correct.
    "hourlyCountsRanked" should "return '18' as the top hourly count" in {
         assert(peakDiscussion.hourlyCountsRanked(df, spark).select("hour").first.getString(0) == "18")
    }
}