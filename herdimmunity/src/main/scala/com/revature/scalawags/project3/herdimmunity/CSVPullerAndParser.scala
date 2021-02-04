package com.revature.scalawags.project3.herdimmunity

import com.github.nscala_time.time.Imports._
import java.io.PrintWriter
import scala.collection.mutable.ArrayBuffer
import scala.language.postfixOps
import sys.process._





object CSVPullerAndParser {


    def pullCDCCSV(): Unit={
        val fileUrl= "curl https://covid.ourworldindata.org/data/owid-covid-data.csv" !!
        val writer = new PrintWriter("tmp.csv")
        writer.print(fileUrl)
        writer.close()
    }

    def parseCDCCSV(file:String = "./tmp.csv"): AnalysisData={    
        val testFile = scala.io.Source.fromFile(file).getLines()
        val dataModels = new ArrayBuffer[AnalysisData]()
        
        for (line <- testFile){
            val splitLine = line.split(",")
            
            if (splitLine(0) == "USA"){    
                var date = splitLine(3).toDateTime
                
                var peopleVaccinated = splitLine(35)
                if (peopleVaccinated == ""){peopleVaccinated = "0.0"}
                var peopleVaccinatedDouble = peopleVaccinated.toDouble
                var peopleVaccinatedToInt = peopleVaccinatedDouble.toInt
                
                var peopleFullyVaccinated = splitLine(36)
                if (peopleFullyVaccinated == ""){peopleFullyVaccinated = "0.0"}
                var peopleFullyVaccinatedDouble = peopleFullyVaccinated.toDouble
                var peopleFullyVaccinatedToInt = peopleFullyVaccinatedDouble.toInt
                
                var newVaccinationsSmoothed = splitLine(38)
                if (newVaccinationsSmoothed == ""){newVaccinationsSmoothed = "0.0"}
                var newVaccinationsSmoothedDouble = newVaccinationsSmoothed.toDouble
                var newVaccinationsSmoothedToInt = newVaccinationsSmoothedDouble.toInt
                
                var population = splitLine(44)
                if (population == ""){population = "0.0"}
                var populationDouble = population.toDouble
                var poplulationToInt = populationDouble.toInt
                val analysis = new AnalysisData(date,peopleVaccinatedToInt,peopleFullyVaccinatedToInt,newVaccinationsSmoothedToInt,poplulationToInt)
                dataModels += analysis
            }  
        }
        dataModels.last
    }
}