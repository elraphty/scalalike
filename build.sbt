name := "postlikesapp"

version := "0.1"

scalaVersion := "2.13.6"

val akkaVersion = "2.5.23"
val akkaHttpVersion = "10.1.8"
val scalaTestVersion = "3.2.8"
val doobieVersion = "0.13.4"
val postgresVersion = "42.2.22"

libraryDependencies ++=Seq(
  // akka streams
  "com.typesafe.akka" %% "akka-stream" % akkaVersion,

  // akka http
  "com.typesafe.akka" %% "akka-http" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpVersion,
  "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpVersion,

  // testing
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
  "org.scalactic" %% "scalactic" % scalaTestVersion,
  "org.scalatest" %% "scalatest" % scalaTestVersion,

  "com.typesafe" % "config" % "1.4.1",

  "org.tpolecat" %% "doobie-core"     % doobieVersion,
  "org.tpolecat" %% "doobie-hikari"   % doobieVersion,
  "org.tpolecat" %% "doobie-postgres" % doobieVersion,
  "org.tpolecat" %% "doobie-specs2"   % doobieVersion,

  "org.postgresql" % "postgresql" % postgresVersion
)