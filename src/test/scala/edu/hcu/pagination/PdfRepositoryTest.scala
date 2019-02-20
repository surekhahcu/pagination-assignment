package edu.hcu.pagination

import org.scalatest.{BeforeAndAfter, FunSuite}
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.util.control.NonFatal

class PdfRepositoryTest extends FunSuite with BeforeAndAfter {

  val pdf = new PdfRepository

  before {
    try {
      Await.result(DBConnection.db.run(pdf.pdfsTableQuery.schema.create), 10 seconds)
      Await.result( pdf.create(PdfContent("This pdf content will always be available","11 Am", Some(1))), 10 seconds)
    } catch {
      case NonFatal(th) =>
        th.printStackTrace()
    }
  }

  after {
    try {
      Await.result(DBConnection.db.run(pdf.pdfsTableQuery.schema.drop), 10 seconds)
    } catch {
      case NonFatal(th) =>
        th.printStackTrace()
    }

  }

  test("Inserting Pdf file") {
    val resultFuture: Future[Int] = pdf.create(PdfContent("This Is Dummy Pdf Data","11 AM"))
    val result: Int = Await.result(resultFuture, 10 seconds)
    assert(result === 2)

  }

  test("Updating Pdf file") {
    val resultFuture: Future[Int] = pdf.update(PdfContent("This content is for update the pdf content", "9 AM",Some(1)))
    val result: Int = Await.result(resultFuture, 10 seconds)
    assert(result === 1)
  }

  test("Deleting Pdf") {
    val resultFuture: Future[Int] = pdf.delete(1)
    val result: Int = Await.result(resultFuture, 10 seconds)
    assert(result === 1)

  }

  test("Fetch Pdf Data By Pdf Id") {
    val resultFuture: Future[Option[PdfContent]] = pdf.getById(1)
    val result: Option[PdfContent] = Await.result(resultFuture, 10 seconds)
    assert(result === Some(PdfContent("This pdf content will always be available","11 Am", Some(1))))
  }

  test("Fetch All Pdfs") {
    val resultFuture: Future[List[PdfContent]] = pdf.getAll()
    val result = Await.result(resultFuture, 10 seconds)
    assert(result === List(PdfContent("This pdf content will always be available","11 Am", Some(1))))
  }


  test("Get Pages") {
    val resultFuture: Future[List[PdfContent]] = pdf.getPage(1,2)
    val result = Await.result(resultFuture, 10 seconds)
    assert(result === List(PdfContent("This pdf content will always be available","11 Am", Some(1))))
  }


}
