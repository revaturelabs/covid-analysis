
class WordCount()

object WordCount {

    def tweetCovid19Words(path: String): Map[String, Int] = {
        val covidIgnore = Set[String]("covid", "coronavirus")
        val otherIgnore = Set[String]("of", "to")
        val ignore = covidIgnore.union(otherIgnore)
        Map("Test" -> 1)
    }
}