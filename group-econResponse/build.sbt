name := "econ-group"

ThisBuild / version := "1.0"
ThisBuild / organization := "com.revatureData.groupEcon"
ThisBuild / scalaVersion := "2.12.13"

//lazy val commonSettings = Seq(
//  version := "1.0",
//  organization := "com.revatureData.groupEcon",
//  scalaVersion := "2.12.13"
//)

// Projects:

lazy val global = project
  .in(file("."))
  .settings(settings)
  .aggregate(
    Utilities,
    CovidResponse,
    CorrelateInfectionGDP
  )
//    assembly / assemblyJarName :=  name.value + ".jar"

lazy val Utilities = project
  .settings(
    name := "Utilities",
    settings,
    libraryDependencies ++= commonDependencies ++ Seq(
      dependencies.aws
    )
  )

lazy val CovidResponse = project
  .settings(
    name := "CovidResponse",
//    settings,
    libraryDependencies ++= commonDependencies ++ Seq(
      dependencies.logCore,
      dependencies.logScala
    )
  )
  .dependsOn(
    Utilities
  )
lazy val CorrelateInfectionGDP = project
  .settings(
    name := "CorrelateInfectionGDP",
    settings,
    libraryDependencies ++= commonDependencies ++ Seq(
      dependencies.logCore,
      dependencies.logScala
    )
  )
  .dependsOn(
    Utilities
  )

lazy val commonDependencies = Seq(
  dependencies.spark,
  dependencies.scalaTest,
  dependencies.mrPowers,
)

lazy val compilerOptions = Seq(
  "-unchecked",
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-language:postfixOps",
  "-deprecation",
  "-encoding",
  "utf8"
)
// Settings:
lazy val settings = Seq(scalacOptions ++= compilerOptions)


//lazy val assemblySettings = Seq(
//  assemblyJarName in assembly := name.value + ".jar",
//)

lazy val dependencies =
  new {
    val aws = "com.amazonaws" % "aws-java-sdk" % "1.3.32"
    val spark = "org.apache.spark" %% "spark-sql" % "3.0.1"
    val scalaTest = "org.scalatest" %% "scalatest" % "3.2.2" % "test"
    val mrPowers = "com.github.mrpowers" %% "spark-fast-tests" % "0.21.3" % "test"
    val logCore = "org.apache.logging.log4j" % "log4j-core" % "2.13.0" % Runtime
    val logScala =  "org.apache.logging.log4j" %% "log4j-api-scala" % "12.0"
    val breezeViz = "org.scalanlp" %% "breeze-viz" % "1.1"
    val breeze = "org.scalanlp" %% "breeze" % "1.1"
  }




//libraryDependencies ++= Seq(
//  "org.scalatest" % "scalatest_2.10" % "2.1.0" % "test",
//  "org.apache.spark" %% "spark-sql" % "3.0.1",
//  "org.apache.logging.log4j" %% "log4j-api-scala" % "12.0",
//  "org.apache.logging.log4j" % "log4j-core" % "2.13.0" % Runtime,
//  "com.github.mrpowers" %% "spark-fast-tests" % "0.21.3" % "test",
//  "com.amazonaws" % "aws-java-sdk" % "1.3.32",
//  "org.scalanlp" %% "breeze-viz" % "1.1",
//  "org.scalanlp" %% "breeze" % "1.1"
//)