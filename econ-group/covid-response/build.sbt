name := "covid-response"

version := "1.0"
organization := "com.revatureData.groupEcon"
scalaVersion := "2.12.13"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-sql" % "3.0.0",
  "org.apache.logging.log4j" %% "log4j-api-scala" % "12.0",
  "org.apache.logging.log4j" % "log4j-core" % "2.13.0" % Runtime,
  "com.github.mrpowers" %% "spark-fast-tests" % "0.21.3" % "test",
  "com.amazonaws" % "aws-java-sdk" % "1.3.32",
  "org.scalanlp" %% "breeze-viz" % "1.1",
  "org.scalanlp" %% "breeze" % "1.1"
)