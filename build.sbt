name := "pagination-assignment"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies ++= Seq(
  "mysql" % "mysql-connector-java" % "5.1.36",
  "com.typesafe.slick" %% "slick-hikaricp" % "3.3.0",
  "ch.qos.logback" % "logback-classic" % "1.1.3",
  "com.typesafe.slick" %% "slick" % "3.3.0",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test",
  "org.apache.tika" % "tika-parsers" % "1.10",
  "org.scalatest" %% "scalatest" % "3.0.5" % Test

)