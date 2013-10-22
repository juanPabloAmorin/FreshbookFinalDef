package models

import play.api._
import play.api.mvc._
import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._
import models._

class LugarDAO_SQL extends LugarDAO {

  val parser = {
    get[Long]("lugar.id") ~
      get[String]("lugar.nombre") ~
      get[String]("lugar.tipo")  map {
        case id ~ nombre ~ tipo => Lugar(id, nombre, tipo)
      }
  }
  
  
  override def getLugarById(id: Long): Option[Lugar] = {
    DB.withConnection { implicit connection =>
      SQL("select * from LUGAR where id = {id} ").on('id -> id).as(this.parser.singleOpt)
    }

  }
  
  override def insertLugar(newLugar: Lugar) {

     DB.withConnection { implicit connection =>
      SQL(
        """
          INSERT INTO LUGAR VALUES( (select next value for seq_lugar),{nombre},{tipo},
          {fk_lugar})
        """
      
      ).on(
          'nombre->newLugar.getNombre,
          'tipo->newLugar.getTipo,
          'fk_lugar->Option(newLugar.getLugar.getId).getOrElse{"null"}
                   
      ).executeUpdate() 
    }
  }
  
  override def getLugarByNameAndType(name: String, typo: String): Option[Lugar] = {
       DB.withConnection { implicit connection =>
      SQL("select * from LUGAR where nombre = {name} and tipo = {typo} ").on('name -> name, 'typo -> typo).as(this.parser.singleOpt)
    }
  }
  
}