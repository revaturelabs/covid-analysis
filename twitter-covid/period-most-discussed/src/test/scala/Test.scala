import org.scalatest.flatspec.AnyFlatSpec

class Test extends AnyFlatSpec {

    val spark = SparkSession.builder()
    .appName("Testing")
    .master("local[4]")
    .getOrCreate()
    
    val path = "TestData.tsv"

    // Tests that dailyCountsRanked returns correct nuber of rows.
    "dailyCountsRanked" should "return 4 rows" in {
        assert(peakDiscussion.dailyCountsRanked().count == 4)
    }

    // Tests that the most common day is correct.
    "dailyCountsRanked" should "return '1/3/2020' as the top daily count" in {
        assert(peakDiscussion.dailyCountsRanked().select(col("date")).first.getString(0))
    }

    // Tests that monthlyCountsRanked returns correct number of rows.
    "monthlyCountsRanked" should "return 1 row" in {
         assert(peakDiscussion.monthlyCountsRanked().count == 1)
    }

    // Tests that the most common month is correct.
    "monthlyCountsRanked" should "return 'January, 2020' as the top monthly count" in {
        assert(peakDiscussion.monthlyCountsRanked().select(col("month")).first.getString(0))
    }

    // Tests that hourlyCountsRanked returns correct number of rows.
    "hourlyCountsRanked" should "return 5 rows" in {
         assert(peakDiscussion.hourlyCountsRanked.count == 5)
    }

    //Tests that the most common hour is correct.
    "hourlyCountsRanked" should "return '18' as the top hourly count" in {
         assert(peakDiscussion.hourlyCountsRanked.select(col("hour")).first.getString(0))
    }
}