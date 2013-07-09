package models

import play.api.db.slick.Config.driver._
import collection.mutable.Map

//case class Citation(citation_id: Int, citation_metadata: Map[String, String], abs: String, doi: String, url: String, booktitle: String, chapter: Int, edition: String,
//               translator: String, how_published: String, journal: String, month: Int, number: Int, pages: String, publisher: String, location: String, title: String,
//               volume: String, year: String, date_retreived: String)

case class Citation(citation_id: Int, doi: String, url: String)
               
object Citations extends Table[Citation]("citations") {
  
  def citation_id = column[Int]("citation_id", O.PrimaryKey, O.AutoInc)
  def citation_metadata = Map(("submitter", column[String]("submitter")),
                              ("owner", column[String]("owner")),
                              ("entry_time", column[Double]("entry_time")),
                              ("raw", column[String]("raw")),
                              ("keywords", column[String]("keywords")),
                              ("annotation", column[String]("annotation")),
                              ("pubtype", column[String]("pubtype")),
                              ("filename", column[String]("filename")),
                              ("verified", column[Int]("verified")))
  def abs = column[String]("abstract")
  def doi = column[String]("doi")
  def url = column[String]("url")
  def booktitle = column[String]("booktitle")
  def chapter = column[Int]("chapter")
  def edition = column[String]("edition")
  def translator = column[String]("translator")
  def how_published = column[String]("howpublished")
  def journal = column[String]("journal")
  def month = column[Int]("month")
  def number = column[Int]("number")
  def pages = column[String]("pages")
  def publisher = column[String]("publisher")
  def location = column[String]("location")
  def title = column[String]("title")
  def volume = column[String]("volume")
  def year = column[String]("year")
  def date_retreived = column[String]("date_retreived")
  def * = citation_id ~ doi ~ url <> (Citation, Citation.unapply _)
  
}

