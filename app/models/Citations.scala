package models

//import play.api.db.slick.Config.driver._
import scala.slick.driver.MySQLDriver.simple._



case class Citation(citation_id: Int, pubtype: String, abs: String, keywords: String,      
                    doi: String, url: String, booktitle: String, chapter: String, edition: String, editor:       
                    String, translator: String, journal: String, month: String, number: String, pages: 
                    String, publisher: String, location: String, title: String, volume: String, year: 
                    String, raw: String, owner: String)

object Citations extends Table[Citation]("citations") {
  def citation_id = column[Int]("citation_id", O.PrimaryKey, O.AutoInc)
  def pubtype = column[String]("pubtype")
  def abs = column[String]("abstract")
  def keywords = column[String]("keywords")
  def doi = column[String]("doi")
  def url = column[String]("url")
  def booktitle = column[String]("booktitle")
  def chapter = column[String]("chapter")
  def edition = column[String]("edition")
  def editor = column[String]("editor")
  def translator = column[String]("translator")
  def journal = column[String]("journal")
  def month = column[String]("month")
  def number = column[String]("number")
  def pages = column[String]("pages")
  def publisher = column[String]("publisher")
  def location = column[String]("location")
  def title = column[String]("title")
  def volume = column[String]("volume")
  def year = column[String]("year")
  def raw = column[String]("raw")
  def owner = column[String]("owner") 

  def * = (citation_id ~ pubtype ~ abs ~ keywords ~ doi ~ url ~ booktitle ~ chapter ~   edition ~ editor ~ translator ~ journal ~ month ~ number ~ pages ~ publisher ~ location ~ title ~ volume ~ year ~ raw ~ owner).<>[Citation](Citation,Citation unapply _)

  def add(citation: Citation)(implicit session: Session) = {
    this.insert(citation)
  } 
  

}
