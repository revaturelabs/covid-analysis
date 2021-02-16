scalaVersion := "2.12.10"

name := "stock_market_composite_index_change_calculator"
organization := "com.revature"
version := "1.0"

libraryDependencies  ++= Seq(
  "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2",
  "org.scalatest" %% "scalatest" % "3.2.2" % Test,
  "org.apache.spark" %% "spark-sql" % "3.0.1",
  "com.github.nscala-time" %% "nscala-time" % "2.26.0"
)