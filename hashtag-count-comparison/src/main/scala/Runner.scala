package HashtagCountComparison

import org.apache.spark.sql.SparkSession




object Runner {
    def main(args: Array[String]): Unit = {

        val spark = SparkSession.builder().appName("HashtagCountComparison").getOrCreate()
    
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
      * 
      *
      * @param path
      * @return
      */
    def hashtagCountComparison(path: String): Map[String, Int] = {
        val ret = Map[String,Int]()

        //TO-DO complete implimentation
         ret
    }
}

