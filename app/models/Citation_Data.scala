package models

import play.api.libs.json._
import play.api.libs.json.Json
import play.api.libs.functional.syntax._
import scala.slick.driver.MySQLDriver.simple._
import scala.collection.mutable.MutableList

case class Citation_Data(citation_id: Int, pubtype: String, abs: String,      
                    doi: String, url: String, booktitle: String, chapter: String, edition: String, editor:       
                    String, translator: String, journal: String, month: String, number: String, pages: 
                    String, publisher: String, location: String, title: String, volume: String, year: 
                    String)

class Citations_Data(tag: Tag) extends Table[Citation_Data](tag, "citations") {
  def citation_id = column[Int]("citation_id", O.PrimaryKey, O.AutoInc)
  def pubtype = column[String]("pubtype")
  def abs = column[String]("abstract")
  def doi = column[String]("doi")
  def url = column[String]("url")
  def booktitle = column[String]("booktitle")
  def chapter = column[String]("chapter")
  def edition = column[String]("edition")
  def editor = column[String]("editor")
  def translator = column[String]("translator")
  def journal = column[String]("journal")
  def month = column[String]("month")
  def number = column[String]("number")
  def pages = column[String]("pages")
  def publisher = column[String]("publisher")
  def location = column[String]("location")
  def title = column[String]("title")
  def volume = column[String]("volume")
  def year = column[String]("year")
  
  def * = (citation_id, pubtype, abs, doi, url, booktitle, chapter, edition, editor, translator, journal, month, number, pages, publisher, location, title, volume, year).<>(Citation_Data.tupled, Citation_Data.unapply)
}

object Citation_Data extends Function19[Int, String, String, String, String, String, String, String, String, String, String, String, String, 
                                   String, String, String, String, String, String, Citation_Data] {
  implicit val citation_format = Json.format[Citation_Data]
  implicit val citation_reads = Json.reads[Citation_Data]
  implicit val citation_writes = Json.writes[Citation_Data]
 
  val citation_data_query = TableQuery[Citations_Data]
  
  def add(citation: Citation_Data)(implicit session: Session) = 
    citation_data_query.insert(citation)
   
  def get_citation(id: Int)(implicit session:Session):Citation_Data = {
    def citation_query(id: Column[Int]) = for {citation <- citation_data_query if citation.citation_id === id} yield citation
    val compiled_citation_query = Compiled(citation_query _)
    val citation = compiled_citation_query(id).run
    
    return citation.head
  }
}    
