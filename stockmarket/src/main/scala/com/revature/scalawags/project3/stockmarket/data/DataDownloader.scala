package com.revature.scalawags.project3.stockmarket.data

import sys.process._
import scala.language.postfixOps
import scala.io.Source
import java.io.PrintWriter
import scala.collection.mutable.ArrayBuffer
import java.time.LocalDate.now
import java.time.format.DateTimeFormatter


object DataDownloader extends App{
    case class CompositeIndexOfCountry(region: String, nation: String, indexName: String, tickerSymbol: String, countryCode: String, dataSource: String)

    final val startDate = "02/17/2020"
    final val dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy")
    final val currentDate = now().format(dateFormat)

    val compositeIndexListFile = "./CompositeIndexList.csv"

    for(i <- urlBuilder(compositeIndexListFile)){
        println(s"Region: ${i(0)}\n" +
          s"Nation: ${i(1)}\n" +
          s"URL: ${i(2)}\n\n")
    }

    def urlBuilder(compositeIndexListFile: String): Array[Array[String]] ={
        val lines = Source.fromFile(compositeIndexListFile).getLines().toArray.drop(1)
        var arrayOfURLs = ArrayBuffer[Array[String]]()

        for(i <- lines){
            val content = i.split(",")
            val contentToCaseClass = new CompositeIndexOfCountry(content(0), content(1), content(2), content(3), content(4), content(5))
            if(contentToCaseClass.dataSource.equals("MarketWatch")){
                    val completeURL = s"https://www.marketwatch.com/investing/index/${contentToCaseClass.tickerSymbol.toLowerCase()}/downloaddatapartial?partial=true" +
                        s"&startdate=${startDate}%2000:00:00&enddate=${currentDate}%2000:00:00&daterange=d30&frequency=p7d&csvdownload=true" +
                        s"&downloadpartial=false&newdates=false&countrycode=${contentToCaseClass.countryCode.toLowerCase()}"
                        arrayOfURLs += Array(contentToCaseClass.region, contentToCaseClass.nation, completeURL)
            }
            else if(contentToCaseClass.dataSource.equals("YahooFinance")){
                    val completeURL = s"https://query1.finance.yahoo.com/v7/finance/download/%5E${contentToCaseClass.tickerSymbol}" +
                        s"?period1=1581897600&period2=1612396800&interval=1wk&events=history&includeAdjustedClose=true"
                        arrayOfURLs += Array(contentToCaseClass.region, contentToCaseClass.nation, completeURL)
            }
        }
        return arrayOfURLs.toArray
    }
}