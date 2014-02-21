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
  
  def citation_factory(citation_data: Citation_Data, citation_meta: Citation_Meta, authors: List[String])(implicit session: Session): Citation = {
    return new Citation(citation_data, citation_meta, authors)
  }
  
  
  
  def citation_all(owner: String)(implicit session: Session): List[Citation] = { 
    /* SELECT citations.citation_id, 
       GROUP_CONCAT(authors.lastname, ', ', authors.firstname
                    ORDER BY author_of.position_num, authors.lastname, authors.firstname
                    SEPARATOR ", ") AS author_string,
       citations.year
FROM citations
INNER JOIN author_of ON citations.citation_id = author_of.citation_id
INNER JOIN authors ON author_of.author_id = authors.author_id
GROUP BY author_of.citation_id
ORDER BY author_string, year;
      */
     
    //val group_concat = SimpleFunction.binary[String, String, String]("GROUP_CONCAT")
    // Peter and Mary is this thing ugly. Saints preserve us! 
    val group_concat = SimpleExpression.ternary[String, String, Int, String] { (lastname, firstname, pos, qb) =>
      qb.sqlBuilder += "GROUP_CONCAT("
      qb.expr(lastname)
      qb.sqlBuilder += ", ', ', "
      qb.expr(firstname)
      qb.sqlBuilder += " ORDER BY "
      qb.expr(pos)
      qb.sqlBuilder += " ,"
      qb.expr(lastname)
      qb.sqlBuilder += " ,"
      qb.expr(firstname)
      qb.sqlBuilder += " SEPARATOR \", \"), "
    }
    
    println(group_concat.toString)
    
    val all_query = for { citation_meta <- Citation_Meta.citation_meta_query  
                          citation_data <- Citation_Data.citation_data_query  
                          entry <- Author_Of.author_of_query 
                          author <- Authors.authors_query
                          if citation_meta.owner === owner && citation_data.citation_id === citation_meta.citation_id &&  
                          entry.citation_id === citation_meta.citation_id && author.author_id === entry.author_id } yield (citation_meta, group_concat(author.lastname, author.firstname, entry.position_num), citation_data)
    
    
    all_query.groupBy(_._1.citation_id)
    all_query.sortBy(_._2)
    
    println("die grosse query\n")                      
    println(all_query.selectStatement)
    val citations = for {citation <- all_query.list} yield citation_factory(citation._3, citation._1, List[String](citation._2))
    
    return citations       
  }                        
  
  def get_citation_json(citation: Citation)(implicit session:Session):JsObject = {
    return citation.toJson()
  }
 /* 
  def get_citations_by_owner(owner: String)(implicit session:Session):List[Citation] = {
    val citation_parts = for { citation_data <- Citation_Data.citation_data_query
      		     		       citation_meta <- Citation_Meta.citation_meta_query if citation_meta.owner === owner && 
                                                                                     citation_meta.citation_id === citation_data.citation_id } yield (citation_data, citation_meta)
    
    val citations = for {citation_part <- citation_parts.list} yield citation_factory(citation_part._1, citation_part._2)
    
    //return citations
    return citations.sortWith(_.authors.toString < _.authors.toString)
  }
*/
}
     