package com.revature.scalawags.project3.herdimmunity

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers._
import com.revature.scalawags.project3.herdimmunity.CSVPullerAndParser.AnalysisData
import org.scalatest.matchers.should.Matchers



class CSVParserTester extends AnyFlatSpec with Matchers {
    
    val result = CSVPullerAndParser.parseCDCCSV("./test.csv")
    "parseCDCCSV" should "returns \"2020-03-26\",0.0,0.0,0.0,3.31002647E8" in {
    assert(result == AnalysisData("2020-03-26",0.0,0.0,0.0,3.31002647E8))
    }
}