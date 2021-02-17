name := "TwitterCovid19PercentageAnalysis"

version := "1"

scalaVersion := "2.12.12"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "3.0.1",
  "org.apache.spark" %% "spark-sql" % "3.0.1",
  "org.apache.logging.log4j" %% "log4j-api-scala" % "12.0",
  "org.apache.logging.log4j" % "log4j-core" % "2.13.0" % Runtime,
  "org.scalactic" %% "scalactic" % "3.2.2",
  "org.scalatest" %% "scalatest" % "3.2.2" % "test",
  "com.amazonaws" % "aws-java-sdk" % "1.7.4",
  "org.apache.hadoop" % "hadoop-aws" % "2.7.1"
)