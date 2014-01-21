package models

import play.api.libs.json._
import play.api.libs.json.Json
import play.api.libs.functional.syntax._
import scala.slick.driver.MySQLDriver.simple._
import scala.collection.mutable.MutableList

case class Citation_Meta(citation_id: Int, raw: String, owner: String, keywords: String)

object Citation_Meta extends Function4[Int, String, String, String, Citation_Meta] {
  implicit val citation_meta_format = Json.format[Citation_Meta]
  implicit val citation_meta_reads = Json.reads[Citation_Meta]
  implicit val citation_meta_writes = Json.writes[Citation_Meta]
}

object Citations_Meta extends Table[Citation_Meta]("citations") {
  def citation_id = column[Int]("citation_id", O.PrimaryKey, O.AutoInc)
  def raw = column[String]("raw")
  def owner = column[String]("owner") 
  def keywords = column[String]("keywords")
  
  def * = (citation_id ~ raw ~ owner ~ keywords).<>[Citation_Meta](Citation_Meta,Citation_Meta unapply _)
  
  def get_citation_meta(id: Int)(implicit session:Session):Citation_Meta = {
    return Query(Citations_Meta).where(_.citation_id === id).first
  }

}