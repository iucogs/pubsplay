package models

import play.api.db.slick.Config.driver._

class citation(citation_id: Int,    pubtype: String, abst: String,   keywords: String,     doi: String,
               url: String,         address: String, annote: String,     booktitle: String,    chapter: String,
               crossref: String,    edition: String, translator: String, howpublished: String, 
               institution: String, journal: String, bibtex_key: String, month: String,        note: String,
               number: String,      organization: String,                pages: String,        publisher: String,
               location: String,    school: String,                      series: String,       title: String,
               volume: String,      year: String,                        raw: String,          verified: Int,
               format: String,      filename: String,                    submitter: String,    owner: String,
               entryTime: Double,   last_modified: Double,               date_retreived: String)
               
object Citations extends Table[citation]("citations") {
  
  def citation_id = column[Int]("citation_id", O.PrimaryKey, O.AutoInc)
  def pubtype = column[String]("pubtype")
  def abst = column[String]("abst")
  def keywords = column[String]("keywords")
  def doi = column[String]("doi")
  def url = column[String]("url")
  def address = column[String]("address")
  def annote = column[String]("annote")
  def booktitle = column[String]("booktitle")
  def chapter = column[String]("chapter")
  def crossref = column[String]("crossref")
  def edition = column[String]("edition")
  def howpublished = column[String]("howpublished")
  def institution = column[String]("institution")
  def journal = column[String]("journal")
  def month = column[String]("month")
  def note = column[String]("note")
  def location = column[String]("location")
  def school = column[String]("school")
  def series = column[String]("series")
  def title = column[String]("title")
  def volume = column[String]("volume")
  def year = column[String]("year")
  def raw = column[String]("raw")
  def verified = column[Int]("verified")
  def format = column[String]("format")
  def filename = column[String]("filename")
  def submitter = column[String]("submitter")
  def owner = column[String]("owner")
  def entryTime = column[Double]("entryTime")
  def last_modified = column[Double]("last_modified")
  def date_retreived = column[String]("date_retreived")
  
  
  
  def * = (citation_id ~ pubtype ~ abst ~ keywords ~ doi ~ url ~ 
           address ~ annote ~ booktitle ~ chapter ~ crossref ~ edition ~ 
           howpublished ~ institution ~ journal ~ month ~ note ~ location ~
           school ~ series ~ title ~ volume ~ year ~ raw ~ verified ~ format ~
           filename ~ submitter ~ owner ~ entryTime ~ last_modified ~ date_retreived) <> (citation, citation.unapply _)  
}
