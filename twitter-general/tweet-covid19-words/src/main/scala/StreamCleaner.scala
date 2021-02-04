import org.apache.log4j._
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.hadoop.fs._
import java.io._
import scala.reflect.io.Directory



object StreamCleaner {

    def cleanAndSave(day: String) {
        //Logger.getLogger("org").setLevel(Level.ERROR)
        val directory = new Directory(new File("test-output-rdd"))
        directory.deleteRecursively()
   
        val spark = SparkSession
        .builder
        .appName("WordCount")
        .master("local[*]")
        .getOrCreate()

        import spark.implicits._
        val input = spark.read
            .option("recursiveFileLookup","true")
            .json(s"C:/Users/Cody/Desktop/extracts/$day")
        
        val text = input.select("text").coalesce(1).write.mode("overwrite").json("test-output")
        val input2 = spark.sparkContext.textFile("test-output/*.json")
        input2.filter(x => x.length > 2).map(x => x.substring(1, x.length - 1)).coalesce(1).saveAsTextFile("test-output-rdd")
        spark.close

        val src = new File("test-output-rdd/part-00000")
        val dest = new File(s"cleanedFiles/$day.txt")
        new FileOutputStream(dest) getChannel() transferFrom(new FileInputStream(src) getChannel, 0, Long.MaxValue )
    }
    
}