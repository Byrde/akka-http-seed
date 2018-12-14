lazy val byrdeVersion     = "242"

lazy val byrdeLibraries =
  Seq(
    "org.byrde" %% "akka-http" % byrdeVersion)

lazy val dependencyInjectionLibraries =
  Seq(
    "com.google.inject.extensions"  % "guice-assistedinject"  % "4.1.0",
    "net.codingwell"                %% "scala-guice"          % "4.1.0")

lazy val jsonParsingLibrary =
  "com.typesafe.play" %% "play-json" % "2.6.9"

lazy val utilsLibraries =
  Seq(
    "io.igl"            %% "jwt"      % "1.2.2",
    "com.typesafe.play" %% "play-ws"  % "2.6.9",
    "de.svenkubiak"     % "jBCrypt"   % "0.4.1")

lazy val root =
  (project in file("."))
  .settings(
    inThisBuild(List(
      organization    := "org.byrde",
      scalaVersion    := "2.12.6"
    )),
    name := "akka-http-seed",
    resolvers ++=
      Seq(
        "byrdelibraries" at "https://dl.cloudsmith.io/public/byrde/libraries/maven/",
        Resolver.bintrayRepo("hseeberger", "maven")),
    libraryDependencies ++=
      byrdeLibraries ++ utilsLibraries ++ dependencyInjectionLibraries :+ jsonParsingLibrary,
    scalacOptions ++=
      Seq(
        "-unchecked",
        "-deprecation",
        "-Xlint",
        "-Ywarn-dead-code",
        "-language:_",
        "-target:jvm-1.8",
        "-encoding", "UTF-8")
  )
