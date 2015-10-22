package models

import scalikejdbc._
import skinny.orm._

trait DAO[T] extends SkinnyCRUDMapper[T] {

}