import com.github.mrpowers.spark.fast.tests.DatasetComparer
import covidAndGDP.CorrelateInfectionGDP
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
      .csv("CorrelateInfectionGDP/src/test/resources/test_dataset.csv")
      .toDF()

    val resultDF = srcDF.select(col("name").alias("country"))

   assert(resultDF.columns.contains("country"))
  }

  it("calculates the pearson correlation coefficient for two columns") {
    val testDF = spark.read
      .option("header", value = true)
      .option("inferSchema", value = true)
      .csv(getClass.getClassLoader.getResource("test_dataset.csv").getPath)
      .toDF()

    val response = CorrelateInfectionGDP.getCorrelation(testDF)

    //Columns have a negative correlation and should produce a -1 coefficient.
    assert(response == -1.0d)
  }

  it("calculates the hypothesis testing for ") {
    // TODO: Test when implemented.
    assert(true)
  }
}
