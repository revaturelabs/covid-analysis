
class WordCount()

object WordCount {

    def tweetCovid19Words(path: String): Map[String, Int] = {
        val covidWords = Set[String]("covid", "coronavirus")
        val otherIgnore = Set[String]("of", "to")
        val ignore = covidWords.union(otherIgnore)
        Map("Test" -> 1)
    }
}