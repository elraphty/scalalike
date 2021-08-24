package actors

import actors.UserActor.users
import akka.actor.{Actor, ActorSystem, Props}
import models.Posts.{GetPosts, Posts}
import akka.pattern.ask
import akka.util.Timeout
import models.MyTypes.PostsList

import scala.concurrent.duration._
import scala.language.postfixOps

object PostActor {
  var posts: PostsList = List()

  class MyPostActor extends Actor {
    override def receive: Receive = {
      case post: Posts =>
        posts = posts :+ post
        sender() ! "Success"
      case GetPosts => sender() ! posts
    }
  }
}