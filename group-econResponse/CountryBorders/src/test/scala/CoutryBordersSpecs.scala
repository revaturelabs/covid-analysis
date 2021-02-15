import com.github.mrpowers.spark.fast.tests.DatasetComparer
import org.scalatest.funspec.AnyFunSpec

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.col

trait SparkSessionTestWrapper {

  lazy val spark: SparkSession = {
    SparkSession
      .builder()
      .master("local[*]")
      .appName("spark session")
      .config("spark.sql.shuffle.partitions", "1")
      .getOrCreate()
  }

}

class CorrelateSpecs extends AnyFunSpec with SparkSessionTestWrapper with DatasetComparer {

  it("aliases a DataFrame to test spark availability") {
    val srcDF = spark.read
      .option("inferSchema", value = true)
      .option("header", value = true)
      .csv("CountryBorders/src/test/resources/test_dataset.csv")
      .toDF()

    val resultDF = srcDF.select(col("name").alias("country"))

    assert(resultDF.columns.contains("country"))
  }
}
