object TwitterCovidAnalysis {

    /**
      * Simple function to read data from s3 bucket.
      *
      * @param spark
      * @param pathForS3
      */
    def readToDF(spark: SparkSession, pathForS3: String): DataFrame= {
        // TO DO
    }

    /**
      * Groups by age groups.
      * Returns DF of age groups and infection counts.
      *
      * @param df
      */
    def ageGroupsInfectionRate(df: DataFrame): DataFrame= {
        // TO DO
    }

    /**
      * Groups by day with highest spike. 
      * Returned columns: Date, infection rate (age 5-30), and Twitter Volume. 
      * @param df
      */
    def ageTwitterVolume(df: DataFrame): DataFrame={
        // TO DO
    }
}