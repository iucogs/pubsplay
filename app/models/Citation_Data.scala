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

               
object Citation_Data extends Function19[Int, String, String, String, String, String, String, String, String, String, String, String, String, 
                                   String, String, String, String, String, String, Citation_Data] {
  implicit val citation_format = Json.format[Citation_Data]
  implicit val citation_reads = Json.reads[Citation_Data]
  implicit val citation_writes = Json.writes[Citation_Data]
}             

object Citations_Data extends Table[Citation_Data]("citations") {
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
  
  def * = (citation_id ~ pubtype ~ abs ~ doi ~ url ~ booktitle ~ chapter ~ edition ~ editor ~ translator ~ journal ~ month ~ number ~ pages ~ publisher ~ location ~ title ~ volume ~ year).<>[Citation_Data](Citation_Data,Citation_Data unapply _)

  def add(citation: Citation_Data)(implicit session: Session) = 
    this.insert(citation)
   
  def get_citation(id: Int)(implicit session:Session):Option[Citation_Data] = 
    Query(Citations_Data).where(_.citation_id === id).firstOption

}

