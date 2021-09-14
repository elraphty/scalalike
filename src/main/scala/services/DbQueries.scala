package services

import DbService.transactor
import cats.effect.{ExitCode, IO}
import doobie.implicits._
import models.Posts.Posts
import com.github.t3hnar.bcrypt._
import models.User.Users

object DbQueries {
  // Posts db functions
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
      a <- sql"select * from posts order by id desc".query[Posts].to[List].transact(xa)
    } yield (a)
  }

  // User Db Functions
  def insertUser(fullName: String, email: String, age: Int, password: String): IO[ExitCode] = {
    val hashPass = password.bcryptSafeBounded.get
    transactor.use {
      xa =>
        for {
          a <- sql"insert into users (fullname, email, age, password) values ($fullName, $email, $age, $hashPass)".update.run.transact(xa)
        } yield ExitCode.Success
    }
  }

  val getAllUsers: IO[List[Users]] = transactor.use { xa =>
    for {
      a <- sql"select * from users order by id desc".query[Users].to[List].transact(xa)
    } yield (a)
  }
}
