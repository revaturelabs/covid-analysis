package com.revature.scalawags.project3.herdimmunity

import com.github.nscala_time.time.Imports._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers._
import org.scalatest.matchers.should.Matchers

class CSVParserTester extends AnyFlatSpec with Matchers {
  val result = CSVPullerAndParser.parseCDCCSV("./test.csv")

  "parseCDCCSV" should "returns \"2020-03-26\", 0, 0, 0, 331002647" in {
    assert(result == AnalysisData("2020-03-26".toDateTime, 0, 0, 0, 331002647))
  }
}