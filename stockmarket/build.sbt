scalaVersion := "2.12.10"

name := "hello-world"
organization := "ch.epfl.scala"
version := "1.0"

libraryDependencies  ++= Seq(
"org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2",
"org.scalatest" %% "scalatest" % "3.2.2" % Test,
"org.apache.spark" %% "spark-sql" % "3.0.1"
)