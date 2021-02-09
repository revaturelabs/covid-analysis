import org.apache.spark.sql.SparkSession
object Runner {
    def main(args: Array[String]): Unit = {

        val spark = SparkSession
        .builder
        .appName("Covid19TweetWordCount")
        .master("yarn")
        .getOrCreate()

        // Adds some jars necessary for our application to run as a thin jar on a Spark cluster.
        spark.sparkContext.addJar("https://repo1.maven.org/maven2/org/apache/hadoop/hadoop-aws/2.7.4/hadoop-aws-2.7.4.jar")
        spark.sparkContext.addJar("https://repo1.maven.org/maven2/com/amazonaws/aws-java-sdk/1.7.4/aws-java-sdk-1.7.4.jar")
        

        // Calls our main function. See WordCount.scala for more information.
        WordCount.tweetCovid19Words(args(0), spark)
        
        spark.stop
    }
}

