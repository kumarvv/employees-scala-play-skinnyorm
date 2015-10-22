package controllers


import com.github.tototoshi.play2.json4s.native.Json4s
import play.api.mvc._
import models.DAO
import org.json4s._
import org.json4s.jackson._
import play.twirl.api.Html

trait AppController[T] extends Controller with Json4s {

  implicit val formats = DefaultFormats

  def dao: DAO[T]
  val renderList: Option[String => Html]

  def all = Action { implicit req =>
    render {
      case Accepts.Html() =>
        val json = Serialization.write(dao.findAll())
        Ok(renderList.get(json));
      case _ =>
        Ok(encodeJson(dao.findAll()))
    }
  }

  def create: Action[JValue]

  def update(id: Long): Action[JValue]

  def delete(id: Long) = Action {
    dao.findById(id) match {
      case Some(d) => dao.deleteById(id); Ok
      case _ => NotFound
    }
  }

  def encodeJson(src: AnyRef): JValue = {
    implicit val formats = Serialization.formats(NoTypeHints)
    Extraction.decompose(src)
  }
}
