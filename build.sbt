
name := "TwitterAnalysis"

version := "0.3.0"

scalaVersion := "2.10.4"

libraryDependencies ++= Seq("org.apache.spark" %% "spark-core" % "1.0.1",
"org.apache.spark" %% "spark-streaming" % "1.0.1",
"org.apache.hadoop" % "hadoop-client" % "2.0.0-cdh4.2.0",
"org.apache.spark" %% "spark-streaming-twitter" % "1.0.1")

// resolvers += "Akka Repository" at "http://repo.akka.io/releases/"

resolvers ++= Seq("Akka Repository" at "http://repo.akka.io/releases/", "Cloudera Repository" at "https://repository.cloudera.com/artifactory/cloudera-repos/")

