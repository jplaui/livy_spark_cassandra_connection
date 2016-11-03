name := "livy_test"

version := "1.0"

scalaVersion := "2.11.8"

resolvers ++= Seq(
  "ScalaJ-Http" at "https://mvnrepository.com/artifact/org.scalaj/scalaj-http_2.11",
  "JsonPlay" at "https://mvnrepository.com/artifact/com.typesafe.play/play-json_2.11"
)

libraryDependencies ++= Seq(
  "org.scalaj" %% "scalaj-http" % "2.3.0",
  "com.typesafe.play" %% "play-json" % "2.5.9"
)
