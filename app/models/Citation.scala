package models

import play.api.libs.json._
import play.api.libs.json.Json
import play.api.libs.functional.syntax._
import scala.slick.driver.MySQLDriver.simple._
import models._
import scala.collection.parallel.immutable._

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
   
  def citation_factory(citation_id: Int)(implicit session:Session):Citation = {
    val citation_data = Citation_Data.get_citation(citation_id)
    val citation_meta = Citation_Meta.get_citation_meta(citation_id)
    val auths = Authors.get_citation_authors(citation_data)
    return new Citation(citation_data, citation_meta, auths)
  }
  
  def citation_factory(citation_data: Citation_Data, citation_meta: Citation_Meta)(implicit session: Session): Citation = {
    val auths = Authors.get_citation_authors(citation_data)
    return new Citation(citation_data, citation_meta, auths)
  }

  def get_citation_json(citation: Citation)(implicit session:Session):JsObject = {
    return citation.toJson()
  }
  
       				     										
  def get_citation(id: Int)(implicit session:Session): Citation = {
   
    // citation query, compilation, and returned objects
    def citation_query (id: Column[Int]) = for { cit_data <- Citation_Data.citation_data_query
                                                 cit_meta <- Citation_Meta.citation_meta_query if cit_data.citation_id === id &&
   	     											                                              cit_meta.citation_id === id } yield (cit_data, cit_meta)  				   
    val compiled_citation_query = Compiled(citation_query _)
    val citation = compiled_citation_query(id).run
    val citation_data = citation.head._1
    val citation_meta = citation.head._2
    
    // Author query, compilation and returned list.
    def auths_query (id: Column[Int]) = for {author <- Authors.authors_query
      				                         entry <- Author_Of.author_of_query if entry.citation_id === id && entry.author_id === author.author_id } yield (author.lastname, author.firstname, entry.position_num)
    val compiled_auths_query = Compiled(auths_query _)
    val auths = compiled_auths_query(id).run.toList
      				 
    return Citation(citation_data, citation_meta, auths.map{case (lastname, firstname, position) => (lastname + ", " + firstname)})  				 
  }
  
  def get_citations_by_owner(owner: String)(implicit session:Session):List[Citation] = {
    val citation_parts = for { citation_data <- Citation_Data.citation_data_query
      		     		       citation_meta <- Citation_Meta.citation_meta_query if citation_meta.owner === owner && 
                                                                                     citation_meta.citation_id === citation_data.citation_id } yield (citation_data, citation_meta)
    
    val citations = for {citation_part <- citation_parts.list} yield citation_factory(citation_part._1, citation_part._2)
    
    return citations
    //return citations.sortWith(_.authors.toString < _.authors.toString)
  }

}
     