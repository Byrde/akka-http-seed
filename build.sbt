lazy val akkaHttpVersion = "10.1.0"
lazy val akkaVersion    = "2.5.11"

lazy val dependencyInjectionLibraries =
  Seq(
    "com.google.inject.extensions" % "guice-assistedinject" % "4.1.0",
    "net.codingwell" %% "scala-guice" % "4.1.0")

lazy val jsonParsingLibrary =
  "com.typesafe.play" %% "play-json" % "2.6.9"

lazy val utils =
  Seq(
    "io.igl" %% "jwt" % "1.2.2",
    "org.byrde" %% "commons" % "166",
    "de.svenkubiak" % "jBCrypt" % "0.4.1",
    "com.typesafe.play" %% "play-ws" % "2.6.9")

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
      Seq(
        "com.typesafe.akka" %% "akka-http"            % akkaHttpVersion,
        "com.typesafe.akka" %% "akka-http-xml"        % akkaHttpVersion,
        "com.typesafe.akka" %% "akka-slf4j"           % akkaVersion,
        "com.typesafe.akka" %% "akka-stream"          % akkaVersion,
        "de.heikoseeberger" %% "akka-http-play-json"  % "1.17.0",
        "ch.qos.logback"    % "logback-classic"       % "1.2.3",

        "com.typesafe.akka" %% "akka-http-testkit"    % akkaHttpVersion % Test,
        "com.typesafe.akka" %% "akka-testkit"         % akkaVersion     % Test,
        "com.typesafe.akka" %% "akka-stream-testkit"  % akkaVersion     % Test,
        "org.scalatest"     %% "scalatest"            % "3.0.1"         % Test) ++ utils ++ dependencyInjectionLibraries :+ jsonParsingLibrary,
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
