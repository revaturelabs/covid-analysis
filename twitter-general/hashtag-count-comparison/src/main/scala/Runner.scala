package HashtagCountComparison

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.{DataFrameReader,DataFrame}
import javax.xml.crypto.Data




object Runner {
    def main(args: Array[String]): Unit = {

        //set up spark session
        val spark = SparkSession.builder().appName("HashtagCountComparison").getOrCreate()
        import spark.implicits._
        
        //get input path for s3
        //read input into a dataframe/dataset
        //val tweets = readToDF(spark,getInputPath(args(0).toInt))
        
        

        //filter input into hashtags

        //reduce on hashtags to get a count of each hashtag

        //map hashtags to covid related or not covid related

        //reduce on categories to get number of non covid hashtags vs covid hashtags
    
        //output results to s3
    }

    def readToDF(spark: SparkSession, path: String): DataFrame={
      //TO-DO complete implementation
      null
      //return spark.read.format("text").load(path)
    }

    /**
      * a function that takes in an integer value and returns a string
      * of the s3 file path where the input is stored
      *
      * @param range an integer that should be 1, 2, or 3
      * @return returns a string of the s3 file path where the input is stored
      */
    def getInputPath(range: Int): String={
        val ret = ""
        //TO-DO complete implementation
        ret
    }

    /**
      * a function that filters the tweet text to just it's hashtags
      *
      * @param df starting state of DataFrame
      * @return new DataFrame after opperations on df
      */
    def filterToHashtags(df: DataFrame): DataFrame={
      
      //TO-DO complete implementation
      //maybe lower rdd work then convert back to DataFrame
      null
    }

    /**
      * a function to reduce all hashtags and count them
      *
      * @param df starting state of DataFrame
      * @return new DataFrame after opperations on df
      */
    def groupByHashtags(df: DataFrame): DataFrame={

      //TO-DO complete implementation
      //groupBy(hashtags).count()
      null
    }

    /**
      * a function that maps hashtags to a category of covid related
      * or non-covid related
      *
      * @param df starting state of DataFrame
      * @return new DataFrame after opperations on df
      */
    def mapHashtags(df: DataFrame): DataFrame={
      
      //TO-DO complete implementation
      //check if hashtag is covid related
      //if it is, add it to the list of covid hashtags
      //if it is not, add it to the list of non-covid hashtags
      //return a dataset which contains 2 rows
      //row 1 is covid hashtags and their count
      //row 2 is non-covid hashtags and their count
      null
    }

    /**
      * a function that counts the number of hashtags in each category (covid vs non-covid)
      *
      * @param df starting state of DataFrame
      * @return new DataFrame after operatoins on df
      */
    def reduceToCategories(df: DataFrame): DataFrame={

      //TO-DO complete implementation
      //groupBy(isCovid).count()
      null
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

