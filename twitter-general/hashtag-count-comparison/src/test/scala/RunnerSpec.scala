

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalamock.scalatest.MockFactory
import org.apache.spark.sql.{SparkSession,Row}
import java.io.FileNotFoundException


class RunnerSpec extends AnyFlatSpec with Matchers{
  
  //create spark session and test DataFrame for the test suite
  val testSpark = SparkSession.builder().master("local").appName("Tester").getOrCreate()
  import testSpark.implicits._

  //expected Dataset[Tweets] after running readToDS(testSpark,"test-data.txt")
  val testDS = testSpark.read.format("text").load("test-data.txt").as[Runner.Tweets]
  
  //a Dataset[Tweets] that does not contain hashtags
  val noHashtagsDS = Seq(Runner.Tweets("there are no hashtags here")).toDS()
  
  //Dataset[Hashtag] filled with the expected hashtags from running makeHashtagDS() on testDS.
  val testHashtagDS = Seq(Runner.Hashtag("#tweet"),Runner.Hashtag("#covid"),Runner.Hashtag("#coronavirus"),Runner.Hashtag("#hashtag"),
                              Runner.Hashtag("#tweet"),Runner.Hashtag("#coronavirus"),Runner.Hashtag("#grilling"),Runner.Hashtag("#meat")).toDS()


  //test hashtags
  val covidHashtag = Runner.Hashtag("#covid")
  val nonCovidHashtag = Runner.Hashtag("#meat")

  
  //Tests for getInputPath()
    "getInputPath case 0" should "return the path to s3" in {
    Runner.getInputPath(0) shouldEqual "s3a://covid-analysis-p3/datalake/twitter-general/dec_11-dec_25/"
  }

  "getInputPath case 1" should "return the path to s3" in {
    Runner.getInputPath(1) shouldEqual "s3a://covid-analysis-p3/datalake/twitter-general/dec_26-jan_05/"
  }

  "getInputPath case 2" should "return the path to s3" in {
    Runner.getInputPath(2) shouldEqual "s3a://covid-analysis-p3/datalake/twitter-general/feb_03-feb_14/"
  }

  //tests for readToDF()
  /**
    * tests readToDs on a valid file path input
    */
  "readToDF case 0" should "return a DataFrame containing the input from path" in {
    val compDF = Runner.readToDS(testSpark,"test-data.txt")

    assert(compDF.collect()===(testDS.collect()))
  }

  //tests for makeHashtagDS()
  /**
    * tests makeHashtagDS on an input with multiple hashtags
    */
  "makeHashtagDS" should "return a Dataset[Hashtag]" in {
    val hashtagDS = Runner.makeHashtagDS(testDS,testSpark)

    assert(hashtagDS.collect()===testHashtagDS.collect())
  }

  /**
    * tests makeHashtagDS on an input with no hashtags
    */
  "makeHashtagDS" should "return an empty Dataset[Hashtag] when there are no hashtags in the input" in {
    val hashtagDS = Runner.makeHashtagDS(noHashtagsDS,testSpark)

    assert(hashtagDS.isEmpty)
  }

  //tests for markCovidRelated()

  /**
    * tests markCovidRelated on a covid related hashtag as input
    */
  "markCovidRelated()" should "return a new tweet continging 'covid' when a hashtag is covid related" in {
    val ht = Runner.markCovidRelated(covidHashtag,true)

    assert(ht.hashtag.equals("covid hashtags"))
  }

  /**
    * tests markCovidRelated on a non-covid related hashtag as input
    */
  "markCovidRelated()" should "return a new tweet containing 'non-covid' when a hashtag is not covid related" in {
    val ht = Runner.markCovidRelated(nonCovidHashtag,false)

    assert(ht.hashtag.equals(nonCovidHashtag.hashtag))
  }

  //tests for isCovidRelated()

  /**
    * tests isCovidRelated on a covid related hashtag as input
    */
  "isCovidRelated()" should "return true when a hashtag is covid related" in {
    assert(Runner.isCovidRelated(covidHashtag.hashtag))
  }

  /**
    * tests isCovidRelated on a non-covid related hashtag as input
    */
  "isCovidRelated()" should "return false when a hashtag is not covid related" in{
    assert(!(Runner.isCovidRelated((nonCovidHashtag.hashtag))))
  }
  

  //tests for ouput


}