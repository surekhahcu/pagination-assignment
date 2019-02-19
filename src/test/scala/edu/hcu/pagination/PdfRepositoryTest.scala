package edu.hcu.pagination
import org.scalatest.{BeforeAndAfter, FunSuite}
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.util.control.NonFatal


class PdfRepositoryTest extends FunSuite with BeforeAndAfter {

  val pdfRepository= new PdfRepository
  before {
    try {
      Await.result(DBConnection.db.run(pdfRepository.pdfsTableQuery.schema.create), 10 seconds)
      Await.result( pdfRepository.create(PdfContent("This pdf content will always be available in all the test cases","11 AM", Some(1))), 10 seconds)
    } catch {
      case NonFatal(th) =>
        th.printStackTrace()
    }
  }

  after {
    try {
      Await.result(DBConnection.db.run(pdfRepository.pdfsTableQuery.schema.drop), 10 seconds)
    } catch {
      case NonFatal(th) =>
        th.printStackTrace()
    }

  }

  test("Save pdf data") {
    val resultFuture: Future[Int] = pdfRepository.create(PdfContent("This content is for creating pdf test case","11 AM"))
    val result: Int = Await.result(resultFuture, 10 seconds)
    assert(result === 2)
  }


  test("Update Pdf") {
    val resultFuture: Future[Int] = pdfRepository.update(PdfContent("This content is for updating the existing pdf data", "11 AM",Some(1)))
    val result: Int = Await.result(resultFuture, 10 seconds)
    assert(result === 1)
  }

  test("Delete Pdf") {
    val resultFuture: Future[Int] = pdfRepository.delete(1)
    val result: Int = Await.result(resultFuture, 10 seconds)
    assert(result === 1)

  }

  test(" Get Pdf By Id") {
    val resultFuture: Future[Option[PdfContent]] = pdfRepository.getById(1)
    val result: Option[PdfContent] = Await.result(resultFuture, 10 seconds)
    assert(result === Some(PdfContent("This pdf content will always be available in all the test cases","11 AM", Some(1))))
  }

  test("Get All The Pdfs") {
    val resultFuture: Future[List[PdfContent]] = pdfRepository.getAll()
    val result = Await.result(resultFuture, 10 seconds)
    assert(result === List(PdfContent("This pdf content will always be available in all the test cases", "11 AM",Some(1))))
  }

 test("Get Pages"){
   val resultFuture=pdfRepository.getPage(1,1)
   val result=Await.result(resultFuture,10 seconds)
   assert(result === List(PdfContent("This pdf content will always be available in all the test cases", "11 AM",Some(1))))
 }
}
