object peakDiscussion {

    /**
      * Returns a DataFrame containing the tweet count from each day in the
      * dataframe, sorted in descending order by number of tweets.
      *
      * @param df 
      * @param spark
      */
    def dailyCountsRanked(df: DataFrame, spark: SparkSession): DataFrame = {
        //TODO
    }

    /**
      * Returns a DataFrame containing the tweet count from each month in the
      * dataframe, sorted in descending order by number of tweets.
      *
      * @param df
      * @param spark
      */
    def monthlyCountsRanked(df: DataFrame, spark: SparkSession): DataFrame = {
        //TODO
    }

    /**
      * Returns a DataFrame containing the tweet count from each hour in the
      * DataFrame, sorted in descending order by number of tweets.
      *
      * @param df
      * @param spark
      */
    def hourlyCountsRanked(df: DataFrame, spark: SparkSession): DataFrame = {
        //TODO
    }


}