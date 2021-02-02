package HashtagCountComparison

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers



class RunnerSpec extends AnyFlatSpec with Matchers {
  
  val testMap =  Map[String,Int](("Covid",1))
  
    "getInputPath case 0" should "return the path to s3" in {
    Runner.getInputPath(0) shouldEqual "s3path0"
  }

  "getInputPath case 1" should "return the path to s3" in {
    Runner.getInputPath(1) shouldEqual "s3path1"
  }

  "getInputPath case 2" should "return the path to s3" in {
    Runner.getInputPath(2) shouldEqual "s3path2"
  }

  "hashtagCountComparison case 0" should "return a map with Hashtags and corresponding counts" in {
    assert(Runner.hashtagCountComparison("s3path0").equals(testMap))
  }



}