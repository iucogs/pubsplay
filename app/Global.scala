import play.api.db.DB
import play.api.GlobalSettings
import scala.slick.driver.MySQLDriver.simple._
import scala.slick.jdbc.JdbcBackend.Database.dynamicSession

import play.api.Logger


import play.api.Application
import play.api.Play.current
import models._

object Global extends GlobalSettings {
  override def onStart(app: Application) {
    lazy val database = Database.forDataSource(DB.getDataSource())    
  }
}