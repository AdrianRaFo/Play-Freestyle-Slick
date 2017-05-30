/**
 * https://github.com/AdrianRaFo
 */
package dao

import freestyle._
import freestyle.implicits._
import freestyle.slick._
import freestyle.slick.implicits._

import _root_.slick.jdbc.PostgresProfile.api._

import scala.concurrent.Future
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Await
import scala.util.{Failure, Success}

import dao.Tables._

object UserDataDB {

  implicit val db: Database = Database.forConfig("postgres")

  def getUser(email: String) =
    SlickM[SlickM.Op].run[UserdataRow](Userdata.filter(_.email === email).result.head)

  def insertUser(userdata: UserdataRow) = {
    SlickM[SlickM.Op].run[String](
      (Userdata returning Userdata
        .map(_.email)) += userdata)
  }

  def listUser =
    SlickM[SlickM.Op].run[List[UserdataRow]](Userdata.result.map[List[UserdataRow]](_.toList))

}
