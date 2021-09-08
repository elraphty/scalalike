package services

import cats._
import cats.data._
import cats.effect._
import cats.implicits._
import doobie._
import doobie.implicits._
import doobie.util.ExecutionContexts
import doobie.hikari.HikariTransactor
import models.MyTypes.PostsList
import models.Posts.Posts

object DbService {
  implicit val cs = IO.contextShift(ExecutionContexts.synchronous)

  // A transactor that gets connections from java.sql.DriverManager and executes blocking operations
  // on an our synchronous EC. See the chapter on connection handling for more info.
  val transactor: Resource[IO, HikariTransactor[IO]] =
  for {
    ce <- ExecutionContexts.fixedThreadPool[IO](32) // our connect EC
    be <- Blocker[IO]    // our blocking EC
    xa <- HikariTransactor.newHikariTransactor[IO](
      "org.postgresql.Driver",     // driver classname
      "jdbc:postgresql://localhost/postlike",     // connect URL (driver-specific)
      "raph",                  // user
      "raph",                          //                                     // password
      ce,                                     // await connection here
      be                                      // execute JDBC operations here
    )
  } yield xa

  val postsearch = transactor.use { xa =>
    // Construct and run your server here!
    for {
      a <- sql"select * from posts".query[Posts].to[List].transact(xa)
    } yield (a)
  }

}
