package relatedHashtags

import org.scalatest.flatspec.AnyFlatSpec

class CovidTermsListTest extends AnyFlatSpec {

  // Test that getTermsList returns a list of 437 strings
  "getTermsList" should "return a List for 437 Strings" in {
    assert(CovidTermsList.getTermsList.size == 437)
  }

  // Test that getTermsList returns a list in which the first element is "Coronavirusmexico"
  it should "return a List in which the first element is \"Coronavirusmexico\"" in {
    assert(CovidTermsList.getTermsList(0) == "Coronavirusmexico")
  }
}