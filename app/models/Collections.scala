package models

import play.api.libs.json._
import play.api.libs.functional.syntax._
import scala.slick.driver.MySQLDriver.simple._
import models._

case class Collection(collection_id: Int, collection_name: String, user_id: Int, submitter: String, owner: String)

class Collections(tag: Tag) extends Table[Collection](tag, "collections") {
  def collection_id = column[Int]("collection_id", O.PrimaryKey, O.AutoInc)
  def collection_name = column[String]("collection_name")
  def user_id = column[Int]("user_id")
  def submitter = column[String]("submitter")
  def owner = column[String]("owner")  
  def * = (collection_id, collection_name, user_id, submitter, owner).<>(Collection.tupled,Collection unapply)
  
  
}

object Collections /*extends Function5[Int, String, Int, String, String, Collection]*/{
  val collections_query = TableQuery[Collections]
  
  def get_collection(id: Int)(implicit session:Session):Option[Collection] = {
    val ret = for {collection <- this.collections_query if collection.collection_id === id} yield collection
    return ret.firstOption
  }

  def get_collection_citations(collection: Option[Collection])(implicit session:Session):List[Citation] = {
    val citation_ids = for { col <- member_of_collection.members_of_collection if col.collection_id === collection.get.collection_id } yield col.citation_id
    return citation_ids.list.map(citation_id => Citation.citation_factory(citation_id))
  } 
}