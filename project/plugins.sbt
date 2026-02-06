scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked",
  "-Yno-adapted-args",
)

addSbtPlugin("org.playframework" % "sbt-plugin" % "3.0.10")

addSbtPlugin("com.heroku" % "sbt-heroku" % "2.1.4")
