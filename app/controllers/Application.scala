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
  def get_sep = Action {
    val json = database withSession {
      val sep = Citation.get_SEP()
      val bar = sep.map(x => x.toJson())
      Json.toJson(bar)
    }
    Ok(json).as(JSON)
  }

  // Citation actions //
  
  def get_citation_json(citation_id:Int) = Action {
    val json = database withSession {
      val citation = Citation.citation_factory(citation_id)
      Json.toJson(citation.toJson())
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
      Json.toJson(coll_citations.map(citation => citation.toJson()))
    }
    Ok(json).as(JSON)
  }

}