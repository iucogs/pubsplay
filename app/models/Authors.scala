package models

import play.api.libs.functional.syntax._
import scala.slick.driver.MySQLDriver.simple._
import play.api.libs.json._

case class Author(author_id: Int, lastname: String, firstname: String, verified: Int)

object Author extends Function4[Int, String, String, Int, Author] {
  implicit val author_format = Json.format[Author]
  implicit val author_reads = Json.reads[Author]
  implicit val author_writes = Json.writes[Author] 
}

object Authors extends Table[Author]("authors") {
  def author_id = column[Int]("author_id", O.PrimaryKey, O.AutoInc)
  def lastname = column[String]("lastname")
  def firstname = column[String]("firstname")
  def verified = column[Int]("Int")
  def * = (author_id ~ lastname ~ firstname ~ verified).<>[Author](Author,Author unapply _)
}