package models

import play.api.libs.functional.syntax._
import scala.slick.driver.MySQLDriver.simple._

case class Author_Of(author_id: Int, citation_id: Int, position_num: Int)

object Author_Of  extends Function3[Int, Int, Int, Author_Of] {
    
  def author_of_query = TableQuery[Authors_Of]  
}

class Authors_Of(tag: Tag) extends Table[Author_Of](tag, "author_of") {
  
  def author_id = column[Int]("author_id")
  def citation_id = column[Int]("citation_id")
  def position_num = column[Int]("position_num")
  
  def * = (author_id, citation_id, position_num).<>(Author_Of.tupled, Author_Of unapply)
}

