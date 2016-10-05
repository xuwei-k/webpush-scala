scalaVersion := "2.11.8"

name := "webpush-scala"

libraryDependencies ++= (
  ("com.github.xuwei-k" %% "play-json-extra" % "0.4.0") ::
  ("org.webjars" % "clipboard.js" % "1.5.5") ::
  ("org.webjars" % "jquery" % "3.1.0") ::
  ("org.webjars" %% "webjars-play" % "2.5.0-3") ::
  Nil
)

fork in Test := true
fork in run := true

scalacOptions ++= (
  "-deprecation" ::
  "-unchecked" ::
  "-Xlint" ::
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

dependsOn(
  uri("git://github.com/xuwei-k/web-push.git#b87fb75e50c237cb1633c792a5241a0bff06d39d")
)
