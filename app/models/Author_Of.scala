package models

import play.api.libs.functional.syntax._
import scala.slick.driver.MySQLDriver.simple._

case class author_of(author_id: Int, citation_id: Int)

object author_of_table extends Table[author_of]("author_of") {
  def author_id = column[Int]("author_id")
  def citation_id = column[Int]("citation_id")
  def * = (author_id ~ citation_id).<>[author_of](author_of,author_of unapply _)
}