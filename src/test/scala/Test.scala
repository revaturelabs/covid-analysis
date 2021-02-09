import org.scalatest.flatspec.AnyFlatSpec
import org.apache.spark.sql.SparkSession

class Test extends AnyFlatSpec {

    val spark = SparkSession
    .builder()
    .appName("Testing")
    .master("local[4]")
    .getOrCreate()

    val path = "src/test/scala/Resources/DummyData.csv"
    val df = spark.read.csv(path)

    "readToDF" should "return 11 rows" in {
        assert(TwitterCovidAnalysis.readToDF(spark,path).count ==11)
    }

    "groupByDate" should "return 2 columns" in {
        assert(TwitterCovidAnalysis.groupByDate(df).columns.size==2)
    }
    "groupByDate" should "return 335 rows" in {
        assert(TwitterCovidAnalysis.groupByDate(df).count==335)
    }

    "ageGroupsInfectionRate" should "return 2 columns" in {
        assert(TwitterCovidAnalysis.ageGroupsInfectionRate(df).columns.size== 2)
    }

    "ageTwitterVolume" should "return 2 columns" in {
         assert(TwitterCovidAnalysis.ageTwitterVolume(df).columns.size== 2)
    }
}