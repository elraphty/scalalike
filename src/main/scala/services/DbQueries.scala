package services

import DbService.transactor
import cats.effect.{ExitCode, IO}
import doobie.implicits._
import models.Posts.Posts
import models.User.{Login, LoginUser, Users}
import org.mindrot.jbcrypt._

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

  def getSInglePost(id: Int): IO[List[Posts]] = transactor.use { xa =>
    for {
      a <- sql"select * from posts where id = $id".query[Posts].to[List].transact(xa)
    } yield (a)
  }

  def getUserPosts(userId: Int): IO[List[Posts]] = transactor.use { xa =>
    for {
      a <- sql"select * from posts where userid = $userId".query[Posts].to[List].transact(xa)
    } yield (a)
  }

  // User Db Functions
  def insertUser(fullName: String, email: String, age: Int, password: String): IO[ExitCode] = {
    val hashPass = BCrypt.hashpw(password, BCrypt.gensalt())
    transactor.use {
      xa =>
        for {
          a <- sql"insert into users (fullname, email, age, password) values ($fullName, $email, $age, $hashPass)".update.run.transact(xa)
        } yield ExitCode.Success
    }
  }

  val getAllUsers: IO[List[Users]] = transactor.use { xa =>
    for {
      a <- sql"select id, age, email, fullname, image from users order by id desc".query[Users].to[List].transact(xa)
    } yield (a)
  }

  def getSIngleUser(userId: Int): IO[List[Users]] = transactor.use { xa =>
    for {
      a <- sql"select id, age, email, fullname, image from users where id = $userId".query[Users].to[List].transact(xa)
    } yield (a)
  }

  def loginUser(email: String): IO[Vector[Login]] = {
    transactor.use {
      xa =>
        for {
          a <- sql"select email, password from users where email = $email".query[Login].to[Vector].transact(xa)
        } yield a
    }
  }
}
