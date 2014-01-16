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
  
  // poc stuff
 /* def get_sep = Action {
    val json = database withSession {
      val sep = Citations.get_sorted_sep
      Json.toJson(sep)
    }
    Ok(json).as(JSON)
  }
  */
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
      val citation_with_auth = Citation_With_Authors.get_citation_with_authors(cit)
      val cit_json = Citation_With_Authors.get_citation_json(citation_with_auth)
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
      val coll_citations = Collections.get_collection_citations(coll)
      val citations = MutableList[JsValue]()
      coll_citations.foreach(citation => citations += Citation_With_Authors.get_citation_json(Citation_With_Authors.get_citation_with_authors(Option(citation))))          
      Json.toJson(citations)
    }
    Ok(json).as(JSON)
  }
  
}