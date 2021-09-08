package models

import models.Posts.{Posts, Post}
import models.User.{Users, User}
import spray.json.{DefaultJsonProtocol, RootJsonFormat}

trait JsonTraits extends DefaultJsonProtocol {
  implicit val postJson: RootJsonFormat[Post] = jsonFormat4(Post)
  implicit val getPostsJson: RootJsonFormat[Posts] = jsonFormat6(Posts)
  implicit val userJson: RootJsonFormat[User] = jsonFormat5(User)
  implicit val getUserJson: RootJsonFormat[Users] = jsonFormat6(Users)
}
