import scala.collection.JavaConverters._
import java.lang.management.ManagementFactory

herokuAppName in Compile := "webpush-scala"

scalaVersion := "2.13.0"

name := "webpush-scala"

libraryDependencies ++= (
  ("nl.martijndwars" % "web-push" % "4.0.0") ::
  ("com.github.xuwei-k" %% "play-json-extra" % "0.6.2") ::
  ("org.webjars" % "clipboard.js" % "2.0.4") ::
  ("org.webjars" % "jquery" % "3.4.1") ::
  ("org.webjars" %% "webjars-play" % "2.7.3") ::
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
  Nil
)

enablePlugins(PlayScala)

javaOptions ++= ManagementFactory.getRuntimeMXBean.getInputArguments.asScala.toList.filter(
  a => Seq("-Xmx", "-Xms", "-XX").exists(a.startsWith)
)

licenses := Seq("MIT License" -> url("https://opensource.org/licenses/MIT"))
