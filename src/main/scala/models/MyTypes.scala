package models

import Posts.Posts
import User.Users

object MyTypes {
  type PostsList = List[Posts]
  type UsersList = List[Users]
}
