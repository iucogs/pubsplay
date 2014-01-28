package models

import play.api.libs.functional.syntax._
import scala.slick.driver.MySQLDriver.simple._

case class member_of_collection (collection_id: Int, citation_id: Int)

class member_of_collection_table(tag: Tag) extends Table[member_of_collection](tag, "member_of_collection") {
  def collection_id = column[Int]("collection_id")
  def citation_id = column[Int]("citation_id")
  def * = (collection_id, citation_id).<>(member_of_collection.tupled,member_of_collection unapply)
  
  
}

object member_of_collection extends Function2[Int, Int, member_of_collection] {
  val members_of_collection = TableQuery[member_of_collection_table]
}