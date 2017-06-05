package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.libs.json._
import play.filters.csrf._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

import cats.implicits._
import freestyle.slick._
import freestyle.slick.implicits._

import dao.Tables.UserdataRow
import dao.UserDataDB
import dao.UserDataDB._

@Singleton
class HomeController @Inject()(val messagesApi: MessagesApi) extends Controller with I18nSupport {
  val userForm = Form(
    mapping(
      "email"    -> email,
      "username" -> nonEmptyText,
      "age"      -> optional(number)
    )(UserdataRow.apply)(UserdataRow.unapply))

  def index = Action { implicit request =>
    Ok(views.html.index(userForm))
  }

  def user = Action.async { implicit request ⇒
    userForm.bindFromRequest.fold(
      formWithErrors => {
        // binding failure, you retrieve the form containing errors:
        Future(
          BadRequest(views.html
            .index(formWithErrors, s"Validation error in fields: ${formWithErrors.errors.map(_.key).mkString(", ")}")))
      },
      userData => {
        /* binding success, you get the actual value. */
        UserDataDB.insertUser(userData).interpret[Future] mapAll [Result] {
          case Success(r) ⇒ Redirect(routes.HomeController.getUsers)
          case Failure(e) ⇒ InternalServerError(views.html.index(userForm, e.getMessage))
        }
      }
    )
  }

  def getUsers = Action.async { implicit request =>
    UserDataDB.listUser.interpret[Future] mapAll [Result] {
      case Success(result: List[UserdataRow]) ⇒ Ok(Json.toJson(result))
      case Failure(_)                         ⇒ InternalServerError(views.html.user(None))
    }
  }

}
