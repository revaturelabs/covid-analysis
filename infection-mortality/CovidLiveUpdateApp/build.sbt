name := "CovidLiveUpdate"

version := "1"

libraryDependencies  ++= Seq(
  "org.apache.spark" %% "spark-sql" % "3.0.1",
  "org.apache.httpcomponents" % "httpclient" % "4.5.12",
  "org.jsoup" % "jsoup" % "1.13.1",
  "org.scalaj" %% "scalaj-http" % "2.3.0",
  "commons-io" % "commons-io" % "2.8.0",
  "org.scalatest" %% "scalatest" % "3.2.2" % Test
)

scalaVersion := "2.12.13"
trapExit := false