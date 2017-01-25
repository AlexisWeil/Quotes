name := """quotes"""
organization := "com.supinfo"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  filters,
  ws,
  jdbc,
  "mysql" % "mysql-connector-java" % "5.1.40",
  "com.typesafe.play" %% "anorm" % "2.5.0"
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.supinfo.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.supinfo.binders._"
