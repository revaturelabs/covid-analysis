import org.apache.log4j._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.hadoop.fs._
import java.io._
import scala.reflect.io.Directory



object StreamCleaner {

    /** Takes data from a specific day that has been streamed in with a separate program
      * and does the following to it:
      *     - Grabs all files relevant to the specified day
      *     - Grabs the file just created as a DataFrame
      *     - Polishes text file and reformats to match the format that is yielded by JsonCleaner
      *     - Saves to cleanedFiles directory
      *     - Renames the file to match the format of files yielded from JsonCleaner
      *     - Moves the output rdd file to a final output directory, 
      * @param day Specified input day (syntax for February 3rd would be '02-03')
      * @return Returns a Long that will contain the number of lines in the final DataFrame. Used for testing purposes.
      */
    def clean(day: String, spark: SparkSession): Long = {

        // The following bit of code exists because the streamed in files 
        // were being saved with names like "Wed-Feb-03-04.04.54-EST-2021"
        // As such, in order to allow the input to match these files, 
        // we need to create cases that will map user specified inputs to the month in the file name
        // i.e. 02-03 => Feb-03
        val monthVal = day.substring(0, 2)
        val dayVal = day.substring(3)
        val dayModified = monthVal match {
            case "01" => s"Jan-$dayVal"
            case "02" => s"Feb-$dayVal"
            case "03" => s"Mar-$dayVal"
            case "04" => s"Apr-$dayVal"
            case "05" => s"May-$dayVal"
            case "06" => s"Jun-$dayVal"
            case "07" => s"Jul-$dayVal"
            case "08" => s"Aug-$dayVal"
            case "09" => s"Sep-$dayVal"
            case "10" => s"Oct-$dayVal"
            case "11" => s"Nov-$dayVal"
            case "12" => s"Dec-$dayVal"
            case _     => day
        }

        import spark.implicits._

        // Grabs all files for the specified day (this was run locally)
        // YOU WILL NEED TO MODIFY THIS PATH TO POINT TO THE LOCATION OF YOUR HOURLY STREAM FILES
        val input = spark.read
        //.text(s"C:/Users/Cody/Desktop/Revature Training Stuff/scalawags-group-5/twitter-stream/HourlyResults/*$dayModified*") // For running locally
        .text(s"unit-test-data/stream-test/*$dayModified*") //For unit testing
        
        // Filters out Tweets to only contain the "text" field, as this was the only thing that was needed for twitter-general's analyses
        // These filtered lines are written as a json file in the test-output directory
        // This output it quite large so it has been .gitignored
        val tweets = input.select(explode(split(col("value"), "--tweet-text")))
            .filter(x => x.mkString.startsWith("-start--:"))
            .map(x => x.mkString.replace("-start--:", "\"text\":\"") + "\"").cache

        // Writes the output to the "test-output-stream" folder
        tweets.coalesce(1).write.mode(("overwrite")).text(s"test-output-stream")

        // Gets count of all tweets to be returned for unit testing.
        val lineCount = tweets.count
        
        // Moves and renames the file that was written by Spark SQL, which will then be uploaded to S3 via the AWS S3 CLI
        val fs = FileSystem.get(spark.sparkContext.hadoopConfiguration)
        val file = fs.globStatus(new Path("test-output-stream/part*"))(0).getPath().getName()
        fs.rename(new Path("test-output-stream/" + file), new Path(s"cleanedStreamFiles/$day.txt"))

        println(lineCount)
        lineCount
    }

    def main(args: Array[String]) {

        val spark = SparkSession
        .builder
        .appName("StreamCleaner")
        .master("local[*]")
        .getOrCreate()

        clean(args(0), spark)
        
        spark.stop
    }
    
}