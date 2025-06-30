scalacOptions ++= (
  "-deprecation" ::
  "-unchecked" ::
  "-language:existentials" ::
  "-language:higherKinds" ::
  "-language:implicitConversions" ::
  "-Yno-adapted-args" ::
  Nil
)

addSbtPlugin("org.playframework" % "sbt-plugin" % "3.0.8")

addSbtPlugin("com.heroku" % "sbt-heroku" % "2.1.4")
