package models

import play.api.libs.functional.syntax._
import scala.slick.driver.MySQLDriver.simple._

case class Author_Of(author_id: Int, citation_id: Int, position_num: Int)

object Authors_Of extends Table[Author_Of]("author_of") {
  def author_id = column[Int]("author_id")
  def citation_id = column[Int]("citation_id")
  def position_num = column[Int]("position_num")
  def * = (author_id ~ citation_id ~ position_num).<>[Author_Of](Author_Of,Author_Of unapply _)
}