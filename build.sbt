import scala.collection.JavaConverters._
import java.lang.management.ManagementFactory

Compile / herokuAppName := "webpush-scala"

scalaVersion := "2.13.16"

name := "webpush-scala"

libraryDependencies ++= (
  ("nl.martijndwars" % "web-push" % "4.0.0") ::
  ("com.github.xuwei-k" %% "play-json-extra" % "0.9.0") ::
  ("org.webjars" % "clipboard.js" % "2.0.11") ::
  ("org.webjars" % "jquery" % "3.7.1") ::
  ("org.webjars" %% "webjars-play" % "3.0.2") ::
  guice ::
  Nil
)

Test / fork := true
run / fork := true

scalacOptions ++= (
  "-deprecation" ::
  "-unchecked" ::
  "-language:existentials" ::
  "-language:higherKinds" ::
  "-language:implicitConversions" ::
  Nil
)

enablePlugins(PlayScala)

javaOptions ++= ManagementFactory.getRuntimeMXBean.getInputArguments.asScala.toList.filter(
  a => Seq("-Xmx", "-Xms", "-XX").exists(a.startsWith)
)

licenses := Seq("MIT License" -> url("https://opensource.org/licenses/MIT"))
