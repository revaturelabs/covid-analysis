import EUSpikes._
import org.apache.spark.sql.SparkSession
import org.scalatest.flatspec.AnyFlatSpec

class EUSpikesTest extends AnyFlatSpec {
//  val df = pullData()
  val spark = SparkSession.builder()
    .appName("EUSpikes")
    .master("local[4]")
    .getOrCreate()
    spark.sparkContext.setLogLevel("WARN")

  "Dataframe" should "not be empty" in {
    val df = pullData(spark)
    assert(df.count() > 0)
  }

  "Dataframe" should "not have age_groups 80+" in {
    val df = pullData(spark)
    val filteredDf = filterAgeGroups(spark, df)
    assert(df.filter(df("age_group") === "80+yr").count() == 0)
  }

  "Dataframe" should "have 2 columns" in {
    import spark.implicits._
    val df = pullData(spark)
    val filteredDf = filterAgeGroups(spark, df)
    val groupedDf = groupData(spark, filteredDf)
    assert(groupedDf.columns.size == 2)
  }

}
