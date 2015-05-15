package controllers

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import play.api._
import play.api.mvc._

import slick.driver.MySQLDriver.api._

object Application extends Controller {

  val db = Database.forConfig("db.default")

  db.run(models.Contents.contents.schema.create)

  def index = Action.async {
    for {
      result <- db.run(models.Contents.contents.map(content => (content.content, content.date)).result)
    } yield Ok(views.html.index(result))
  }

  def createContents = Action.async { implicit req =>
    models.Contents.form.bindFromRequest.fold(
      e => Future.successful(BadRequest(e.errorsAsJson)),
      { case (content, date) =>
        for {
          _ <- db.run(models.Contents.contents.map(content => (content.userId, content.content, content.date)) += (0, content, new java.sql.Date(date.getTime)))
        } yield Redirect("/")
      }
    )
  }

  def createUser = Action.async { implicit req =>
    models.Users.form.bindFromRequest.fold(
      e => Future.successful(BadRequest(e.errorsAsJson)),
      { case (_, name) =>
        for {
          _ <- db.run(models.Users.users.map(user => user.name) += name)
        } yield Redirect("/")
      }
    )
  }

  def helloworld = Action {
    Ok("helloworld")
  }

}
