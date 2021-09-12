package actors

import actors.UserActor.users
import akka.actor.{Actor, ActorSystem, Props}
import models.Posts.{GetPosts, Post}
import akka.pattern.ask
import akka.util.Timeout
import models.MyTypes.PostsList
import services.DbQueries.{getAllPosts, insertPost}

import scala.concurrent.duration._
import scala.language.postfixOps

object PostActor {
  var posts: PostsList = List()

  class MyPostActor extends Actor {
    override def receive: Receive = {
      case post: Post =>
        try {
          insertPost(post.userId, post.wins, post.failures, post.commitments).unsafeRunSync()
          sender() ! "Success"
        } catch {
          case e: Exception => sender() ! "Error"
        }
      case GetPosts =>
        val newPosts = getAllPosts.unsafeRunSync()
        sender() ! newPosts;
    }
  }
}