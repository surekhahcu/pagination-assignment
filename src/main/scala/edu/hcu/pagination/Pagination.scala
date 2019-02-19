package edu.hcu.pagination

import slick.jdbc.MySQLProfile.api._

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}


object Pagination extends App {

  val pdfRepository= new PdfRepository
  Await.result(DBConnection.db.run(pdfRepository.pdfsTableQuery.schema.create), 10 seconds)


  val pdfContentList = List(
    PdfContent(PdfToText.getContent("/home/surekha/Documents/pdfs/pdf1.pdf"), "9 AM"),
    PdfContent(PdfToText.getContent("/home/surekha/Documents/pdfs/pdf2.pdf"), "10 AM"),
    PdfContent(PdfToText.getContent("/home/surekha/Documents/pdfs/pdf3.pdf"), "11 AM"),
    PdfContent(PdfToText.getContent("/home/surekha/Documents/pdfs/pdf4.pdf"), "12 AM"),
    PdfContent(PdfToText.getContent("/home/surekha/Documents/pdfs/pdf5.pdf"), "1 PM"),
    PdfContent(PdfToText.getContent("/home/surekha/Documents/pdfs/pdf6.pdf"), "2 PM"),
    PdfContent(PdfToText.getContent("/home/surekha/Documents/pdfs/pdf7.pdf"), "3 PM"),
    PdfContent(PdfToText.getContent("/home/surekha/Documents/pdfs/pdf8.pdf"), "4  PM"),
    PdfContent(PdfToText.getContent("/home/surekha/Documents/pdfs/pdf9.pdf"), "11 PM"),
    PdfContent(PdfToText.getContent("/home/surekha/Documents/pdfs/pdf10.pdf"), "10 PM"),
    PdfContent(PdfToText.getContent("/home/surekha/Documents/pdfs/pdf11.pdf"), "9 PM"),
    PdfContent(PdfToText.getContent("/home/surekha/Documents/pdfs/pdf12.pdf"), "8 PM"),
    PdfContent(PdfToText.getContent("/home/surekha/Documents/pdfs/pdf13.pdf"), "7 PM"),
    PdfContent(PdfToText.getContent("/home/surekha/Documents/pdfs/pdf14.pdf"), "7 AM"),
    PdfContent(PdfToText.getContent("/home/surekha/Documents/pdfs/pdf15.pdf"), "8 AM")
  )


  pdfContentList.foreach { pdf =>
    val futureResult: Future[Int] = pdfRepository.create(pdf)
    Await.result(futureResult, 10 seconds)
  }

  println(Await.result(pdfRepository.getPage(1, 2), 10 seconds))
}

