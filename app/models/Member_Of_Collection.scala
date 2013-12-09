package models

import play.api.libs.functional.syntax._
import scala.slick.driver.MySQLDriver.simple._

case class member_of_collection (collection_id: Int, citation_id: Int)

object member_of_collection_table extends Table[member_of_collection]("member_of_collection") {
  def collection_id = column[Int]("collection_id")
  def citation_id = column[Int]("citation_id")
  def * = (collection_id ~ citation_id).<>[member_of_collection](member_of_collection,member_of_collection unapply _)
}