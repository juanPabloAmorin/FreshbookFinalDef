package models

import java.util.{ Date }

import play.api._
import play.api.mvc._
import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._
import models._

class NotificacionDAO_SQL extends NotificacionDAO {

  val parser = {
    get[Long]("notificacion.id") ~
      get[String]("notificacion.contenido") ~
      get[Long]("notificacion.tipo") ~
      get[Date]("notificacion.fecha_creacion") ~
      get[Option[Long]]("notificacion.fk_amigo") map {
        case id ~ contenido ~ fechaCreacion ~ tipo ~ idAmigo  => Notificacion(id, contenido, tipo, fechaCreacion, idAmigo)
      }
  }
  
   override def insertNotification(newNotification: Notificacion,notificatedUserId: Long)
   {
     
     var fkAmigo: Option[String] = null
     var idAmigo  = newNotification.getIdAmigo
     idAmigo match{
       case Some(amigo) =>
         
          fkAmigo = Some(amigo.toString)
         
       case _ =>
          
         fkAmigo = Some("null");
          
     }
     
     DB.withConnection { implicit connection =>
      SQL(
        """
          INSERT INTO NOTIFICACION VALUES( nextval('seq_notificacion'),{contenido},
          NOW(),"""+fkAmigo.get+""",{tipo})
        """
      
      ).on(
          'contenido->newNotification.getContenido,
          'tipo->newNotification.getTipo  
          
      ).executeUpdate() 
      
      SQL(
        """
          INSERT INTO NOTIFICACION_USUARIO VALUES({notificatedUserId} ,currval('seq_notificacion'))
        """
      
      ).on(
          'notificatedUserId->notificatedUserId  
          
      ).executeUpdate() 
    }
  }
   
   override def getTypeNotificationNumberFormat(typeNotification: String): Int = {
     
       DB.withConnection { implicit connection =>
      SQL("""
          select tipo_notificacion.id 
          from TIPO_NOTIFICACION
          where nombre = {nombre} 
          
          """).on('nombre -> typeNotification).as(scalar[Int].single)
    }
   
   }
   
   

}