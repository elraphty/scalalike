package actors

import akka.actor.{Actor, ActorSystem}
import models.MyTypes.UsersList
import models.User._

object UserActor {
  var users: UsersList = List(Users(1, "Raphael Osaze Eyerin", 26, "elraphtyofficial@gmail.com", "#21243", ""))

   class MyUserActor extends Actor {
     override def receive: Receive = {
       case user: Users =>
         users = users :+ user
         sender() ! "Success"
       case GetUsers => sender() ! users
     }
   }
}
