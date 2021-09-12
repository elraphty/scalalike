package services

import DbService.transactor
import cats.effect.{ExitCode, IO}
import doobie.implicits._
import models.Posts.Posts

object DbQueries {
  def insertPost(userId: Int, wins: String, failures: String, commitments: String): IO[ExitCode] = {
    transactor.use {
      xa =>
        for {
          a <- sql"insert into posts (userid, wins, failures, commitments) values ($userId, $wins, $failures, $commitments)".update.run.transact(xa)
        } yield ExitCode.Success
    }
  }

  val getAllPosts: IO[List[Posts]] = transactor.use { xa =>
    for {
      a <- sql"select * from posts".query[Posts].to[List].transact(xa)
    } yield (a)
  }
}
