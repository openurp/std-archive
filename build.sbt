import org.openurp.parent.Dependencies.*
import org.openurp.parent.Settings.*

ThisBuild / organization := "org.openurp.std.archive"
ThisBuild / version := "0.0.1-SNAPSHOT"

ThisBuild / scmInfo := Some(
  ScmInfo(
    url("https://github.com/openurp/std-archive"),
    "scm:git@github.com:openurp/std-archive.git"
  )
)

ThisBuild / developers := List(
  Developer(
    id = "chaostone",
    name = "Tihua Duan",
    email = "duantihua@gmail.com",
    url = url("http://github.com/duantihua")
  )
)

ThisBuild / description := "OpenURP Student Info"
ThisBuild / homepage := Some(url("http://openurp.github.io/std-archive/index.html"))

val apiVer = "0.37.0"
val starterVer = "0.3.22"
val baseVer = "0.4.19"
val openurp_std_api = "org.openurp.std" % "openurp-std-api" % apiVer
val openurp_edu_api = "org.openurp.edu" % "openurp-edu-api" % apiVer
val openurp_spa_api = "org.openurp.spa" % "openurp-spa-api" % apiVer
val openurp_stater_web = "org.openurp.starter" % "openurp-starter-web" % starterVer
val openurp_base_tag = "org.openurp.base" % "openurp-base-tag" % baseVer

lazy val root = (project in file("."))
  .enablePlugins(WarPlugin, TomcatPlugin)
  .settings(
    name := "openurp-std-archive-webapp",
    common,
    libraryDependencies ++= Seq(beangle_commons_core, beangle_data_orm, beangle_webmvc_core, beangle_webmvc_support),
    libraryDependencies ++= Seq(openurp_std_api, openurp_edu_api, beangle_serializer_text, openurp_spa_api),
    libraryDependencies ++= Seq(openurp_stater_web, openurp_base_tag, beangle_doc_docx,beangle_doc_pdf)
  )
