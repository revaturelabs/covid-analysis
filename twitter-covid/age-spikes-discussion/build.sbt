name := "age-spikes-discussion"

version := "0.1"

scalaVersion := "2.12.10"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.2" % Test
libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.0.1"

libraryDependencies += "org.apache.hadoop" % "hadoop-common" % "3.0.0"
libraryDependencies += "org.apache.hadoop" % "hadoop-client" % "3.0.0"
libraryDependencies += "org.apache.hadoop" % "hadoop-aws" % "3.0.0"