object TwitterCovidAnalysis {

    /**
      * 
      *
      * @param spark
      * @param pathForS3
      */
    def readToDF(spark: SparkSession, pathForS3: String): DataFrame= {
        // TO DO
    }

    /**
      * 
      *
      * @param df
      */
    def ageGroupsInfectionRate(df: DataFrame): DataFrame= {
        // TO DO
    }

    /**
      * 
      *
      * @param df
      */
    def ageTwitterVolume(df: DataFrame): DataFrame={
        // TO DO
    }
}