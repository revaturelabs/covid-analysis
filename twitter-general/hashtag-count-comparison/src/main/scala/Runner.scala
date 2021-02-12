

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.{DataFrameReader,DataFrame,Dataset}
import org.apache.spark.sql.functions._
import javax.xml.crypto.Data
import org.apache.hadoop.fs.FileSystem
import org.apache.hadoop.fs.Path
import java.io.File
import java.io.PrintWriter
import java.io.FileOutputStream
import java.io.FileInputStream
import scala.sys.process._




object Runner {

  case class Tweets(value: String)

  case class Hashtag(hashtag: String)
  
    def main(args: Array[String]): Unit = {



      if(args.length ==1){
        //set up spark session
        val spark = SparkSession.builder().master("local").appName("HashtagCountComparison").getOrCreate()
        import spark.implicits._
        
        
        
        
        //get input path for s3
        //read input into a dataset
        val tweets = readToDS(spark, getInputPath(args(0).toInt))

        
        //split input on words, filter out hashtags and put all hashtags into new dataset
        //of type Hashtag
        val hashtags = makeHashtagDS(tweets,spark)

        //map hashtags to covid related or not covid related
        val hashtagCategories = hashtags.map(markCovidRelated(_,isCovidRelated(x$1.hashtag)))

        //reduce on categories to get number of non covid hashtags vs covid hashtags
        val categoryCount = hashtagCategories.groupBy("hashtag").count().sort(desc("count")).cache()
        categoryCount.show()

        //save locally
        outputLocal(categoryCount)
        //rename file
        findAndRename(spark,getFileName(args(0).toInt))
        //output to s3
        outputS3(getFileName(args(0).toInt))
        
                    
        spark.stop()
      }else{
        println("Usage: [mode] where mode is an integer from 0-2")
      }
        
    }

  
    /**
      * a function for reading in 
      *
      * @param spark the spark session for reading in the data
      * @param path the path to pass to the load function of the spark session
      * @return
      */
    def readToDS(spark: SparkSession, path: String): Dataset[Tweets]={
      import spark.implicits._
      return spark.read.text(path).as[Tweets]
    }

    /**
      * a function that takes in a Dataset[Tweets] and parses it into a new
      * Dataset[Hashtag] which contains the hashtags from the Tweets in the input
      * dataset
      *
      * @param ds the input dataset
      * @return a new 
      */
    def makeHashtagDS(ds: Dataset[Tweets], spark: SparkSession): Dataset[Hashtag]={

      import spark.implicits._

      val hashtags = ds
        .withColumn("hashtag",explode(split($"value", " "))).as("hashtag")
        .filter($"hashtag" =!= "")
        .filter($"hashtag".startsWith("#")).as[Hashtag]
        hashtags.printSchema()

        hashtags
    }
    
    /**
      * a function that takes in a hashtag, check to see if it is covid related,
      * and returns a new hashtag with 'covid' or 'non-covid' as the hashtag field
      * depending on the result
      *
      * @param Hashtag the input hashtag
      * @return a new tweet with the new text
      */
    def markCovidRelated(hashtag: Hashtag, condition: Boolean): Hashtag={

      if(condition){
          Hashtag("covid hashtags")
      }else{
          hashtag
      }

    }

        /**
          * a helper function for mackCovidRelated that takes in a string
          * and determines if the hashtag is covid related or not
          *
          * @param hashtag
          * @return
          */
        def isCovidRelated(hashtag: String): Boolean={
          val h = hashtag.drop(1)
          Terms.getCovidTerms.contains(h.toLowerCase())
        }

    /**
      * a function that takes in an integer value and returns a string
      * of the s3 file path where the input is stored
      *
      * @param range an integer that should be 1, 2, or 3
      * @return returns a string of the s3 file path where the input is stored
      */
    def getInputPath(range: Int): String={
        var ret =""

       range match {
          case 0 => ret = "s3a://covid-analysis-p3/datalake/twitter-general/dec_11-dec_25/"
          case 1 => ret = "s3a://covid-analysis-p3/datalake/twitter-general/dec_26-jan_05/"
          case 2 => ret = "s3a://covid-analysis-p3/datalake/twitter-general/feb_03-feb_14/"
          case 3 => ret = "s3a://covid-analysis-p3/datalake/twitter-general/test-data/*"
          case _ => ret = "no preset"
        }
        ret
        
    }
        
    /**
      * a function that outputs the data to the s3 bucket
      *
      * @param df DataFrame to be saved in s3
      * @param path path to the data warehouse of the s3 bucket
      */
    def outputLocal(df: DataFrame):Unit ={

      df.coalesce(1).write.mode("overwrite").option("header", "true").csv("Results")

    }

    /**
      * a function that takes in a file name and output the file to the s3 bucket
      * 
      *
      * @param fileName
      */
    def outputS3(fileName: String): Unit ={

      //this block is for local testing
      // val sendToS3 = s"aws s3 mv Results/$fileName.csv s3://covid-analysis-p3/datawarehouse/twitter-general/hashtag-count-comparison/$fileName.csv"
      //   sendToS3.!

      //this block is for running on the cluster
        val sendToLocal = s"hdfs dfs -get Results/$fileName.csv $fileName.csv"
        val sendToS3 = s"aws s3 mv $fileName.csv s3://covid-analysis-p3/datawarehouse/twitter-general/hashtag-count-comparison/$fileName.csv"
        sendToLocal.!
        sendToS3.!
    }

    /**
      * a function that returns a string of the correct output file name
      * based in the mode in which the program is running, similar to getInputPath()
      *
      * @param range a number between 0 and 3 which designates the mode the program is running in
      *             and determins the name of the output file
      * @return a String that is the specified name of the output file
      */
    def getFileName(range: Int): String={
      val fileName = range match {
            case 0 => "HashtagCountResults-Dec_11-Dec_25"
            case 1 => "HashtagCountResults-Dec_26-Jan_05"
            case 2 => "HashtagCountResults-Feb_03-Feb_14"
            case 3 => "S3ConnectionTestResults"
            case _ => "WordCountResults-TestData"
      }
        fileName
    }


    /**
      * A function that finds the output file in HDFS and renames it
      *
      * @param spark our spark session
      * @param fileName a String which the output file should be renamed to
      */
    def findAndRename(spark: SparkSession, fileName: String): Unit={
      //initialize file system
        val fs = FileSystem.get(spark.sparkContext.hadoopConfiguration)
      //retrieve the file from HDFS
        val file = fs.globStatus(new Path("Results/part*"))(0).getPath().getName()
      //rename that file
        fs.rename(new Path("Results/" + file), new Path(s"Results/$fileName.csv"))
    }
    
}