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
  "org.apache.logging.log4j" % "log4j-core" % "2.13.0" % Runtime,
  //for s3
  "org.apache.hadoop" % "hadoop-aws" % "3.2.0",
  "com.amazonaws" % "aws-java-sdk-bundle" % "1.11.375",
  "org.apache.hadoop" % "hadoop-hdfs" % "3.2.0",
  "org.apache.hadoop" % "hadoop-common" % "3.2.0"
)

dependencyOverrides ++= {
  Seq(
    "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.8.1",
    "com.fasterxml.jackson.core" % "jackson-databind" % "2.8.1",
  )
}

assemblyMergeStrategy in assembly := {
 case PathList("META-INF", xs @ _*) => MergeStrategy.discard
 case x => MergeStrategy.first
}
