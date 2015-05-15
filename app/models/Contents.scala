package models

import play.api.data._
import play.api.data.Forms._

import java.sql.Date

import slick.driver.MySQLDriver.api._

class Contents(tag: Tag) extends Table[(Int, Int, String, Date)](tag, "CONTENTS") {
  def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
  def userId = column[Int]("USER_ID")
  def content = column[String]("CONTENT")
  def date = column[Date]("DATE")
  def * = (id, userId, content, date)
}

object Contents {
  val contents = TableQuery[Contents]
  val form = Form(tuple("content" -> nonEmptyText(maxLength = 200), "date" -> date("yyyy-MM-dd")))
}
