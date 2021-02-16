scalaVersion := "2.12.10"

name := "COVID-ANALYSIS-1"
organization := "com.revature.michael"
version := "1.0"

libraryDependencies ++= Seq(
  // "org.scalatest" %% "scalatest" % "3.2.2" % Test,
  // "org.apache.spark" %% "spark-sql" % "3.0.1",
  "org.apache.spark" %% "spark-sql" % "3.0.1",
  "com.amazonaws" % "aws-java-sdk" % "1.11.948",
  "org.apache.hadoop" % "hadoop-aws" % "2.8.5",
  "org.scalatest" %% "scalatest" % "3.2.2" % Test,
  "com.outr" %% "scribe-slf4j" % "3.3.1",
  "com.outr" %% "scribe-file" % "3.3.1"
)
