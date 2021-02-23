scalaVersion := "2.12.10"

name := "stock_market_data_downloader"
organization := "com.revature"
version := "1.0"


libraryDependencies  ++= Seq(
    "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2",
    "org.scalatest" %% "scalatest" % "3.2.2" % Test,
    "com.amazonaws" % "aws-java-sdk" % "1.3.32",
    "org.apache.spark" %% "spark-sql" % "3.0.1"
)