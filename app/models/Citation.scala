package models

import play.api.libs.json._
import play.api.libs.json.Json
import play.api.libs.functional.syntax._
import scala.slick.driver.MySQLDriver.simple._
import models._
import scala.collection.mutable.MutableList


case class Citation(citation_data: Citation_Data, citation_meta: Citation_Meta, authors: List[String]) {
  val citation_id: Int = citation_data.citation_id
  val pubtype: String = citation_data.pubtype
  val abs: String = citation_data.pubtype
  val doi: String = citation_data.doi
  val url: String = citation_data.url
  val booktitle: String = citation_data.booktitle
  val chapter: String = citation_data.chapter
  val edition: String = citation_data.edition
  val editor: String = citation_data.editor
  val translator: String = citation_data.translator
  val journal: String = citation_data.journal
  val month: String = citation_data.month
  val number: String = citation_data.number
  val pages: String = citation_data.pages
  val publisher: String = citation_data.publisher
  val location: String = citation_data.location
  val title: String = citation_data.title
  val volume: String = citation_data.volume
  val year: String = citation_data.year
  
  val owner: String = citation_meta.owner
  val raw: String = citation_meta.raw
  val keywords: String = citation_meta.keywords
  
  def toJson()(implicit session:Session): JsObject = {
    return Json.obj("citation_id" -> this.citation_id,
                    "title" -> this.title,
    				"pubtype" -> this.pubtype,
    				"authors" -> Json.toJson(this.authors),
                    "abs" -> this.abs,
                    "doi" -> this.doi,
                    "url" -> this.url,
                    "booktitle" -> this.booktitle,
                    "chapter" -> this.chapter,
                    "edition" -> this.edition,
                    "editor" -> this.editor,
                    "translator" -> this.translator,
                    "journal" -> this.journal,
                    "month" -> this.month,
                    "number" -> this.number,
                    "pages" -> this.pages,
                    "publisher" -> this.publisher,
                    "location" -> this.location,
                    "volume" -> this.volume,
                    "year" -> this.year,
                    "owner" -> this.owner,
                    "raw" -> this.raw,
                    "keywords" -> this.keywords
                    )
    
  }
    
}

object Citation extends Function3[Citation_Data, Citation_Meta, List[String], Citation] {
  //implicit val citation_format = Json.format[Citation]
  //implicit val citation_reads = Json.reads[Citation]
  //implicit val citation_writes = Json.writes[Citation]
   
  def citation_factory(citation_id: Int)(implicit session:Session):Citation = {
    val citation_data = Citations_Data.get_citation(citation_id).get
    val citation_meta = Citations_Meta.get_citation_meta(citation_id)
    val authors = Authors.get_citation_authors(citation_data)
    return new Citation(citation_data, citation_meta, authors)
  }

  def get_citation_json(citation: Citation)(implicit session:Session):JsObject = {
    return citation.toJson()
  }
  
  def get_SEP()(implicit session:Session):List[Citation] = {
    val sep_ids = for {citation <- Citations_Meta if citation.owner === "sep" } yield citation.citation_id
    val citations = sep_ids.list.map(citation_id => this.citation_factory(citation_id))
    return citations
    //return citations.sortWith(_.authors.toString < _.authors.toString)
  }

}
     