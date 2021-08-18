package actors

import akka.actor.{Actor, ActorSystem, Props}
import models.Posts.{GetPosts, Post}
import akka.pattern.ask
import akka.util.Timeout
import models.MyTypes.PostsList

import scala.concurrent.duration._
import scala.language.postfixOps

object PostActor {
  var posts: PostsList = List()

  class MyPostActor extends Actor {
    override def receive: Receive = {
      case post: Post => sender() ! post
      case GetPosts => sender() ! posts
    }
  }
}