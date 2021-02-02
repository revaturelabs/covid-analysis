package HashtagCountComparison

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalamock.scalatest.MockFactory


class RunnerSpec extends AnyFlatSpec with Matchers with MockFactory{
  
  val testMap =  Map[String,Int](("CoronavirusUpdate",1),("PartyGordo",1),("SlimeRancher",1))
  
    "getInputPath case 0" should "return the path to s3" in {
    Runner.getInputPath(0) shouldEqual "s3://covid-analysis-p3/twitter-general/data-lake/Dec_11-Dec_25/"
  }

  "getInputPath case 1" should "return the path to s3" in {
    Runner.getInputPath(1) shouldEqual "s3://covid-analysis-p3/twitter-general/data-lake/Dec_26-Jan_05/"
  }

  "getInputPath case 2" should "return the path to s3" in {
    Runner.getInputPath(2) shouldEqual "s3://covid-analysis-p3/twitter-general/data-lake/Jan_06-Pres/"
  }

  "hashtagCountComparison case 0" should "return a map with Hashtags and corresponding counts" in {
    assert(Runner.hashtagCountComparison("test-data.json").equals(testMap))
  }



}