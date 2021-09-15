package models

object Posts {
  case class Post(userId: Int, wins: String, failures: String, commitments: String)
  case class Posts(id: Int, userId: Int, wins: String, failures: String, commitments: String, created_at: String)
  case object GetPosts
  case class GetSinglePost(postId: Int)
  case class GetUserPosts(userId: Int)
}