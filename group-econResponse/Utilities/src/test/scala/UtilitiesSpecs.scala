import com.github.mrpowers.spark.fast.tests.DatasetComparer
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions.col
import org.scalatest.funspec.AnyFunSpec
import utilites.s3DAO

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
  class UtilitiesSpecs extends AnyFunSpec with SparkSessionTestWrapper with DatasetComparer {
    it("aliases a DataFrame to test spark availability") {
      val srcDF = spark.read
        .option("header", value = true)
        .csv(getClass.getClassLoader.getResource("test_dataset.csv").getPath)
        .toDF("name", "agg_gdp", "agg_cases")

      val resultDF = srcDF.select(col("name").alias("country"))

      assert(resultDF.columns.contains("country"))
    }

    it("Sets the download path for the dao.") {
      val s3 = s3DAO()
      val path = "test/path.tsv"
      s3.setDownloadPath(path)

      assert(s3.downloadPath == path)
    }
  }