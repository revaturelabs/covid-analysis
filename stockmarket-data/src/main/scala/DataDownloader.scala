import sys.process._
import scala.language.postfixOps
import scala.io.Source
import java.io.PrintWriter
import scala.collection.mutable.ArrayBuffer
import java.time.LocalDate.now
import java.time.format.DateTimeFormatter


object DataDownloader{
    case class CompositeIndexOfCountry(region: String, nation: String, indexName: String, tickerSymbol: String, countryCode: String, dataSource: String)

    val startDate = "02/17/2020"
    val endDate = "02/03/2021"

    def main(args: Array[String]){
        val compositeIndexListFile = "./CompositeIndexList.csv"
        for(i <- urlBuilder(compositeIndexListFile)){
            val file = s"curl ${i(2)}" !!
            val writer = new PrintWriter(s"./datalake/${i(0)}_${i(1)}_CompositeIndex.csv")
            writer.print(file)
            writer.close()
        }
        urlBuilder(compositeIndexListFile)
    }

    def urlBuilder(compositeIndexListFile: String): Array[Array[String]] ={
        import sys.process._
        val lines = Source.fromFile(compositeIndexListFile).getLines().toArray.drop(1)
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