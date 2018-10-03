import scala.collection.JavaConverters._
import java.lang.management.ManagementFactory

scalaVersion := "2.12.7"

name := "webpush-scala"

libraryDependencies ++= (
  ("nl.martijndwars" % "web-push" % "3.1.1") ::
  ("com.github.xuwei-k" %% "play-json-extra" % "0.5.1") ::
  ("org.webjars" % "clipboard.js" % "2.0.0") ::
  ("org.webjars" % "jquery" % "3.3.1") ::
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

javaOptions ++= ManagementFactory.getRuntimeMXBean.getInputArguments.asScala.toList.filter(
  a => Seq("-Xmx", "-Xms", "-XX").exists(a.startsWith)
)

licenses := Seq("MIT License" -> url("https://opensource.org/licenses/MIT"))
