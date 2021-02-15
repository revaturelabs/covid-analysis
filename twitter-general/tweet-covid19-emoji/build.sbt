name := "TweetCovid19Emoji"

version := "1"

scalaVersion := "2.12.12"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "3.0.1",
  "org.apache.spark" %% "spark-sql" % "3.0.1",
  "org.apache.logging.log4j" %% "log4j-api-scala" % "12.0",
  "org.scalatest" %% "scalatest" % "3.2.2" % "test",
  "com.vdurmont" % "emoji-java" % "5.1.1",
  "org.apache.logging.log4j" % "log4j-core" % "2.13.0" % Runtime
)

assemblyMergeStrategy in assembly := {
 case PathList("META-INF", xs @ _*) => MergeStrategy.discard
 case x => MergeStrategy.first
}