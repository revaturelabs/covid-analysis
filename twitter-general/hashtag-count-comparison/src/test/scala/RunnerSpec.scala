package HashtagCountComparison

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalamock.scalatest.MockFactory


class RunnerSpec extends AnyFlatSpec with Matchers with DataFrameSuiteBase{
  
 val sqlContext = sqlContext
  
  //Tests for getInputPath()
    "getInputPath case 0" should "return the path to s3" in {
    Runner.getInputPath(0) shouldEqual "s3://covid-analysis-p3/twitter-general/data-lake/Dec_11-Dec_25/"
  }

  "getInputPath case 1" should "return the path to s3" in {
    Runner.getInputPath(1) shouldEqual "s3://covid-analysis-p3/twitter-general/data-lake/Dec_26-Jan_05/"
  }

  "getInputPath case 2" should "return the path to s3" in {
    Runner.getInputPath(2) shouldEqual "s3://covid-analysis-p3/twitter-general/data-lake/Jan_06-Pres/"
  }

  //tests for readToDF()
  "readToDF case 0" should "return a DataFrame containint the input" in {
    
  }



}