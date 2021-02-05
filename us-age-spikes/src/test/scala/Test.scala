import org.scalatest.flatspec.AnyFlatSpec

class Test extends AnyFlatSpec {

    val spark = SparkSession
    .builder()
    .appName("Testing")
    .master("local[4]")
    .getOrCreate()

    val path = "DummyData.csv"

    "readToDF" should "return 5 rows" in {
        assert(TwitterCovidAnalysis.readToDF(spark,path).count ==5)
    }

    "ageGroupsInfectionRate" should "return 0" in {
        assert(TwitterCovidAnalysis.ageGroupsInfectionRate().count== 0)
    }

    "ageTwitterVolume" should "return 0" in {
         assert(TwitterCovidAnalysis.ageTwitterVolume().count== 0)
    }
}