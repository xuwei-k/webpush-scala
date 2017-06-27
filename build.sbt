scalaVersion := "2.12.2"

name := "webpush-scala"

libraryDependencies ++= (
  // https://github.com/MartijnDwars/web-push/pull/28
  ("nl.martijndwars" % "web-push" % "3.0.0").exclude("org.apache.httpcomponents", "fluent-hc") ::
  ("com.github.xuwei-k" %% "play-json-extra" % "0.5.0") ::
  ("org.webjars" % "clipboard.js" % "1.6.1") ::
  ("org.webjars" % "jquery" % "3.2.0") ::
  ("org.webjars" %% "webjars-play" % "2.6.0-M1") :: // TODO https://github.com/webjars/webjars-play/pull/69
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
