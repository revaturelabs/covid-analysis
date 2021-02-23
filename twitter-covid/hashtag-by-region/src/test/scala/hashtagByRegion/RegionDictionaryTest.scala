package hashtagByRegion

import org.scalatest.flatspec.AnyFlatSpec

class RegionDictionaryTest extends AnyFlatSpec {

  // Test to ensure that reverseMapSearch with input "Chile" returns "South America"
  "reverseMapSearch" should "return \"South America\" for input: \"Chile\"" in {
    assert (RegionDictionary.reverseMapSearch("Chile") == "South America")
  }

  // Test to ensure that reverseMapSearch with input "Robo-Hungarian Empire," a fictional country, returns "Country Not Found"
  it should "return \"Country Not Found\" for input: \"Robo-Hungarian Empire\"" in {
    assert(RegionDictionary.reverseMapSearch("Robo-Hungarian Empire") == "Country Not Found")
  }
}