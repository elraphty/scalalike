package models

object User {
  case class User(fullName: String, age: Int, email: String, password: String, image: Option[String])
  case class Users(id: Int, age: Int, email: String, fullName: String, image: Option[String])
  case object GetUsers
  case class GetSingleUser(userId: Int)
}