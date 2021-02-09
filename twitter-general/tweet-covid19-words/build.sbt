name := "TweetCovid19Words"

version := "1"

scalaVersion := "2.12.12"

artifactName := { (sv: ScalaVersion, module: ModuleID, artifact: Artifact) =>
  "twitter-general-word-count." + artifact.extension
}

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "3.0.1",
  "org.apache.spark" %% "spark-sql" % "3.0.1",
  "org.apache.logging.log4j" %% "log4j-api-scala" % "12.0",
  "org.apache.logging.log4j" % "log4j-core" % "2.13.0" % Runtime,
  "org.scalatest" %% "scalatest" % "3.2.2" % "test",
)