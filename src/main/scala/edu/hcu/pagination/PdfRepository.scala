package edu.hcu.pagination

import slick.jdbc.MySQLProfile
import slick.jdbc.MySQLProfile.api._
import slick.lifted.ProvenShape

import scala.concurrent.Future


class PdfRepository {


  val pdfsTableQuery = TableQuery[Pdfs]

  def create(pdf: PdfContent): Future[Int] =
    DBConnection.db.run {
      (pdfsTableAutoInc += pdf)

    }

  def pdfsTableAutoInc: MySQLProfile.ReturningInsertActionComposer[PdfContent, Int] = pdfsTableQuery returning pdfsTableQuery.map(_.id)

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

  def getPage(pageNo: Int, size: Int): Future[List[PdfContent]] = {
    val query = pdfsTableQuery.to[List].sortBy(_.id).drop(size * (pageNo - 1)).take(size)
    val futureResult: Future[List[PdfContent]] = DBConnection.db.run(query.result)
    futureResult
  }


  class Pdfs(tag: Tag) extends Table[PdfContent](tag, "pdf_content") {

    def * : ProvenShape[PdfContent] = (content, time, id.?) <> (PdfContent.tupled, PdfContent.unapply)

    def content: Rep[String] = column[String]("content")

    def time: Rep[String] = column[String]("time")

    def id: Rep[Int] = column[Int]("id", O.PrimaryKey, O.AutoInc)

  }


}

case class PdfContent(content: String, time: String, id: Option[Int] = None)
