/**
 * https://github.com/AdrianRaFo
 */
import scala.util.Try
import scala.concurrent.{ExecutionContext, Future, Promise}
import dao.Tables.UserdataRow
import play.api.libs.json._
import play.api.libs.json.Reads._
import play.api.libs.functional.syntax._
package object controllers {

  implicit class FutureExtensions[T](f: Future[T]) {
    def mapAll[Target](m: Try[T] => Target)(implicit ec: ExecutionContext): Future[Target] = {
      val p = Promise[Target]()
      f.onComplete { r =>
        p success m(r)
      }(ec)
      p.future
    }
  }
  implicit def conv[T](a: T): Option[T] = Some(a)

  implicit val userdataFormat: Format[UserdataRow] = (
    (JsPath \ "email").format[String](email) and
      (JsPath \ "username").format[String] and
      (JsPath \ "age").formatNullable[Int]
  )(UserdataRow.apply _, unlift(UserdataRow.unapply _))

}
