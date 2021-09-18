package actors

import akka.actor.Actor
import models.User._
import services.DbQueries.{getAllUsers, getSIngleUser, insertUser, loginUser}
import org.mindrot.jbcrypt._

object UserActor {
  class MyUserActor extends Actor {
    override def receive: Receive = {
      case user: User =>
        try {
          insertUser(user.fullName, user.email, user.age, user.password).unsafeRunSync()
          sender() ! "Success"
        } catch {
          case e: Exception => sender() ! "Error"
        }
      case GetUsers =>
        val newUsers = getAllUsers.unsafeRunSync()
        sender() ! newUsers
      case GetSingleUser(userId) =>
        val user = getSIngleUser(userId).unsafeRunSync()
        sender() ! user;
      case login: Login =>
        val user = loginUser(login.email).unsafeRunSync()
        println(s"User === ${user}")
        if(user.nonEmpty) {
          user.foreach(data => {
            if (BCrypt.checkpw(login.password, data.password)) {
              sender() ! user
            } else {
              sender() ! Vector(Login(data.email, "wrong password"))
            }
          })
        } else sender() ! Vector(Login("not found", "not found"))
    }
  }
}
