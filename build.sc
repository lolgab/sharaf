import mill._
import mill.scalalib._, scalafmt._, publish._

import $ivy.`io.chris-kipp::mill-ci-release::0.1.9`
import io.kipp.mill.ci.release.CiReleaseModule

object sharaf extends SharafPublishModule {

  def artifactName = "sharaf"

  def ivyDeps = Agg(
    ivy"io.undertow:undertow-core:2.3.5.Final",
    ivy"ba.sake::tupson:0.6.0",
    ivy"ba.sake::hepek-components:0.10.0+0-3aaeebf1+20230522-1255-SNAPSHOT"
  )

  def moduleDeps = Seq(querson, formson)

  object test extends Tests with SharafTestModule
}

object querson extends SharafPublishModule {

  def artifactName = "querson"

  def ivyDeps = Agg(
    ivy"ba.sake::tupson:0.6.0", // TODO we need just the validation stuff..
    ivy"com.lihaoyi::fastparse:3.0.1",
    ivy"com.lihaoyi::requests:0.8.0" // TODO move to a separate module
  )

  object test extends Tests with SharafTestModule
}

object formson extends SharafPublishModule {

  def artifactName = "formson"

  object test extends Tests with SharafTestModule
}

trait SharafPublishModule extends SharafCommonModule with CiReleaseModule {

  def pomSettings = PomSettings(
    organization = "ba.sake",
    url = "https://github.com/sake92/sharaf",
    licenses = Seq(License.Common.Apache2),
    versionControl = VersionControl.github("sake92", "sharaf"),
    description = "Sharaf http library",
    developers = Seq(
      Developer("sake92", "Sakib Hadžiavdić", "https://sake.ba")
    )
  )
}

trait SharafCommonModule extends ScalaModule with ScalafmtModule {
  def scalaVersion = "3.3.0"
  def scalacOptions = super.scalacOptions() ++ Seq(
    "-deprecation",
    "-Yretain-trees",
    "-Wunused:all"
  )
}

trait SharafTestModule extends TestModule.Munit {
  def ivyDeps = Agg(
    ivy"org.scalameta::munit::0.7.29"
  )
}

////////////////////
object examples extends mill.Module {
  object html extends SharafCommonModule {
    def moduleDeps = Seq(sharaf)
    object test extends Tests with SharafTestModule
  }
  object json extends SharafCommonModule {
    def moduleDeps = Seq(sharaf)
    object test extends Tests with SharafTestModule
  }
  object form extends SharafCommonModule {
    def moduleDeps = Seq(sharaf)
    object test extends Tests with SharafTestModule
  }
  object todo extends SharafCommonModule {
    def moduleDeps = Seq(sharaf)
    object test extends Tests with SharafTestModule
  }
}