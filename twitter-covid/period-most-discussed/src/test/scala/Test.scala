import org.scalatest.flatspec.AnyFlatSpec

class Test extends AnyFlatSpec {
    val spark = SparkSession.builder()
    .appName("Testing")
    .master("local[4]")
    .getOrCreate()
    
    val path = "DummyData.csv"

    //TODO: Make some tests.
}