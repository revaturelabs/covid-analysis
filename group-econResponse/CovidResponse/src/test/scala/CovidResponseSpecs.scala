import com.github.mrpowers.spark.fast.tests.DatasetComparer
import org.scalatest.funspec.AnyFunSpec

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.col

import response.RankRegions
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

  it("aliases a DataFrame as general test for basic spark functionality") {
    val srcDF = spark.read
      .option("header", value = true)
      .csv(getClass.getClassLoader.getResource("test_dataset.csv").getPath)
      .toDF("name", "agg_gdp", "agg_cases")

    val resultDF = srcDF.select(col("name").alias("country"))

    val expectedDF = spark.read
      .option("header", value = true)
      .csv(getClass.getClassLoader.getResource("test_dataset.csv").getPath)
      .toDF("country", "agg_gdp", "agg_cases")

    assertSmallDatasetEquality(resultDF, expectedDF)
  }

  it("calculates GDP percent change in each region") {
    val spark = SparkSession.builder()
      .master("local[*]")
      .getOrCreate()

    val srcDF = spark.read
      .option("header", value = true)
      .csv(getClass.getClassLoader.getResource("test_dataset.csv").getPath)
      .toDF("name", "agg_gdp", "agg_cases")

    val res = RankRegions.changeGDP(spark, srcDF, "avg", percapita = true)

  }
}
