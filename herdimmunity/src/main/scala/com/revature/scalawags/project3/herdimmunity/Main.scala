package com.revature.scalawags.project3.herdimmunity

import com.revature.scalawags.project3.herdimmunity.CSVPullerAndParser._
import com.revature.scalawags.project3.herdimmunity.HerdImmunity._
import java.io.PrintWriter
import java.io.BufferedWriter
import sys.process._
import scala.language.postfixOps
import com.github.nscala_time.time.Imports._
import java.io.FileWriter
import java.io.IOException


object Main {
    
    def printResults(analysis: AnalysisData, date: String){
        try{ 
            val fileName = "rollingResults.txt"
            val fileWriter = new FileWriter(s"output/$fileName",true)
            val writer = new PrintWriter(fileWriter)
            val finalResults = s"As of ${DateTime.now} (People Fully Vaccinated: ${analysis.peopleFullyVaccinated}, Date Of HerdImmunity: $date)"
            writer.println(finalResults)
            writer.close()
        }catch {
            case e: IOException => e.printStackTrace()
        }
    }
    
    
    def main(args: Array[String]){
        pullCDCCSV()
        val analysis = parseCDCCSV()
        val days = daysRemaining(analysis.population,analysis.peopleFullyVaccinated,analysis.newVaccinationsSmoothed)
        val dateString = prettyDate(exactDate(days))
        printResults(analysis,dateString)
        "aws s3 sync ./output s3://covid-analysis-p3/datawarehouse/herdimmunity/" !!
        
    }



}