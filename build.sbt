name := """play-free-slick"""

version := "1.0"

scalaVersion := "2.11.11"

//scalacOptions += "-Ywarn-unused-import"

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)

libraryDependencies ++= Seq(
  filters,
  "org.postgresql"     % "postgresql"       % "9.4.1212",
  "com.typesafe.slick" %% "slick"           % "3.2.0",
  "com.typesafe.slick" %% "slick-codegen"   % "3.2.0",
  "io.frees"           %% "freestyle"       % "0.1.1",
  "io.frees"           %% "freestyle-slick" % "0.1.1"
)

lazy val root = (project in file(".")).enablePlugins(PlayScala)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "AdrianRaFo.controllers._"

// Adds additional packages into conf/routes
play.sbt.routes.RoutesKeys.routesImport += "dao.Tables.UserdataRow"

slick := slickCodeGenTask.value // register manual sbt command

lazy val slick = TaskKey[Seq[File]]("slick-gen")

lazy val slickCodeGenTask = Def.task {
  val outputDir = "app"
  toError(
    (runner in Compile).value.run(
      "slick.codegen.SourceCodeGenerator",
      (dependencyClasspath in Compile).value.files,
      Array(
        "slick.jdbc.PostgresProfile",
        "org.postgresql.Driver",
        "jdbc:postgresql://localhost/postgres?currentSchema=public",
        "app",
        "dao",
        "postgres",
        "Arfpostgre"
      ),
      streams.value.log
    ))
  Seq(file("app/dao/Tables.scala"))
}
