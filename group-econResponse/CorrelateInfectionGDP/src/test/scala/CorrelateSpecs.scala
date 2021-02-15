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
      .toDF("name", "agg_gdp", "agg_cases")

    val resultDF = srcDF.select(col("name").alias("country"))

    val expectedDF = spark.read
      .option("header", value = true)
      .csv(getClass.getClassLoader.getResource("test_dataset.csv").getPath)
      .toDF("country", "agg_gdp", "agg_cases")

    assertSmallDatasetEquality(resultDF, expectedDF)
  }

  it("calculates the pearson correlation coefficient for two columns") {
    val (arr1: Array[Double], arr2: Array[Double]) = (Array(2.4d, 1.62d), Array(2.4d, 1.62d))
//    val res = StatFunc.correlation(arr1, arr2)

//    assert(res == 1.0d)

  }
  it("calculates the hypothesis testing for ") {
//    val res = Calculator().hypoTest(1.1234d, 5.6789d)

//    assert(res == 1.0d)
  }
}
