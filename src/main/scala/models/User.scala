package models

object User {
  case class User(fullName: String, age: Int, email: String, password: String, image: String)
  case class Users(id: Int, fullName: String, age: Int, email: String, password: String, image: String)
  case object GetUsers
}