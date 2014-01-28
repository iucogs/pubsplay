package models

import play.api.libs.functional.syntax._
import scala.slick.driver.MySQLDriver.simple._
import play.api.libs.json._
import scala.collection.mutable.MutableList
import models._


case class Author(author_id: Int, lastname: String, firstname: String, verified: Int)

object Authors /*extends Function4[Int, String, String, Int, Author]*/ {
  //implicit val author_format = Json.format[Author]
  //implicit val author_reads = Json.reads[Author]
  //implicit val author_writes = Json.writes[Author] 
  
  val authors_query = TableQuery[Authors]
  
  def get_citation_authors(citation: Citation_Data)(implicit session:Session):List[String] = { 
    def auths_query(id: Column[Int]) = for {author <- authors_query
      				                        entry <- Author_Of.author_of_query if entry.citation_id === citation.citation_id && entry.author_id === author.author_id } yield (author.lastname, author.firstname, entry.position_num)
        
    val compiled_auths_query = Compiled(auths_query _)
    val auths = compiled_auths_query(citation.citation_id).run.toList
    //authors.sortBy(a => a._3 < a._3)

    return auths.map{case (lastname, firstname, position) => (lastname + ", " + firstname)}
  }
}

class Authors(tag: Tag) extends Table[Author](tag, "authors") {
  def author_id = column[Int]("author_id", O.PrimaryKey, O.AutoInc)
  def lastname = column[String]("lastname")
  def firstname = column[String]("firstname")
  def verified = column[Int]("Int")
  def * = (author_id, lastname, firstname, verified).<>(Author.tupled,Author unapply)

}

