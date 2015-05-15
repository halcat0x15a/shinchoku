package models

import play.api.data._
import play.api.data.Forms._

import slick.driver.MySQLDriver.api._

class Users(tag: Tag) extends Table[(Int, String)](tag, "USERS") {
  def id = column[Int]("ID", O.PrimaryKey)
  def name = column[String]("NAME")
  def * = (id, name)
}

object Users {
  val users = TableQuery[Users]
  val form = Form(tuple("id" -> longNumber, "name" -> nonEmptyText(maxLength = 12)))
}
