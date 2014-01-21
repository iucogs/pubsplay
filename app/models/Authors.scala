package models

import play.api.libs.functional.syntax._
import scala.slick.driver.MySQLDriver.simple._
import play.api.libs.json._
import scala.collection.mutable.MutableList


case class Author(author_id: Int, lastname: String, firstname: String, verified: Int)

object Author extends Function4[Int, String, String, Int, Author] {
  implicit val author_format = Json.format[Author]
  implicit val author_reads = Json.reads[Author]
  implicit val author_writes = Json.writes[Author] 
}

object Authors extends Table[Author]("authors") {
  def author_id = column[Int]("author_id", O.PrimaryKey, O.AutoInc)
  def lastname = column[String]("lastname")
  def firstname = column[String]("firstname")
  def verified = column[Int]("Int")
  def * = (author_id ~ lastname ~ firstname ~ verified).<>[Author](Author,Author unapply _)

  def get_citation_authors(citation: Citation_Data)(implicit session:Session):List[String] = { 
    val authors = for { author <- Authors
                        entry  <- Authors_Of if entry.citation_id === citation.citation_id && entry.author_id === author.author_id  } yield author.lastname ~ author.firstname ~ entry.position_num                    
    //authors.sortBy(a => a._3 < a._3)
    return authors.list.map{case (lastname, firstname, position) => (lastname + ", " + firstname)}
  }

}