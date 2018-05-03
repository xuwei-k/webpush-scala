scalaVersion := "2.12.6"

name := "webpush-scala"

libraryDependencies ++= (
  ("nl.martijndwars" % "web-push" % "3.0.1").exclude("org.apache.httpcomponents", "fluent-hc") ::
  ("com.github.xuwei-k" %% "play-json-extra" % "0.5.0") ::
  ("org.webjars" % "clipboard.js" % "1.7.1") ::
  ("org.webjars" % "jquery" % "3.2.1") ::
  ("org.webjars" %% "webjars-play" % "2.6.2") ::
  guice ::
  Nil
)

fork in Test := true
fork in run := true

scalacOptions ++= (
  "-deprecation" ::
  "-unchecked" ::
  "-language:existentials" ::
  "-language:higherKinds" ::
  "-language:implicitConversions" ::
  "-Yno-adapted-args" ::
  Nil
)

enablePlugins(PlayScala)

javaOptions ++= sys.process.javaVmArguments.filter(
  a => Seq("-Xmx", "-Xms", "-XX").exists(a.startsWith)
)

licenses := Seq("MIT License" -> url("https://opensource.org/licenses/MIT"))
