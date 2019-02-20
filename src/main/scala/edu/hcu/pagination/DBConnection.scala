package edu.hcu.pagination

import slick.jdbc.MySQLProfile
import slick.jdbc.MySQLProfile.api._

object DBConnection {
  def db: MySQLProfile.backend.DatabaseDef = {
    Database.forURL("jdbc:mysql://localhost:3306/pagination_assignment",
      driver = "com.mysql.jdbc.Driver",
      user = "root", password = "root")
  }

}

