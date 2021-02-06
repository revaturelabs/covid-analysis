import org.apache.spark.sql.SparkSession
object Runner {
    def main(args: Array[String]): Unit = {

        val spark = SparkSession
        .builder
        .appName("Covid19TweetWordCount")
        .master("local[*]")
        .getOrCreate()

        WordCount.tweetCovid19Words(args(0), spark)
        spark.stop
    }
}

