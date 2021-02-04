import org.apache.spark.sql.{DataFrame, SparkSession}

object EUSpikes {
  //read to dataframe from s3 bucket
  def pullData(): DataFrame = {

  }

  //filter to only include age groups <15 and 15-24
  def filterAgeGroups(): DataFrame = {

  }

  //group by year_week and sum(new_cases)
  //result will have columns: year_week, age_group, sum(new_cases)
  def groupData(): DataFrame = {

  }


}
