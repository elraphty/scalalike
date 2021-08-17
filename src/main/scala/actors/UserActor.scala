package actors

import akka.actor.{Actor, ActorSystem}
import models.User._

object UserActor extends App {
   class MyUserActor extends Actor {
     override def receive: Receive = {
       case user: User => sender() ! user
       case users: GetUser => sender() ! users
     }
   }
}
