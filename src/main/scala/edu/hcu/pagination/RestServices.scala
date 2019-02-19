package edu.hcu.pagination


import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import edu.hcu.pagination.JsonUtility._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object RestServices extends App{

  implicit val system: ActorSystem = ActorSystem("RestServices")

  implicit val materializer: ActorMaterializer = ActorMaterializer()

  val pdfRepository = new PdfRepository
  val route: Route =
    path("pdf" / "getPage") {
      get {
        parameter('pageNo.as[Int], 'size.as[Int]) { (pageNo, size) =>
          complete {
            val pdfs: Future[List[PdfContent]] = pdfRepository.getPage(pageNo, size)
            pdfs.map { pdfData => write(pdfData)
            }
          }
        }
      }
    }

  val port = 8000

  Http().bindAndHandle(route, "localhost", port)

  println(s"Rest service is running on $port")


}

