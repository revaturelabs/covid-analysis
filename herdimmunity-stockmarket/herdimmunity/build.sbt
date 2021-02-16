lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "com.revature.scalawags",
      scalaVersion := "2.13.3"
    )),
    name := "herdimmunity"
  )

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.2" % Test
libraryDependencies += "com.github.nscala-time" %% "nscala-time" % "2.26.0"
