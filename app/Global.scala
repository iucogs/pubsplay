import models._
import play.api.db.DB
import play.api.GlobalSettings
import scala.slick.driver.MySQLDriver.simple._
import Database.threadLocalSession

import play.api.Logger


import play.api.Application
import play.api.Play.current

object Global extends GlobalSettings {
  override def onStart(app: Application) {
    implicit val application = app
    lazy val database = Database.forDataSource(DB.getDataSource())
    database.withSession {
      implicit session: Session =>
    }
  }
  
}