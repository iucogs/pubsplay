import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "pubs"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    jdbc,
    "mysql" % "mysql-connector-java" % "5.1.24",
    "com.typesafe.slick" % "slick_2.10" % "1.0.0",
    "org.slf4j" % "slf4j-nop" % "1.6.4",
    "com.typesafe.play" %% "play-slick" % "0.3.3"
  )


  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
  )

}
