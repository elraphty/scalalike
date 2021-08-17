package models

object Posts {
  case class Posts(userId: Int, wins: String, failures: String, commitments: String)
  case class GetPosts(userId: Int, wins: String, failures: String, commitments: String, created_at: String)
}