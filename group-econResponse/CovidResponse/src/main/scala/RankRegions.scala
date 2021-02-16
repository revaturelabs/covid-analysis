package response

import org.apache.spark.sql.functions._
import org.apache.spark.sql.{DataFrame, SparkSession}

/** The RankRegions object uses methods to group and sort by some metric passed as an argument.
  */
object RankRegions {

  /** returns resulting dataFrame after performing given operation 'op'
    * on some field/metric 'metric' on df
    * If no op is provided default operation is 'avg'
    *
    * @param spark spark session
    * @param fullDS dataFrame from question 1('data') that has been grouped by region
    *               with new population field added
    *               NOTE: original dataFrame is the joined 'econDF' and 'casesDF'
    * @param metric field to perform operation on
    * @param op operation to perform, options:
    *           "max", "pop", "maxpop", default = "avg"
    * @return resulting dataFrame
    *
    * TODO: Figure out what 'max' is doing
    * Warning: Queries below may not be producing correct output
    */
  def rankByMetricLow(
      spark: SparkSession,
      fullDS: DataFrame,
      metric: String,
      op: String = "avg"
  ): DataFrame = {
    import spark.implicits._
    //fullDS.show()
    var oneTimeMetric: DataFrame = spark.emptyDataFrame
    op match {
      case "avg" =>
        oneTimeMetric = fullDS
          .select(
            $"region",
            $"date",
            $"country",
            round(col(metric)) as metric
          )
          .distinct()
          .groupBy("region")
          .agg(round(avg(s"$metric")).cast("integer") as s"$metric")
          .sort(col(metric) desc)
      // finds max 'metric' in each  country, region grouping
      // then groups on region and sums the 'max'
      case "max" =>
        oneTimeMetric = fullDS
          .select($"region", $"date", $"country", col(metric))
          .distinct()
          .groupBy("region", "country")
          .agg(max(s"$metric") as metric)
          .groupBy("region")
          .agg(round(sum(s"$metric")).cast("integer") as s"$metric")
          .sort(col(metric) desc)
      case "pop" =>
        //fullDS.select($"region", $"population").distinct().show()
        oneTimeMetric = fullDS
          .select(
            $"region",
            $"date",
            $"country",
            $"population",
            round(col(metric)) as metric
          )
          .dropDuplicates("country", "region", "date")
          .distinct()
          .groupBy($"region")
          .agg((avg(s"$metric") / max($"population")) as "avg_new_cases")
      case "maxpop" =>
        oneTimeMetric = fullDS
          .select(
            $"region",
            $"date",
            $"country",
            $"population",
            round(col(metric)) as metric
          )
          .dropDuplicates("country", "region", "date")
          .distinct()
          .groupBy($"region")
          .agg(
            max(s"$metric") / max(
              $"population"
            ) as s"${metric}_per_million"
          )
    }
    oneTimeMetric
  }

  /** calculates GDP percent change in each region
    * years used were 2019 & 2020
    *
    * @param spark spark session
    * @param fullDS dataFrame from question 1('data') that has been grouped by region
    *               with new population field added
    *               NOTE: original dataFrame is the joined 'econDF' and 'casesDF'
    * @param metric gdp metric
    * @param percapita Boolean flag (*** NOT USED ANYWHERE)
    */
  def changeGDP(
      spark: SparkSession,
      fullDS: DataFrame,
      metric: String,
      percapita: Boolean
  ): DataFrame = {
    import spark.implicits._

    val gdp_temp = fullDS
      .select(
        $"country",
        $"region",
        col(metric) as "gdp",
        $"year"
      )
      .cache()

    val gdp_2020 = gdp_temp
      .where($"year" === 2020)
      .select($"country", $"region", $"gdp" as "gdp20")
      .distinct()

    val gdp_2019 = gdp_temp
      .where($"year" === 2019)
      .distinct()
      .select($"country", $"region", $"gdp" as "gdp19")

    gdp_2020
      .join(gdp_2019, Seq("country", "region"))
      .withColumn(
        "Delta GDP (%)",
        (($"gdp20" - $"gdp19") / $"gdp20") * 100
      )
      .groupBy("region")
      .agg(avg("Delta GDP (%)"))

    //rankByMetric(spark, gdpPercentChange, "delta_gdp_percentChange")
  }
}
