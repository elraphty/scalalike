package actors

import akka.actor.Actor
import models.User._
import services.DbQueries.{getAllUsers, getSIngleUser, insertUser}

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
     }
   }
}
