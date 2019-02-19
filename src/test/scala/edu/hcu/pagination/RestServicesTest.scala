package edu.hcu.pagination

import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.http.scaladsl.unmarshalling.Unmarshaller._
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{Matchers, WordSpec}

import scala.concurrent.Future

class RestServicesTest extends WordSpec with Matchers with ScalatestRouteTest with MockitoSugar {

  val mockedPdfRepository = mock[PdfRepository]

  val restService = new RestServices {
    val pdfObject: PdfRepository = mockedPdfRepository
  }


  "Rest service " should {
    "Get the pages of pdfs" in {
      when(mockedPdfRepository.getPage(1,2)) thenReturn Future(List(PdfContent("This pdf content will always be available in rest Services test cases","11 AM", Some(1)),
        PdfContent("This pdf content will always be available in rest Services test cases for getPage Method","9 AM", Some(2))))
      Get("/pdf/getPage?pageNo=1&size=2") ~> restService.route ~> check {
        responseAs[String] shouldEqual
          """[{"data":"This pdf content will always be available in rest Services test cases","time":"11 AM","id":1},{"data":"This pdf content will always be available in rest Services test cases for getPage Method","time":"9 AM","id":2}]""".stripMargin
      }
    }
  }
  }
