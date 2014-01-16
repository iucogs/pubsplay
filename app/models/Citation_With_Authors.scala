package models

import play.api.libs.json._
import play.api.libs.json.Json
import play.api.libs.functional.syntax._
import scala.slick.driver.MySQLDriver.simple._
import scala.collection.mutable.MutableList

case class Citation_With_Authors(citation: Citation, authors: MutableList[String]) 

object Citation_With_Authors extends Function2[Citation, MutableList[String], Citation_With_Authors] {
  val citation_with_author_format = Json.format[Citation_With_Authors]
  implicit val citation_with_author_reads = Json.reads[Citation_With_Authors]
  implicit val citation__with_author_writes = Json.writes[Citation_With_Authors]

  def get_citation_with_authors(cit: Option[Citation])(implicit session:Session):Citation_With_Authors = {
    val citation = cit.get
    val authors = Citations.get_citation_authors(citation)
    return new Citation_With_Authors(citation, authors)
  }
  
  def get_citation_json(citation: Citation_With_Authors):JsValue = {
    return Json.toJson(citation)
  }

}
     