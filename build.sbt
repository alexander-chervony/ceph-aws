val awsJavaSdk = "com.amazonaws" % "aws-java-sdk" % "1.10.50"
val scalatest = "org.scalatest" %% "scalatest" % "2.2.4" % "test"

lazy val root = (project in file(".")).
    settings(
      name := "CEPH through AWS client",
      version := "1.0",
      scalaVersion := "2.11.7",
      libraryDependencies ++= Seq(awsJavaSdk, scalatest)
    )