import actors.PostActor.MyPostActor
import actors.UserActor.MyUserActor
import akka.actor.{ActorRef, ActorSystem, Props}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, Multipart, StatusCodes}
import akka.http.scaladsl.server.Route
import akka.stream.ActorMaterializer
import akka.pattern.ask

import scala.concurrent.Future
import scala.language.postfixOps
import scala.concurrent.duration._
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.util.Timeout
import models.JsonTraits
import models.MyTypes._
import models.User.{GetSingleUser, GetUsers, Login, LoginUser, User, Users}
import models.Posts.{GetPosts, GetSinglePost, GetUserPosts, Post}
import ch.megard.akka.http.cors.scaladsl.CorsDirectives._
import spray.json._
import org.mindrot.jbcrypt._

import scala.concurrent.ExecutionContext.Implicits.global

object MyRoutes extends SprayJsonSupport with JsonTraits {
  implicit val system: ActorSystem = ActorSystem("RouteSystem");
  implicit val materializer: ActorMaterializer = ActorMaterializer();

  //  import system.dispatcher;

  implicit val timeout: Timeout = Timeout(10 seconds);

  val userActorRef: ActorRef = system.actorOf(Props[MyUserActor], "userActor")
  val postActorRef: ActorRef = system.actorOf(Props[MyPostActor], "postActor")

  val routes: Route = cors() {
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
    } ~ path("user") {
      get {
        val users: Future[UsersList] = (userActorRef ? GetUsers).mapTo[UsersList]
        complete(users)
      } ~
        post {
          entity(as[User]) {
            user =>
              complete((userActorRef ? user).map(x => if (x == "Success") StatusCodes.OK else StatusCodes.BadRequest))
          }
        }
    } ~ path("auth" / "login") {
      post {
        entity(as[Login]) {
          loginData => {
            val user: Future[Vector[Login]] = (userActorRef ? loginData).mapTo[Vector[Login]]
            complete(user);
          }
      }
    } ~ path("user" / IntNumber) {
        userId => {
          get {
            val user: Future[UsersList] = (userActorRef ? GetSingleUser(userId)).mapTo[UsersList]
            val user1: User = User("Raphael", 25, "elraphty", "hhh", Some("hello"));
            val user2: UsersList = List(Users(1, 25, "Raphael", "elraphty", Some("hello")))
            println(user2.toJson.prettyPrint)
            println(user1.toJson.prettyPrint)
            complete(user)
          }
        }
      } ~ path("post") {
        get {
          val posts: Future[PostsList] = (postActorRef ? GetPosts).mapTo[PostsList]
          complete(posts)
        } ~
          post {
            entity(as[Post]) {
              post =>
                complete((postActorRef ? post).map(x => if (x == "Success") StatusCodes.OK else StatusCodes.BadRequest))
            }
          }
      } ~ path("post" / IntNumber) {
        id => {
          get {
            val post: Future[PostsList] = (postActorRef ? GetSinglePost(id)).mapTo[PostsList]
            complete(post)
          }
        }
      } ~ (path("post" / "user" / IntNumber) & get) {
        userId => {
          val posts: Future[PostsList] = (postActorRef ? GetUserPosts(userId)).mapTo[PostsList]
          complete(posts)
        }
      }
    }
  }
}
