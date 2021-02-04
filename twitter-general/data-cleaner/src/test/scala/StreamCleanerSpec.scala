import org.scalatest.flatspec.AnyFlatSpec
import org.apache.spark.sql.SparkSession

class StreamCleanerSpec extends AnyFlatSpec {

    val spark = SparkSession
        .builder
        .appName("StreamCleanerTester")
        .master("local[*]")
        .getOrCreate()

    "StreamCleaner" should "return the number of lines in the file it outputs" in {
        assert(StreamCleaner.clean("test-data", spark) == 9)
        spark.stop
    }
}