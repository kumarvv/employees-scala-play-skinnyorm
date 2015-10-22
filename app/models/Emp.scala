package models

import scalikejdbc._

case class Emp(id: Long, name: String, deptId: Option[Long] = None, dept: Option[Dept] = None)

object Emp extends DAO[Emp] {

  override lazy val defaultAlias = createAlias("e")
  private[this] lazy val e = defaultAlias

  override def extract(rs: WrappedResultSet, rn: ResultName[Emp]): Emp = {
    autoConstruct(rs, rn, "dept");
  }

  belongsTo[Dept](Dept, (e, d) => e.copy(dept = d)).byDefault

}