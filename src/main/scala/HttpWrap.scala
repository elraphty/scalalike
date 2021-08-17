import akka.actor.{Actor, ActorSystem}
import akka.stream.{ActorMaterializer, Materializer}
import akka.http.scaladsl.Http
import MyRoutes.routes
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.Directives.complete
import akka.http.scaladsl.server.{ExceptionHandler, MethodRejection, MissingQueryParamRejection, Rejection, RejectionHandler}

object HttpWrap extends App {
  implicit val system: ActorSystem = ActorSystem("Scalalike");
  implicit val materializer: ActorMaterializer = ActorMaterializer();

  // Handle rejections
  implicit val customRejectionHandler: RejectionHandler = RejectionHandler.newBuilder()
    .handle {
      case m: MissingQueryParamRejection =>
        println(s"I got a query param rejection: $m")
        complete("Rejected query param!")
    }
    .handle {
      case m: Rejection =>
        println(s"I got a query param rejection: $m")
        complete("Rejected query param!")
    }
    .handle {
      case m: MethodRejection =>
        println(s"I got a method rejection: $m")
        complete("Rejected method!")
    }
    .handleNotFound {
      complete(StatusCodes.NotFound, "Not Found!")
    }
    .result()

  implicit val customExceptionHandler: ExceptionHandler = ExceptionHandler {
    case e: IllegalArgumentException =>
      println(s"Illegal Argument exception ${e.getMessage}")
      complete(StatusCodes.BadRequest, e.getMessage)
    case e: RuntimeException =>
      println(s"Runtime exception ${e.getMessage}")
      complete(StatusCodes.NotFound, e.getMessage)
  }

  Http().bindAndHandle(routes, "localhost", 8083);
}
