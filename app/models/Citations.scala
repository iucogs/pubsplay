package models

import play.api.libs.json._
import play.api.libs.functional.syntax._
import scala.slick.driver.MySQLDriver.simple._
import scala.collection.mutable.MutableList

case class Citation(citation_id: Int, pubtype: String, abs: String, keywords: String,      
                    doi: String, url: String, booktitle: String, chapter: String, edition: String, editor:       
                    String, translator: String, journal: String, month: String, number: String, pages: 
                    String, publisher: String, location: String, title: String, volume: String, year: 
                    String, raw: String, owner: String)

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
  def raw = column[String]("raw")
  def owner = column[String]("owner") 

  def * = (citation_id ~ pubtype ~ abs ~ keywords ~ doi ~ url ~ booktitle ~ chapter ~ edition ~ editor ~ translator ~ journal ~ month ~ number ~ pages ~ publisher ~ location ~ title ~ volume ~ year ~ raw ~ owner).<>[Citation](Citation,Citation unapply _)

  def add(citation: Citation)(implicit session: Session) = 
    this.insert(citation)
  
    
  def get_citation(id: Int)(implicit session:Session):Option[Citation] = 
    Query(Citations).where(_.citation_id === id).firstOption
    
  def get_citation_json(citation: Option[Citation])(implicit session:Session):JsObject = {
    if (citation.isEmpty) {
      return Json.obj("error" -> "citation not found!")
    } else {
      val cit = citation.get
      return Json.obj("citation_id" -> cit.citation_id,
    		  		  "authors" -> get_citation_authors(cit),
                      "pubtype" -> cit.pubtype,
                      "abstract" -> cit.abs,
                      "keywords" -> cit.keywords,
                      "doi" -> cit.doi,
                      "url" -> cit.url,
                      "booktitle" -> cit.booktitle,
                      "chapter" -> cit.chapter,
                      "edition" -> cit.edition,
                      "translator" -> cit.translator,
                      "journal" -> cit.journal,
                      "month" -> cit.month,
                      "number" -> cit.number,
                      "pages" -> cit.pages,
                      "publisher" -> cit.publisher,
                      "location" -> cit.location,
                      "title" -> cit.title,
                      "volume" -> cit.volume,
                      "year" -> cit.year,
                      "raw" -> cit.raw,
                      "owner" -> cit.owner)
    }
  }
  
  
  def get_citation_authors(citation: Citation)(implicit session:Session):JsValue = { 
    val cit_id = citation.citation_id
    val authors = for { author <- Authors
                        entry <- author_of_table if entry.citation_id === cit_id && entry.author_id === author.author_id  } yield author.lastname ~ author.firstname
    val authors_list = MutableList[String]()
    authors.foreach {case (l, f) => authors_list += (l + ", " + f)}
    return Json.toJson(authors_list)
  }
   
}
