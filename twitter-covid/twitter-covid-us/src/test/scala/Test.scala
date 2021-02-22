import org.scalatest.flatspec.AnyFlatSpec
import org.apache.spark.sql.SparkSession

class Test extends AnyFlatSpec {

    val spark = SparkSession
    .builder()
    .appName("Testing")
    .master("local[4]")
    .getOrCreate()

    val path = "src/test/scala/Resources/DummyData.csv"
    // val path = "Resources/DummyData.csv"
    //val resultPath = "Resources/DummyData.csv"
	val resultPath = "src/test/scala/Resources/DummyData.csv"
	val twitterPath = "src/test/scala/Resources/twitterData.csv"
    // val df = spark.read.csv(path)
    val df = TwitterCovidAnalysis.readToDF(spark, path)
    val resultDF = TwitterCovidAnalysis.readToDF(spark, resultPath)
    val twitterDF = TwitterCovidAnalysis.readToDF(spark, twitterPath)


    "readToDF" should "return 10 rows" in {
        assert(TwitterCovidAnalysis.readToDF(spark,path).count == 10)
    }

    "groupByDate" should "return 2 columns" in {
        assert(TwitterCovidAnalysis.groupByDate(df).columns.size == 2)
    }

    "groupByDate" should "return 10 rows" in {
        assert(TwitterCovidAnalysis.groupByDate(df).count == 10)
    }

    "ageGroupsInfectionRate" should "return 2 columns" in {
        assert(TwitterCovidAnalysis.ageGroupsInfectionCount(df).columns.size == 2)
    }

    "twitterVolumeSpikes" should "return 3 columns" in {
        assert(TwitterCovidAnalysis.twitterVolumeSpikes(twitterDF,df).columns.size == 3)
    }
}