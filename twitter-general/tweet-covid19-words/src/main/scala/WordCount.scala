import org.apache.spark.sql.SparkSession

class WordCount()

object WordCount {

    def tweetCovid19Words(path: String, spark: SparkSession): Map[String, Int] = {
        val covidWords = Set[String]("covid", "coronavirus")
        val otherIgnore = Set[String]("of", "to")
        val ignore = covidWords.union(otherIgnore)
        Map("Test" -> 1)
    }
}