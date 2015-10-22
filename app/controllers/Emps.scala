package controllers

import models._
import org.json4s.jackson.Serialization
import play.api.mvc._
import play.twirl.api.Html

class Emps extends AppController[Emp] {

  override def dao: DAO[Emp] = Emp

  override val renderList: Option[(String) => Html] = None

  override def all = Action { implicit req =>
    render {
      case Accepts.Html() => {
        val emps = Serialization.write(dao.findAll())
        val depts = Serialization.write(Dept.findAll())
        Ok(views.html.emp.list.render(emps, depts));
      }
      case _ => {
        Ok(encodeJson(dao.findAll()))
      }
    }
  }

  override def create = Action(json) { req =>
    val o = req.body.extract[Emp]
    val id = Emp.createWithAttributes('name -> o.name, 'deptId -> o.deptId);
    Ok(encodeJson(Emp.findById(id)))
  }

  override def update(id: Long) = Action(json) { req =>
    Emp.findById(id) match {
      case Some(e) => {
        val o = req.body.extract[Emp]
        Emp.updateById(id).withAttributes('name -> o.name, 'deptId -> o.deptId.getOrElse(null))
        Ok(encodeJson(Emp.findById(id)))
      }
      case _ => NotFound
    }
  }
}
