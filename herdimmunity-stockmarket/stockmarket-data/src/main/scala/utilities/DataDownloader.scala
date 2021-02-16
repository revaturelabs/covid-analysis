package utilities

import sys.process._
import scala.language.postfixOps
import scala.io.Source
import java.io.{PrintWriter, File}
import scala.collection.mutable.ArrayBuffer
import java.time.LocalDate.now
import java.time.format.DateTimeFormatter
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.functions._
import org.apache.log4j._

/**
  * A simple data downloader that programmatically downloads stock market composite index data to a local file system and uploads them to an AWS S3 bucket.
  */

object DataDownloader{
    case class CompositeIndexOfCountry(region: String, nation: String, indexName: String, tickerSymbol: String, countryCode: String, dataSource: String)

    // Period that this program will pull data from for Project 3.
    val startDate = "02/17/2020"
    val endDate = "02/14/2021"

    def main(args: Array[String]){
        val log4jConfPath = "./src/main/resources/log4j.properties"
        PropertyConfigurator.configure(log4jConfPath)

        // The program refers to the CompositeIndexList.csv file in order to get information about each country's name, region, an index name, a ticker symbol, a country code, and a data source.
        val pathToCompositeIndexList = "./CompositeIndexList.csv"

        for(i <- urlBuilder(pathToCompositeIndexList)){
            // After a complete url is built, it curls an actual csv file from a web and assigns the content to "csvFile".
            val csvFile = s"curl ${i(2)}" !!

            // File class is created as a placeholder.
            val file = new File(s"./datalake/${i(0)}_${i(1)}_CompositeIndex.csv")
            val fileName = s"${i(0)}_${i(1)}_CompositeIndex.csv"

            // Creates a new csv file under the datalake directory and names each file using a region name and a nation name.
            val writer = new PrintWriter(file)

            // Writes the curled csvFile to the newly created file under the datalake directory.
            writer.print(csvFile)
            writer.close()

            //Upload the newly file to an AWS S3 bucket programmatically.
            s3DAO.apply().uploadFile(file, fileName)
        }
    }

  /** Takes in a path to CompositeIndexList.csv as a parameter,
    * reads the CompositeIndexList.csv file line by line,
    * pulls information about each country's name, region, an index name, a ticker symbol, a country code, and a data source from the csv file,
    * saves them into a case class,
    * completes building a url for downloading each country's stock market composite index,
    * and returns an array of an array that contains each country's region, name, and a complete url.
    *
    * @param pathToCompositeIndexList a path to CompositeIndexList.csv
    */
    def urlBuilder(pathToCompositeIndexList: String): Array[Array[String]] ={
        val lines = Source.fromFile(pathToCompositeIndexList).getLines().toArray.drop(1)
        var arrayOfURLs = ArrayBuffer[Array[String]]()

        for(i <- lines){
            val content = i.split(",")
            val contentToCaseClass = new CompositeIndexOfCountry(content(0), content(1), content(2), content(3), content(4), content(5))
            if(contentToCaseClass.dataSource.equals("MarketWatch")){
                val completeURL = s"https://www.marketwatch.com/investing/index/${contentToCaseClass.tickerSymbol.toLowerCase()}/downloaddatapartial?partial=true" +
                        s"&startdate=${startDate}%2000:00:00&enddate=${endDate}%2000:00:00&daterange=d30&frequency=p1d&csvdownload=true" +
                        s"&downloadpartial=false&newdates=false&countrycode=${contentToCaseClass.countryCode.toLowerCase()}"
                        arrayOfURLs += Array(contentToCaseClass.region, contentToCaseClass.nation, completeURL)
            }
        }
        return arrayOfURLs.toArray
    }
}