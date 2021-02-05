name := "econ-response"

version := "1.0"
organization in ThisBuild := "com.revatureData.groupEcon"
scalaVersion in ThisBuild := "2.12.13"

// Projects:

lazy val global = project
  .in(file("."))
  .settings(settings)
  .disablePlugins(AssemblyPlugin)
  .aggregate(
    utilities,
    covidResponse,
    correlateInfectionGDP
  )

lazy val utilities = project
  .settings(
    name := "utilities",
    settings,
    libraryDependencies ++= commonDependencies
  )
  .disablePlugins(AssemblyPlugin)

lazy val covidResponse = project
  .settings(
    name := "CovidResponse",
    settings,
    assemblySettings,
    libraryDependencies ++= commonDependencies ++ Seq(
      dependencies.monocleCore,
      dependencies.monocleMacro
    )
  )
  .dependsOn(
    utilities
  )
lazy val correlateInfectionGDP = project
  .settings(
    name := "CorrelateInfectionGDP",
    settings,
    assemblySettings,
    libraryDependencies ++= commonDependencies ++ Seq(
      dependencies.monocleCore,
      dependencies.monocleMacro
    )
  )
  .dependsOn(
    utilities
  )

libraryDependencies ++= Seq(
  "org.scalatest" % "scalatest_2.10" % "2.1.0" % "test",
  "org.apache.spark" %% "spark-sql" % "3.0.0",
  "org.apache.logging.log4j" %% "log4j-api-scala" % "12.0",
  "org.apache.logging.log4j" % "log4j-core" % "2.13.0" % Runtime,
  "com.github.mrpowers" %% "spark-fast-tests" % "0.21.3" % "test",
  "com.amazonaws" % "aws-java-sdk" % "1.3.32",
  "org.scalanlp" %% "breeze-viz" % "1.1",
  "org.scalanlp" %% "breeze" % "1.1"
)