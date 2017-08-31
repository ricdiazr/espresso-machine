enablePlugins(SbtOsgi)
scalaVersion := "2.11.8"
organization := "org.o1"
name := "espresso-machine"
version := "0.0.1-SNAPSHOT"

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",
  "-feature",
  "-target:jvm-1.8",
  "-unchecked",
  "-Ywarn-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-numeric-widen",
  "-Ywarn-unused",
  "-Ywarn-value-discard",
  "-Xfuture",
  "-Xlint"
)

scalacOptions in (Compile, doc) ++= baseDirectory.map {
  (bd: File) => Seq[String](
     "-sourcepath", bd.getAbsolutePath
  )
}.value

javacOptions ++= Seq(
  "-Xlint:deprecation",
  "-Xlint:unchecked",
  "-source", "1.8",
  "-target", "1.8",
  "-g:vars"
)

libraryDependencies ++= Seq(
  "org.scala-lang.modules" %% "scala-xml" % "1.0.6",
  "joda-time" % "joda-time" % "2.9.9",
  "org.o1" %%  "espresso-cup" % "0.0.1"
)

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"

osgiSettings
OsgiKeys.bundleSymbolicName := "org.o1.espresso-machine"
OsgiKeys.exportPackage := Seq("org.o1.espresso.machine.*")
OsgiKeys.importPackage := Seq("org.o1.logging")

logLevel := Level.Warn

// Only show warnings and errors on the screen for compilations.
// This applies to both test:compile and compile and is Info by default
logLevel in compile := Level.Info

// Level.INFO is needed to see detailed output when running tests
logLevel in test := Level.Debug
logBuffered in test := false

// define the statements initially evaluated when entering 'console', 'console-quick', but not 'console-project'
initialCommands in console := """|""".stripMargin

cancelable := true
