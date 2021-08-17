package models

import spray.json.{DefaultJsonProtocol, RootJsonFormat}

object User {
  case class User(fullName: String, age: Int, email: String, password: String, image: String)
  case class GetUser(id: Int, fullName: String, age: Int, email: String, password: String, image: String)

  trait UserJson extends DefaultJsonProtocol {
    implicit val userJson: RootJsonFormat[User] = jsonFormat5(User);
  }

  trait GetUserJson extends DefaultJsonProtocol {
    implicit val getUserJson: RootJsonFormat[GetUser] = jsonFormat6(GetUser);
  }
}