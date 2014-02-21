package controllers

import play.api._
import play.api.mvc._
import scala.slick.driver.MySQLDriver.simple._
//import scala.slick.session.PlayDatabase
import play.api.libs.json._
import models._
import play.api.db._
import play.api.Play.current
import scala.slick.jdbc.JdbcBackend.Database.dynamicSession
import play.api.libs.iteratee.Enumerator

object Application extends Controller {
  
  lazy val database = Database.forDataSource(DB.getDataSource())
  
  def index = Action {
    Ok(views.html.index("Your new application is ready."))
  }
  
  // poc stuff
  /*
  def get_citations_by_owner(owner:String) = Action {
    val json = database withDynSession {
      val sep = Citation.get_citations_by_owner(owner).map(x => x.toJson)
      sep.toString.getBytes() 
    }
    
    SimpleResult  (
        header = ResponseHeader(200),
        body = Enumerator(json)
        )
  }
  
 */
  def get_citations_by_owner(owner: String) = Action {
    val json = database withDynSession {
      //val sep = Citation.get_citations_by_owner(owner)
      val sep = Citation.citation_all(owner)
      val bar = sep.map(x => x.toJson())
      Json.toJson(bar)
    }
    Ok(json).as(JSON)
  }

  // Citation actions //
  
  def get_citation_json(citation_id:Int) = Action {
    val json = database withDynSession {
      val citation = Citation.citation_factory(citation_id)
      Json.toJson(citation.toJson())
    }
    Ok(json).as(JSON)
  }
  
  def get_citation(citation_id: Int) = Action {
    def ret = database withDynSession {
      val citation = Citation.citation_factory(citation_id)
      citation.toString.getBytes
    } 
    SimpleResult ( 
        header = ResponseHeader(200),
        body = Enumerator(ret))
  }
     
  // Collection actions
  def get_collection(collection_id: Int) = Action {
    val json = database withDynSession{
      val coll = Collections.get_collection(collection_id)
      Json.toJson(coll.toString)
    }
    Ok(json).as(JSON)
  }
  
  def get_collection_citations(collection_id: Int) = Action {
    val json = database withDynSession{
      val coll = Collections.get_collection(collection_id)
      val coll_citations = Collections.get_collection_citations(coll)
      Json.toJson(coll_citations.map(citation => citation.toJson()))
    }
    Ok(json).as(JSON)
  }

}