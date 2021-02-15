package emojis

object Main extends App {
  println("Hello, World!")

  val map = Utilities.tweetcovid19emoji("test-data.txt")

  Utilities.printMap(map)

}