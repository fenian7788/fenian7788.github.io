package github.fenian7788.io

import java.time.ZonedDateTime

import scalikejdbc._

object ScalaJdbcApp {

  val driverClass = "com.mysql.jdbc.Driver"
  val jdbcUrl = "jdbc:mysql://192.168.137.189:3306/hive";
  val username = "root";
  val pwd = "123456";


  def main(args: Array[String]): Unit = {
    Class.forName(driverClass)
    ConnectionPool.singleton(jdbcUrl, username, pwd)

    //建表
    doBefore

    //提交一条数据
    doInsert("Peter")
    //提交多条数据
    doInsert("Mike","Tom","Ken")

    //查询列表
    val list = doList()
    list.foreach(println) //Person(1,Peter,2018-12-09T15:20:27+08:00[Asia/Shanghai])....

    //根据ID获取一条记录
    val person1 = doGet(1)
    println(person1) //Person(1,Peter,2018-12-09T15:20:27+08:00[Asia/Shanghai])

    //更新一条记录
    val status = doUpdate("Peter Wu",person1.get.id)
    println(status) //1


    val statusD = doDelete(4)
    println(statusD) //false -->已经删除了

    doAfter

  }

  def doBefore = {
    implicit val session = AutoSession
    sql"""
       CREATE TABLE IF NOT EXISTS Person(
         id int PRIMARY KEY NOT NULL auto_increment,
         name varchar(64),
         created_time timestamp not null DEFAULT current_timestamp
      )ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1
      """.execute.apply()
  }

  //提交一条
  def doInsert(name: String) = {
    implicit val session = AutoSession
    sql"insert into Person(name) values (${name})".update().apply()
  }

  //批量提交
  def doInsert(names: String*) = {
    implicit val session = AutoSession
    names.foreach(
      name => sql"insert into Person(name) values (${name})".update().apply()
    )
  }

  //更新
  def doUpdate(name: String, id: Int) = {
    implicit val session = AutoSession
    sql"update Person set name = ${name} where id = ${id}".update().apply()
  }

  //删除
  def doDelete(id: Int) = {
    implicit val session = AutoSession
    sql"delete from Person where id = ${id}".execute().apply()
  }

  //根据ID获取一条
  def doGet(id: Int) = {
    implicit val session = AutoSession
    sql"select * from person where id = ${id}".map((Person(_))).single().apply().get
  }

  //获取列表
  def doList() = {
    implicit val session = AutoSession
    sql"select * from person".map((Person(_))).list().apply()
  }

  case class Person(id: Int, name: String, createdTime: ZonedDateTime)

  object Person extends SQLSyntaxSupport[Person] {
    override val tableName = "Person"

    def apply(rs: WrappedResultSet) = {
      new Person(rs.int("id"), rs.string("name"), rs.zonedDateTime("created_time"))
    }
  }


  def doAfter = {
    implicit val session = AutoSession
    sql"DROP TABLE IF EXISTS Person".execute.apply()
  }


}
