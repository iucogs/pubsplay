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
  
  val citation_meta_query = TableQuery[Citations_Meta]
  
  def get_citation_meta(id: Int)(implicit session:Session):Citation_Meta = {
    def citation_query(id: Column[Int]) = for {citation <- citation_meta_query if citation.citation_id === id} yield citation
    val compiled_citation_query = Compiled(citation_query _)
    val citation_meta = compiled_citation_query(id).run
    return citation_meta.head
  }
}

class Citations_Meta(tag: Tag) extends Table[Citation_Meta](tag, "citations") {
  def citation_id = column[Int]("citation_id", O.PrimaryKey, O.AutoInc)
  def raw = column[String]("raw")
  def owner = column[String]("owner") 
  def keywords = column[String]("keywords")
  
  def * = (citation_id, raw, owner, keywords).<>(Citation_Meta.tupled,Citation_Meta unapply)
}