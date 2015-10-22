package controllers

import play.api.mvc._
import models._
import org.json4s._
import play.twirl.api.Html

class Depts extends AppController[Dept] {

  override def dao: DAO[Dept] = Dept

  override val renderList: Option[(String) => Html] = Some(views.html.dept.list.render _)

  override def create = Action(json) { req =>
    val o = req.body.extract[Dept]
    val id = Dept.createWithAttributes('name -> o.name);
    Ok(encodeJson(Dept.findById(id)))
  }

  override def update(id: Long) = Action(json) { req =>
    Dept.findById(id) match {
      case Some(d) => {
        val o = req.body.extract[Dept]
        Dept.updateById(id).withAttributes('name -> o.name)
        Ok(encodeJson(o))
      }
      case _ => NotFound
    }
  }
}
