package models

import play.api.libs.json._
import play.api.libs.json.Json
import play.api.libs.functional.syntax._
import scala.slick.driver.MySQLDriver.simple._
import scala.collection.mutable.MutableList

case class Citation(citation_id: Int, pubtype: String, abs: String, keywords: String,      
                    doi: String, url: String, booktitle: String, chapter: String, edition: String, editor:       
                    String, translator: String, journal: String, month: String, number: String, pages: 
                    String, publisher: String, location: String, title: String, volume: String, year: 
                    String)

               
object Citation extends Function20[Int, String, String, String, String, String, String, String, String, String, String, String, String, 
                                   String, String, String, String, String, String, String, Citation] {
  implicit val citation_format = Json.format[Citation]
  implicit val citation_reads = Json.reads[Citation]
  implicit val citation_writes = Json.writes[Citation]
}             

object Citations extends Table[Citation]("citations") {
  def citation_id = column[Int]("citation_id", O.PrimaryKey, O.AutoInc)
  def pubtype = column[String]("pubtype")
  def abs = column[String]("abstract")
  def keywords = column[String]("keywords")
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
  
  def * = (citation_id ~ pubtype ~ abs ~ keywords ~ doi ~ url ~ booktitle ~ chapter ~ edition ~ editor ~ translator ~ journal ~ month ~ number ~ pages ~ publisher ~ location ~ title ~ volume ~ year).<>[Citation](Citation,Citation unapply _)

  def add(citation: Citation)(implicit session: Session) = 
    this.insert(citation)
   
  def get_citation(id: Int)(implicit session:Session):Option[Citation] = 
    Query(Citations).where(_.citation_id === id).firstOption
        
  def get_citation_authors(citation: Citation)(implicit session:Session):MutableList[String] = { 
    val cit_id = citation.citation_id
    val authors_list = MutableList[String]()
    val authors = for { author <- Authors
                        entry  <- Authors_Of if entry.citation_id === cit_id && entry.author_id === author.author_id  } yield author.lastname ~ author.firstname ~ entry.position_num
                        
    authors.sortBy(a => a._3 < a._3)
    authors.foreach {case (l, f, p) => authors_list += (l + ", " + f)}
    
    return authors_list
  }

  def get_sorted_sep()(implicit session:Session):JsValue = {
    val sep = for {citation <- Citations
                   citation_meta <- Citations_Meta if citation_meta.citation_id === citation.citation_id && citation_meta.owner === "sep" } yield citation
    
    return Json.toJson("ok")
  }


}

