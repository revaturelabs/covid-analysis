import org.apache.spark.streaming._
import org.apache.spark.streaming.twitter._
import org.apache.log4j.Logger
import org.apache.log4j.Level
import twitter4j.Status

object Main {

  /** Uses system properties to set up twitter credentials
    * taking the information from twitterProps.txt
    * - https://www.udemy.com/course/apache-spark-with-scala-hands-on-with-big-data/learn/lecture/22022796#overview
    */ 
  def setupTwitterStream(){
    import scala.io.Source
    val lines = Source.fromFile("twitterProps")
    for (line <- lines.getLines) {
      val fields = line.split(" ")
      if (fields.length == 2) {
        System.setProperty("twitter4j.oauth." + fields(0), fields(1))
      }
    }
    lines.close()
  }

  def main(args: Array[String]) {

    // Set up Twitter credentials
    setupTwitterStream()

    // Will write to results every hour
    val duration = Minutes(60)

    val ssc = new StreamingContext("local[*]", "TwitterStreaming", duration)
    Logger.getRootLogger().setLevel(Level.ERROR)

    // Filters tweets by text only, then adds "--tweet-text-start--" 
    // to the beginning of the text to denote individual tweets
    // overwrites results every hour with the most recent hour's worth of data
    val results = TwitterUtils.createStream(ssc, None)
    .map(status => "--tweet-text-start--:" + status.getText)
    .window(Minutes(60))

    // Save as an RDD
    results.foreachRDD(rdd => rdd.coalesce(1).saveAsTextFile(s"RawResults"))

    // Will create streaming checkpoints to ensure accurate data
    ssc.checkpoint("Checkpoint")

    // Start the stream
    ssc.start()

    // Stream until manually terminated
    ssc.awaitTermination
  }  
}
