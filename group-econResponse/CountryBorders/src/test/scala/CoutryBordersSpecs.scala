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

  it("aliases a DataFrame") {
    val srcDF = spark.read
      .option("header", value = true)
      .csv(getClass.getClassLoader.getResource("test_dataset.csv").getPath)

    val resultDF = srcDF.select(col("name").alias("country"))

    val expectedDF = spark.read
      .option("header", value = true)
      .csv(getClass.getClassLoader.getResource("test_dataset.csv").getPath)
      .toDF("country", "agg_gdp", "agg_cases")
      .drop("agg_gdp", "agg_cases")

    assertSmallDatasetEquality(resultDF, expectedDF)
  }
}
