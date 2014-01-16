package models

import play.api.libs.json._
import play.api.libs.functional.syntax._
import scala.slick.driver.MySQLDriver.simple._

case class Collection(collection_id: Int, collection_name: String, user_id: Int, submitter: String, owner: String)

object Collections extends Table[Collection]("collections") {
  def collection_id = column[Int]("collection_id", O.PrimaryKey, O.AutoInc)
  def collection_name = column[String]("collection_name")
  def user_id = column[Int]("user_id")
  def submitter = column[String]("submitter")
  def owner = column[String]("owner")  
  def * = (collection_id ~ collection_name ~ user_id ~ submitter ~ owner).<>[Collection](Collection,Collection unapply _)
  
  def get_collection(id: Int)(implicit session:Session):Option[Collection] = 
    Query(Collections).where(_.collection_id === id).firstOption

  def get_collection_citations(collection: Option[Collection])(implicit session:Session):List[Citation] = {
      
	  val coll_id = collection.get.collection_id
      val citations_list = for { col <- member_of_collection_table
                                 citation <- Citations if col.collection_id === coll_id } yield citation
      return citations_list.list
    }
  
}