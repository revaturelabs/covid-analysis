name := "TwitterSentimentAnalysis"

version := "1"

scalaVersion := "2.11.12"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest-flatspec" % "3.2.2" % "test",
  "org.apache.spark" %% "spark-core" % "2.4.7",
  "org.apache.spark" %% "spark-mllib" % "2.4.7",
  "org.apache.spark" %% "spark-sql" % "2.4.7",
  // https://mvnrepository.com/artifact/com.johnsnowlabs.nlp/spark-nlp
  "com.johnsnowlabs.nlp" %% "spark-nlp" % "2.7.2", 
  "org.apache.logging.log4j" %% "log4j-api-scala" % "12.0",
  "org.apache.logging.log4j" % "log4j-core" % "2.13.0" % Runtime
)