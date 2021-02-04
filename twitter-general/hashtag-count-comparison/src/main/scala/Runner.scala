package HashtagCountComparison

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.{DataFrameReader,DataFrame,Dataset}
import javax.xml.crypto.Data





object Runner {

case class Tweet(text: String)
  
    def main(args: Array[String]): Unit = {


      if(args.length ==1){
        //set up spark session
        val spark = SparkSession.builder().master("local").appName("HashtagCountComparison").getOrCreate()
        import spark.implicits._

        
        
        
        //get input path for s3
        //read input into a dataset
        val tweets = spark.read.format("text").load(getInputPath(args(0).toInt)).as[Tweet]
        
        

        //filter input into hashtags
        //val tweetsWithHashtags = tweets.filter(hasHashtag(_))
        //val hashtags = extractHashtags(tweetsWithHashtags)

        //reduce on hashtags to get a count of each hashtag
        //val hashtagCount = hashtags.groupBy("text").count().as[Tweet]

        //map hashtags to covid related or not covid related
        //val hashtagCategories = hashtagCount.map(markCovidRelated)

        //reduce on categories to get number of non covid hashtags vs covid hashtags
    
        //output results to s3
        spark.stop()
      }else{
        println("Usage: [mode] where mode is an integer from 0-2")
      }
        
    }

    def readToDS(spark: SparkSession, path: String): Dataset[Tweet]={
      // import spark.implicits._
      // return spark.read.format("text").load(path).as[Tweet]
      null
    }

    /**
      * a function that returns true if a text string contains a word that starts with '#'
      *
      * @param text the text string to be parsed 
      * @return true if text contains a word starting with '#' otherwise false
      */
    def hasHashtag(tweet: Tweet):Boolean={
      false
    }

    /**
      * a function that takes in a dataset of type tweet
      * and returns a new dataset of type tweet where the text contains only the
      * hashtags from the text of the input dataset
      *
      * @param dataset the input dataset
      * @return a new dataset of type tweet which contains only the hashtags from the text of the
      *         input dataset
      */
    def extractHashtags(dataset: Dataset[Tweet]): Dataset[Tweet]={
      null
    }
    
    /**
      * a function that takes in a Tweet who's text contains
      * only a hashtag, and replaces the text with "covid" or "non-covid"
      * based on whether or not the hashtag is covid related or not
      *
      * @param tweet the input tweet
      * @return a new tweet with the new text
      */
    def markCovidRelated(tweet: Tweet): Tweet={
      null
    }

    

    def manipulateDataFrame(df: DataFrame): DataFrame={

      //TO DO complete implementation
      //TO-DO complete implementation
      //filter tweet text into hashtags only

        //reduce on hashtags to get a count of each hashtag

        //map hashtags to covid related or not covid related

        //reduce on categories to get number of non covid hashtags vs covid hashtags
      //maybe lower rdd work then convert back to DataFrame
      //groupBy(hashtags).count()
      //check if hashtag is covid related
      //if it is, add it to the list of covid hashtags
      //if it is not, add it to the list of non-covid hashtags
      //return a dataset which contains 2 rows
      //row 1 is covid hashtags and their count
      //row 2 is non-covid hashtags and their count
      //groupBy(isCovid).count()
      null
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
          case 0 => ret = "s3://covid-analysis-p3/datalake/twitter-general/dec_11-dec_25/"
          case 1 => ret = "s3://covid-analysis-p3/datalake/twitter-general/dec_26-jan_05/"
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
    def output(df: DataFrame, path: String):Unit ={

      //TO-DO complete implementation
      //output df as a parquet? Yes probably.

    }
    
}

