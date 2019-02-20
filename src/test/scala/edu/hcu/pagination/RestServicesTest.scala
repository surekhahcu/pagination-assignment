package edu.hcu.pagination

import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.testkit.ScalatestRouteTest
import akka.http.scaladsl.unmarshalling.Unmarshaller._
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatest.{Matchers, WordSpec}

import scala.concurrent.Future

class RestServicesTest extends WordSpec with Matchers with ScalatestRouteTest with MockitoSugar {

  val mockedPdfRepository = mock[PdfRepository]

  val restService = new RestServices {
    override val pdfObject: PdfRepository = mockedPdfRepository
  }


  "Rest service " should {

    "save record to pdf repository " in {
      val requestJson = """{"content":"This is test case data","time":"11 AM"}"""
      when(mockedPdfRepository.create(PdfContent("This is test case data", "11 AM"))) thenReturn (Future(1))
      Post("/pdf/save", HttpEntity(ContentTypes.`application/json`, requestJson)) ~> restService.route ~> check {
        responseAs[String] shouldEqual "Pdf created"
      }
    }

    "update record of pdf " in {
      val requestJson = """{"content":"This content is for update test case","time":"12 AM","id": 1}"""
      when(mockedPdfRepository.update(PdfContent("This content is for update test case", "12 AM", Some(1)))) thenReturn (Future(1))
      Post("/pdf/update", HttpEntity(ContentTypes.`application/json`, requestJson)) ~> restService.route ~> check {
        responseAs[String] shouldEqual "Pdf updated"
      }
    }

    "delete the record of given id" in {
      when(mockedPdfRepository.delete(1)) thenReturn (Future(1))
      Post("/pdf/delete?id=1") ~> restService.route ~> check {
        responseAs[String] shouldEqual "Pdf Deleted"
      }
    }


    "Get the content of given id's pdf" in {
      when(mockedPdfRepository.getById(1)) thenReturn Future(Some(PdfContent("This pdf content will always be available in rest Services test cases", "9 AM", Some(1))))
      Get("/pdf/getById?id=1") ~> restService.route ~> check {
        responseAs[String] shouldEqual """{"content":"This pdf content will always be available in rest Services test cases","time":"9 AM","id":1}"""
      }
    }

    "Not found requuired pdf" in {
      when(mockedPdfRepository.getById(4)) thenReturn (Future(None))
      Get("/pdf/getById?id=4") ~> restService.route ~> check {
        responseAs[String] shouldEqual "Pdf does not exist[id: 4]"
      }
    }

    "Get All The Pdfs" in {
      when(mockedPdfRepository.getAll()) thenReturn Future(List(PdfContent("This pdf content will always be available in rest Services test cases", "11 AM", Some(1)),
        PdfContent("This pdf content is for the testCase of method getAll", "9 AM", Some(2))))
      Get("/pdf/getAll") ~> restService.route ~> check {
        responseAs[String] shouldEqual
          """[{"content":"This pdf content will always be available in rest Services test cases","time":"11 AM","id":1},{"content":"This pdf content is for the testCase of method getAll","time":"9 AM","id":2}]"""
      }
    }


    "Get Pdf Pages" in {
      when(mockedPdfRepository.getPage(1, 2)) thenReturn Future(List(PdfContent("This pdf content will always be available in rest Services test cases", "11 AM", Some(1)),
        PdfContent("This pdf content is for the testCase of method getAll", "9 AM", Some(2))))
      Get("/pdf/getPage?pageNo=1&size=2") ~> restService.route ~> check {
        responseAs[String] shouldEqual
          """[{"content":"This pdf content will always be available in rest Services test cases","time":"11 AM","id":1},{"content":"This pdf content is for the testCase of method getAll","time":"9 AM","id":2}]"""
      }
    }

  }

}
