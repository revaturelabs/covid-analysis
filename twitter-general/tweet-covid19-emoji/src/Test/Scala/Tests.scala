package emojis

import org.scalatest.flatspec.AnyFlatSpec
import com.vdurmont.emoji._


/**
  * All tests are designed to fail as of now.
  *
  */
class SetSpec extends AnyFlatSpec {


  //Store the computations to be tested beforehand so we're not re-computing for every single test case.
  val mapToTest = Utilities.tweetcovid19emoji("test-data.txt")

  //The Emoji Count function should be a Map populated with all emojis found in the data, and their respective counts.
  "Utilities" should "Return a non-empty map of emojis and counts" in {
    assert(mapToTest.isEmpty)
  }

  //The Key values for the EmojiCount map should all be emojis.
  //Update this with a regex or library function that tests each key as an emoji.
  it should "Contain an emoji in the first element" in{
    assert(mapToTest.contains("❤"))
  }

  //The EmojiCount Map shold not contain any non-Emoji characters in the Keys. 
  it should "omit any non-emoji characters" in {
    assert(!mapToTest.contains("covid")) //Replace this with a regex encompassing unicode outside Emojis?
  }
}