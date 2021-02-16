package com.revature.scalawags.project3.herdimmunity

import java.io.{BufferedWriter, IOException, FileWriter, PrintWriter}

import scala.language.postfixOps
import sys.process._

import com.github.nscala_time.time.Imports._

import com.revature.scalawags.project3.herdimmunity.CSVPullerAndParser._
import com.revature.scalawags.project3.herdimmunity.HerdImmunity._

object Main {
  /** Writes vaccine data and herd immunity date to local file for syncing with 
    * Amazon S3 bucket.
    */
  def printResults(analysis: AnalysisData, date: String) {
    try { 
      val fileName = "rollingResults.txt"
      val fileWriter = new FileWriter(s"output/$fileName",true)
      val writer = new PrintWriter(fileWriter)
      val finalResults = s"As of ${DateTime.now} (People Fully Vaccinated: ${analysis.peopleFullyVaccinated}, Date Of HerdImmunity: $date)"
      writer.println(finalResults)
      writer.close()
    } catch {
      case e: IOException => e.printStackTrace()
    }
  }
    
  /** Pulls and parses data from Our World in Data's COVID-19 dataset, calculates
    * the expected date the United States will achieve herd immunity, and appends
    * the output to an object in Amazon S3.
    */
  def main(args: Array[String]) {
    pullCDCCSV()
    val analysis = parseCDCCSV()
    val daysOption = daysRemaining(analysis)
    val days = daysOption match {
      case Some(v) => v
      case None =>
        println("Daily Vaccinations is 0, meaning herd immunity can't be achieved." +
          "Aborting program.")
        sys.exit()
    }
    val dateString = prettyDate(exactDate(days))
    printResults(analysis,dateString)
    "aws s3 sync ./output s3://covid-analysis-p3/datawarehouse/herdimmunity/" !!      
  }
}