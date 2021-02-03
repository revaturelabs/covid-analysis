package HashtagCountComparison

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import org.scalamock.scalatest.MockFactory


class RunnerSpec extends AnyFlatSpec with Matchers{
  
 
  
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
  "readToDF case 0" should "return a DataFrame containing the input from path" in {
    
  }

  //tests for filterToHashtags
  "filterToHashtags case 0" should "return a DataFrame containing just hashtags" in {

  }

  //tests for groupByHashtags()
  "groupByHashtags case 0" should "return a DataFrame containing hashtags, and the number of times that hashtag appeared" in {

  }

  //tests for mapHashtags()
  "mapHashtags case 0" should "return a DataFrame contianing hashtags, the number of times that hashtag appears, and if it is covid related" in {

  }

  //tests for reduceToCategories()
  "reduceToCategories case 0" should "return a DataFrame containing covid and the number of covid hashtags, and non-covid and the number of non-covid hashtags" in {

  }


  //tests for ouput


}