package models

object Posts {
  case class Post(userId: Int, wins: String, failures: String, commitments: String)
  case class Posts(userId: Int, wins: String, failures: String, commitments: String, created_at: String)
  case object GetPosts
}