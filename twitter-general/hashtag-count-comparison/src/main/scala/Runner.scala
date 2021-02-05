package HashtagCountComparison

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.{DataFrameReader,DataFrame,Dataset}
import javax.xml.crypto.Data





object Runner {

  case class Tweets(text: String)

  case class Hashtag(hashtag: String)
  
    def main(args: Array[String]): Unit = {


      if(args.length ==1){
        //set up spark session
        val spark = SparkSession.builder().master("local").appName("HashtagCountComparison").getOrCreate()
        import spark.implicits._

        
        
        
        //get input path for s3
        //read input into a dataset
        //val tweets = spark.read.format("text").load(getInputPath(args(0).toInt)).as[Tweets]
        
        //split input on words, filter out hashtags and put all hashtags into new dataset
        //of type Hashtag
        //val hashtags = makeHashtagDS(tweets)

        //map hashtags to covid related or not covid related
        //val hashtagCategories = hashtags.map(markCovidRelated)

        //reduce on categories to get number of non covid hashtags vs covid hashtags
        //val categoryCount = hashtagCategories.groupBy("hashtag").count()
    
        //output results to s3

                    
        spark.stop()
      }else{
        println("Usage: [mode] where mode is an integer from 0-2")
      }
        
    }

    def readToDS(spark: SparkSession, path: String): Dataset[Tweets]={
      // import spark.implicits._
      // return spark.read.format("text").load(path).as[Tweets]
      null
    }

    def makeHashtagDS(ds: Dataset[Tweets]): Dataset[Hashtag]={
      null
      // val hashtags = ds
      //   .select(explode(split("text", "\\W+")).alias("word"))
      //   .filter("word" =!= "")
      //   .filter("word".startsWith("#")).as[Hashtag]
    }
    
    /**
      * a function that takes in a hashtag, check to see if it is covid related,
      * and replaces the text of the hashtag with 'covid' or 'non-covid'
      * depending on the result
      *
      * @param Hashtag the input hashtag
      * @return a new tweet with the new text
      */
    def markCovidRelated(hashtag: Hashtag): Hashtag={
      null
    }

        /**
          * a helper function for mackCovidRelated that takes in a string
          * and determines if the hashtag is covid related or not
          *
          * @param hashtag
          * @return
          */
        def isCovidRelated(hashtag: String): Boolean={
          false
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

      //  range match {
      //     case 0 => ret = "s3://covid-analysis-p3/datalake/twitter-general/dec_11-dec_25/"
      //     case 1 => ret = "s3://covid-analysis-p3/datalake/twitter-general/dec_26-jan_05/"
      //     case _ => ret = "no preset"
      //   }
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

