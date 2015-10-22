package models

import scalikejdbc._

case class Dept(id: Long, name: String)

object Dept extends DAO[Dept] {

  override lazy val defaultAlias = createAlias("d")
  private[this] lazy val d = defaultAlias

  override def extract(rs: WrappedResultSet, rn: ResultName[Dept]): Dept = {
    autoConstruct(rs, rn);
  }

}