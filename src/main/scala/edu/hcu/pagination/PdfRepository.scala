package edu.hcu.pagination

import slick.jdbc.MySQLProfile.api._

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}


object PdfRepository {


  val pdfsTableQuery = TableQuery[Pdfs]

  def create(pdf: PdfContent): Future[Int] =
    DBConnection.db.run {
      (pdfsTableAutoInc += pdf)

    }

  def pdfsTableAutoInc = pdfsTableQuery returning pdfsTableQuery.map(_.id)

  def delete(id: Int): Future[Int] = DBConnection.db.run {
    pdfsTableQuery.filter(_.id === id).delete
  }

  def update(pdf: PdfContent): Future[Int] = DBConnection.db.run {
    pdfsTableQuery.filter(_.id === pdf.id.get).update(pdf)
  }

  def getById(id: Int): Future[Option[PdfContent]] = DBConnection.db.run {
    pdfsTableQuery.filter(_.id === id).result.headOption
  }

  def getAll(): Future[List[PdfContent]] = DBConnection.db.run {
    pdfsTableQuery.to[List].result
  }


  /* def contentOfGivenLines(pdf: PdfContent):String = {
     val line = pdf.content.split("\n").take(10)
    line.mkString("\n")
   }
 */

  def getPage(pageNo: Int, size: Int): List[PdfContent] = {
    val query = pdfsTableQuery.sortBy(_.id).drop(size * (pageNo - 1)).take(size)
    val finalResult: Seq[PdfContent] = Await.result(DBConnection.db.run(query.result), 10 seconds)
    finalResult.toList
  }

  class Pdfs(tag: Tag) extends Table[PdfContent](tag, "pdf_content") {

    def * = (content, time, id.?) <> (PdfContent.tupled, PdfContent.unapply)

    def content = column[String]("content")

    def time = column[String]("time")

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

  }


}

case class PdfContent(content: String, time: String, id: Option[Int] = None)
