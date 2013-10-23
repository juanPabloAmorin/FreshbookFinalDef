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
  
  var lastLugarSequenceNumber: Long = 0
  
  
  override def getLugarById(id: Long): Option[Lugar] = {
    DB.withConnection { implicit connection =>
      SQL("select * from LUGAR where id = {id} ").on('id -> id).as(this.parser.singleOpt)
    }

  }
  
  override def insertLugar(newLugar: Lugar) {

     var fkLugar: Option[String] = null
     var newLugarLugar  = newLugar.getLugar
     newLugarLugar match{
       case Some(lugar) =>
         
          fkLugar = Some(lugar.getId.toString)
         
       case _ =>
          
         fkLugar = Some("null");
          
     }
     DB.withConnection { implicit connection =>
      SQL("INSERT INTO LUGAR VALUES(nextval('seq_lugar'),{nombre},{tipo},"+ fkLugar.get +")"
      
      ).on(
          'nombre->newLugar.getNombre,
          'tipo->newLugar.getTipo
          
                   
      ).executeUpdate() 
      
      lastLugarSequenceNumber = SQL("select currval('seq_lugar')").as(scalar[Long].single)
    }
  }
  
  override def getLugarByNameAndType(name: String, typo: String): Option[Lugar] = {
       DB.withConnection { implicit connection =>
      SQL("select * from LUGAR where nombre = {name} and tipo = {typo} ").on('name -> name, 'typo -> typo).as(this.parser.singleOpt)
    }
  }
  
  override def getNewLugarLastIdFromSequence(): Long = {
    
      return lastLugarSequenceNumber
  }
  
  override def getLugarByNameAndTypeAndZone(name: String, typo: String, zone: Long): Option[Lugar] = {
       DB.withConnection { implicit connection =>
      SQL("select * from LUGAR where nombre = {name} and tipo = {typo} and fk_lugar = {zone}").on('name -> name, 'typo -> typo,'zone -> zone).as(this.parser.singleOpt)
    }
  }
  
}