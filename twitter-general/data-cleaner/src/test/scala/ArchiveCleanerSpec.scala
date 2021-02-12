import org.scalatest.flatspec.AnyFlatSpec
import org.apache.spark.sql.SparkSession

class ArchiveCleanerSpec extends AnyFlatSpec {
    val spark = SparkSession
        .builder
        .appName("Tester")
        .master("local[*]")
        .getOrCreate()

    "ArchiveCleaner" should "return the number of lines in the file it outputs" in {
        assert(ArchiveCleaner.clean("test-data", spark) == 9)
    }
}