import org.apache.log4j._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.hadoop.fs._
import java.io._
import scala.reflect.io.Directory



object ArchiveCleaner {

    /** Takes data from a specific day that has been downloaded and extracted (extracted on the root level only, do not need to extract any subdirectories)
      * taken from https://archive.org/details/twitterstream?and[]=year%3A%222020%22&and[]=year%3A%222021%22
      * and does the following to it:
      *     - Grabs all files within the specified input directory and all of its subdirectories (this was run locally)
      *         - The specified input directory will be the root level of all of the extracted results
      *         - Sample format is given in sample-archive-data directory
      *     - Filters out Tweets to only contain the "text" field, as this was the only thing that was needed for twitter-general's analyses. 
      * These filtered lines are written as a json file in the test-output directory
      *     - Grabs the file just created as an RDD
      *     - Polishes json file and reformats to a text file saved to root project directory (without this, json output contains random empty lines)
      *     - Moves the output rdd file to a final output directory, which will then be uploaded to S3 via the AWS S3 CLI
      * @param day Specified input directory (sample-archive-data example: "01-01")
      * @return Returns a Long that will contain the number of lines in the final RDD. Used for testing purposes.
      */
    def clean(day: String, spark: SparkSession): Long = {
        
        // Deletes this file if it exists so program does not throw a "file already exists" exception
        val directory = new Directory(new File("test-output-rdd"))
        directory.deleteRecursively()

        import spark.implicits._

        // Grabs all files within the specified input directory and all of its subdirectories (this was run locally)
        // The "recursiveFileLookup" option allows this
        // YOU WILL NEED TO MODIFY THIS PATH TO POINT TO THE LOCATION OF YOUR DOWNLOADED AND EXTRACTED DIRECTORY
        val input = spark.read
            .option("recursiveFileLookup","true")
            .json(s"unit-test-data/archive-test/$day.json") // For unit testing
            //.json(s"sample-archive-data/$day") // For testing the sample data
            //.json(s"C:/Users/Cody/Desktop/extracts/$day") // For running locally
        
        // Filters out Tweets to only contain the "text" field, as this was the only thing that was needed for twitter-general's analyses
        // These filtered lines are written as a json file in the test-output directory
        // This output it quite large so it has been .gitignored
        val text = input.select("text").coalesce(1).write.mode("overwrite").json("test-output")

        // Grabs the file just created as an RDD
        val input2 = spark.sparkContext.textFile("test-output/*.json").cache()

        // Polishes json file and reformats to a text file saved to root project directory (without this, json output contains random empty lines)
        // This output it quite large so it has been .gitignored
        input2.filter(x => x.length > 2).map(x => x.substring(1, x.length - 1)).coalesce(1).saveAsTextFile("test-output-rdd")

        // Gets counts of all tweets to be returned for unit testing.
        val lineCount = input2.count

        // Moves the output rdd file to a final output directory, which will then be uploaded to S3 via the AWS S3 CLI
        val src = new File("test-output-rdd/part-00000")
        val dest = new File(s"cleanedArchiveFiles/$day.txt")
        new FileOutputStream(dest) getChannel() transferFrom(new FileInputStream(src) getChannel, 0, Long.MaxValue )

        println(lineCount)
        lineCount
    }

    def main(args: Array[String]) {

        val spark = SparkSession
        .builder
        .appName("ArchiveCleaner")
        .master("local[*]")
        .getOrCreate()

        clean(args(0), spark)

        spark.stop
    }
    
}