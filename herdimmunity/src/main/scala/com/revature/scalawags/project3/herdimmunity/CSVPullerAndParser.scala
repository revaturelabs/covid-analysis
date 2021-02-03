package com.revature.scalawags.project3.herdimmunity

import sys.process._
import scala.language.postfixOps
import java.io.PrintWriter
import scala.collection.mutable.ArrayBuffer



object CSVPullerAndParser  extends App{

    case class AnalysisData(date: String, peopleVaccinated: Double, peopleFullyVaccinated: Double, newVaccinationsSmoothed: Double, population: Double)

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
                var date = splitLine(3)
                
                var peopleVaccinated = splitLine(35)
                if (peopleVaccinated == ""){peopleVaccinated = "0.0"}
                var peopleVaccinatedToDouble = peopleVaccinated.toDouble
                
                var peopleFullyVaccinated = splitLine(36)
                if (peopleFullyVaccinated == ""){peopleFullyVaccinated = "0.0"}
                var peopleFullyVaccinatedToDouble = peopleFullyVaccinated.toDouble
                
                var newVaccinationsSmoothed = splitLine(38)
                if (newVaccinationsSmoothed == ""){newVaccinationsSmoothed = "0.0"}
                var newVaccinationsSmoothedToDouble = newVaccinationsSmoothed.toDouble
                
                var population = splitLine(44)
                if (population == ""){population = "0.0"}
                var poplulationToDouble = population.toDouble
                val analysis = new AnalysisData(date,peopleVaccinatedToDouble,peopleFullyVaccinatedToDouble,newVaccinationsSmoothedToDouble,poplulationToDouble)
                dataModels += analysis
            }  
        }
        dataModels.last
    }

    pullCDCCSV()
}