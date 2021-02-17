import com.github.mrpowers.spark.fast.tests.DatasetComparer
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.col
import org.scalatest.funspec.AnyFunSpec
trait SparkSessionTestWrapper {

  lazy val spark: SparkSession = {
    SparkSession
      .builder()
      .master("local")
      .appName("spark session")
      .config("spark.sql.shuffle.partitions", "1")
      .getOrCreate()
  }
}
class CovidResponseSpecs
    extends AnyFunSpec
    with SparkSessionTestWrapper
    with DatasetComparer {

  it("aliases a DataFrame to test spark availability") {
    val srcDF = spark.read
      .option("inferSchema", true)
      .option("header", value = true)
      .csv("CovidResponse/src/test/resources/test_dataset.csv")
      .toDF()

    val resultDF = srcDF.select(col("name").alias("country"))

    assert(resultDF.columns.contains("country"))
  }
}
