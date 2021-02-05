scalaVersion := "2.12.10"

name := "us-age-spikes"
organization := "com.revature.michael"
version := "1.0"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.2" % Test,
  "org.apache.spark" %% "spark-sql" % "3.0.1",
)
