package controllers

import play.api._
import play.api.mvc._
import scala.slick.driver.MySQLDriver.simple._
import scala.slick.session.Session
import play.api.libs.json._
import models._
import play.api.db._
import play.api.Play.current
import Database.threadLocalSession
import scala.collection.mutable.MutableList


object Application extends Controller {
  
  lazy val database = Database.forDataSource(DB.getDataSource())
  
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }
  // Citation actions //
  
  def getCitations = Action {
    val json = database withSession {
      val citations = for (citation <- Citations) yield citation.title.toString
      Json.toJson(citations.list)
    }
    Ok(json).as(JSON)
  }
  
  def getCitation(citation_id: Int) = Action {
    val json = database withSession{
      val cit = Citations.get_citation(citation_id)
      Json.toJson(cit.toString)
    }
    Ok(json).as(JSON)
  }
  
  def getCitationJson(citation_id:Int) = Action {
    val json = database withSession {
      val cit = Citations.get_citation(citation_id)
      val cit_json = Citations.get_citation_json(cit)
      Json.toJson(cit_json)
    }
    Ok(json).as(JSON)
  }
  
  // Collection actions
  def get_collection(collection_id: Int) = Action {
    val json = database withSession{
      val coll = Collections.get_collection(collection_id)
      Json.toJson(coll.toString)
    }
    Ok(json).as(JSON)
  }
  
  def get_collection_citations(collection_id: Int) = Action {
    val json = database withSession{
      val coll = Collections.get_collection(collection_id)
      val coll_citations = Collections.get_collection_citations(coll)
      Json.toJson(coll_citations)
    }
    Ok(json).as(JSON)
  }
  
  def get_collection_citations_json(collection_id: Int) = Action {
    val json = database withSession{
      val coll = Collections.get_collection(collection_id)
      val coll_citations = Collections.get_collection_citations(coll).value
      val coll_list = coll_citations.get("citations").get.as[List[Int]]
      val citations = MutableList[JsObject]()
      coll_list.foreach(citation => citations += Citations.get_citation_json(Citations.get_citation(citation)))
      println(coll_list.toString)
      Json.toJson(citations)
    }
    Ok(json).as(JSON)
  }
  
}