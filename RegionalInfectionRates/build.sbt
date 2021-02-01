name := "CovidLiveUpdate"

version := "1"

libraryDependencies  ++= Seq(
  "org.scalanlp" %% "breeze" % "1.1",
  "org.scalanlp" %% "breeze-natives" % "1.1",
  "org.scalanlp" %% "breeze-viz" % "1.1",
  "org.apache.spark" %% "spark-sql" % "3.0.1",
  "org.apache.bahir" %% "spark-streaming-twitter" % "2.4.0",
  "org.apache.httpcomponents" % "httpclient" % "4.5.12",
  "commons-io" % "commons-io" % "2.8.0",
  "org.jsoup" % "jsoup" % "1.13.1",
  "org.scalaj" %% "scalaj-http" % "2.3.0"
)

scalaVersion := "2.12.10"