import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, Multipart, StatusCodes}
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import akka.pattern.ask

import scala.concurrent.Future

import scala.language.postfixOps
import scala.concurrent.duration._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport

object MyRoutes {
  val routes: Route =
    path("") {
      get {
        complete(
          HttpEntity(
            ContentTypes.`text/html(UTF-8)`,
            """
              |<html>
              | <body>
              |   Hello! welcome to scala like home.
              | </body>
              |</html>
              |""".stripMargin
          )
        )
      }
    }
}
