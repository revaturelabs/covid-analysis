package com.revature.scalawags.project3.herdimmunity

import sys.process._
import java.net.URL
import java.io.File
import scala.language.postfixOps
import java.io.PrintWriter
import scala.collection.mutable.ArrayBuffer

object CSVPullerAndParser  extends App{
    def pullCDCCSV(): Tuple5[Array[String], Array[Double], Array[Double], Array[Double], Array[Double]]={
        val fileUrl= "curl https://covid.ourworldindata.org/data/owid-covid-data.csv?v=2021-02-01" !!
        val writer = new PrintWriter("tmp.csv")
        writer.print(fileUrl)
        writer.close()
        val file = scala.io.Source.fromFile("./tmp.csv").getLines()
        val dates = new ArrayBuffer[String]()
        val peopleVaccinated = new ArrayBuffer[Double]()
        val peopleFullyVaccinated = new ArrayBuffer[Double]()
        val newVaccinationsSmoothed = new ArrayBuffer[Double]()
        val populations = new ArrayBuffer[Double]()
        
        for (line <- file){
            val splitLine = line.split(",")
            
            if (splitLine(0) == "USA"){    
                var date = splitLine(3)
                dates += date
                
                var peopleVaccinated = splitLine(35)
                if (peopleVaccinated == ""){peopleVaccinated = "0.0"}
                var peopleVaccinatedToDouble = peopleVaccinated.toDouble
                peopleVaccinated += peopleVaccinatedToDouble
                
                var peopleFullyVaccinated = splitLine(36)
                if (peopleFullyVaccinated == ""){peopleFullyVaccinated = "0.0"}
                var peopleFullyVaccinatedToDouble = peopleFullyVaccinated.toDouble
                peopleFullyVaccinated += peopleFullyVaccinatedToDouble
                
                var newVaccinationsSmoothed = splitLine(38)
                if (newVaccinationsSmoothed == ""){newVaccinationsSmoothed = "0.0"}
                var newVaccinationsSmoothedToDouble = newVaccinationsSmoothed.toDouble
                newVaccinationsSmoothed += newVaccinationsSmoothedToDouble
                
                var population = splitLine(44)
                if (population == ""){population = "0.0"}
                var poplulationToDouble = population.toDouble
                populations += poplulationToDouble
            }  
        }
        var dataSet = Tuple5(dates.toArray, peopleVaccinated.toArray, peopleFullyVaccinated.toArray, 
                             newVaccinationsSmoothed.toArray, populations.toArray)
        dataSet 
    }

    pullCDCCSV()
}