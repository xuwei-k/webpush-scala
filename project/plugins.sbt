scalacOptions ++= (
  "-deprecation" ::
  "-unchecked" ::
  "-language:existentials" ::
  "-language:higherKinds" ::
  "-language:implicitConversions" ::
  "-Yno-adapted-args" ::
  Nil
)

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.9.0")

addSbtPlugin("com.heroku" % "sbt-heroku" % "2.1.4")
