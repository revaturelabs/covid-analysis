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

class CovidResponseSpecs extends AnyFunSpec with SparkSessionTestWrapper with DatasetComparer {

  it("aliases a DataFrame") {
    val srcDF = spark.read
      .option("header", value = true)
      .csv("test_dataset.csv")
      .toDF("name", "agg_gdp", "agg_cases")

    val resultDF = srcDF.select(col("name").alias("country"))

    val expectedDF = spark.read
      .option("header", value = true)
      .csv("test_dataset.csv")
      .toDF("country", "agg_gdp", "agg_cases")

    assertSmallDatasetEquality(resultDF, expectedDF)
  }

}
