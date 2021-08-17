package actors

import akka.actor.Actor
import models.Posts.{GetPosts, Posts}

object PostActor {
  class MyPostActor extends Actor {
    override def receive: Receive = {
      case post: Posts => sender() ! post
      case posts: GetPosts => sender() ! posts
    }
  }
}
