package actors

import akka.actor.Actor
import models.Posts.{GetPosts, GetSinglePost, GetUserPosts, Post}
import services.DbQueries.{getAllPosts, getSInglePost, getUserPosts, insertPost}

import scala.language.postfixOps

object PostActor {
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
      case GetSinglePost(postId) =>
        val post = getSInglePost(postId).unsafeRunSync()
        sender() ! post
      case GetUserPosts(userId) =>
        val posts = getUserPosts(userId).unsafeRunSync()
        sender() ! posts
    }
  }
}