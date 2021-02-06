import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import scala.sys.process._
import java.io.File
import java.io.PrintWriter
import java.io.FileOutputStream
import java.io.FileInputStream
import org.apache.hadoop.fs.FileSystem
import org.apache.hadoop.fs.Path
import scala.collection.immutable.ListMap
import org.apache.spark.sql.Row

class WordCount()

object WordCount {

    case class TweetText(value: String)

    def tweetCovid19Words(range: String, spark: SparkSession): Array[String] = {
        val covidTerms = Terms.getCovidTerms
        val otherTerms = Terms.getOtherTerms
        val ignore = covidTerms.union(otherTerms)
        val path = range match {
            case "1" => "s3a://covid-analysis-p3/datalake/twitter-general/dec_11-dec_25/*"
            case "2" => "s3a://covid-analysis-p3/datalake/twitter-general/dec_26-jan_05/*"
            case "test-s3" => "s3a://covid-analysis-p3/datalake/twitter-general/test-data/*"
            case _ => "test-data.txt"
        }

        import spark.implicits._

        val input = spark.read
        //.option("recursiveFileLookup","true")
        .text(path).as[TweetText] // For production

        val covidTweets = input.filter(text => covidTerms.exists(word => {text.value.toLowerCase.contains(word)}))

        val words = covidTweets
            .map(tweet => tweet.value.replace(raw"\n", ""))
            .select(explode(split(col("value"), "\\W+")))
            .filter(word => !ignore.exists(ignoreWord => {word.mkString.toLowerCase.equals(ignoreWord)}))
            .filter(word => word.mkString.length > 2)
            .map(word => word.mkString.toLowerCase)
            .groupBy("value").count
            .sort(desc("count"))
            .limit(1000).cache

            words.coalesce(1).write.mode("overwrite").json("Results")

           val results = words.map(row => " " + row.mkString(" ")).collect()
           results.foreach(println)
           

        val fileName = range match {
            case "1" => "WordCountResults-Dec_11-Dec_25"
            case "2" => "WordCountResults-Dec_26-Jan-05"
            case "test-s3" => "S3ConnectionTestResults"
            case _ => "WordCountResults-TestData"
        }

        val fs = FileSystem.get(spark.sparkContext.hadoopConfiguration)
        val file = fs.globStatus(new Path("Results/part*"))(0).getPath().getName()
        fs.rename(new Path("Results/" + file), new Path(s"Results/$fileName.json"))

        val sendToS3 = s"aws s3 mv Results/$fileName.json s3://covid-analysis-p3/datawarehouse/twitter-general/word-count/$fileName.json"
        sendToS3.!

        results
    }
}