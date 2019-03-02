lazy val byrdeVersion = "250"

lazy val byrde =
  Seq(
    "org.byrde" %% "akka-http" % byrdeVersion)

lazy val logback =
  "ch.qos.logback" % "logback-classic" % "1.2.3"

lazy val root =
  (project in file("."))
  .settings(
    inThisBuild(List(
      organization    := "org.byrde",
      scalaVersion    := "2.12.8")),
    name := "akka-http-seed",
    resolvers +=
      ("byrdelibraries" at "https://dl.cloudsmith.io/public/byrde/libraries/maven/"),
    libraryDependencies ++=
      byrde :+ logback,
    scalacOptions ++=
      Seq(
        "-unchecked",
        "-deprecation",
        "-Xlint",
        "-Ywarn-dead-code",
        "-language:_",
        "-target:jvm-1.8",
        "-encoding", "UTF-8")).enablePlugins(JavaAppPackaging)
