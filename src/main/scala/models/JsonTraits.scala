package models

import models.Posts.{Post, Posts}
import models.User.{Login, User, Users}
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait JsonTraits extends DefaultJsonProtocol {
  implicit val postJson: RootJsonFormat[Post] = jsonFormat4(Post)
  implicit val getPostsJson: RootJsonFormat[Posts] = jsonFormat6(Posts)
  implicit val userJson: RootJsonFormat[User] = jsonFormat5(User)
  implicit val getUserJson: RootJsonFormat[Users] = jsonFormat5(Users)
  implicit val loginJson: RootJsonFormat[Login] = jsonFormat2(Login)
}
