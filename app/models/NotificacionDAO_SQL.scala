package models

import java.util.{ Date }

import play.api._
import play.api.mvc._
import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._
import models._

import org.h2.jdbc.JdbcSQLException

class NotificacionDAO_SQL extends NotificacionDAO {

  var lastNotificationSequenceNumber: Long = 0;

  val parser = {
    get[Long]("notificacion.id") ~
      get[String]("notificacion.contenido") ~
      get[Long]("notificacion.fk_tipo") ~
      get[Date]("notificacion.fecha_creacion") ~
      get[Long]("notificacion.id_usuario_generador") ~
      get[Option[Long]]("notificacion.fk_amigo") map {
        case id ~ contenido ~ fechaCreacion ~ tipo ~ usuarioGenerador ~ idAmigo => Notificacion(id, contenido, tipo, fechaCreacion, usuarioGenerador, idAmigo)
      }
  }

  override def insertNotification(newNotification: Notificacion, notificatedUserId: Long) {

    var fkAmigo: Option[String] = null
    var idAmigo = newNotification.getIdAmigo
    idAmigo match {
      case Some(amigo) =>

        fkAmigo = Some(amigo.toString)

      case _ =>

        fkAmigo = Some("null");

    }

    try {
      DB.withConnection { implicit connection =>
        SQL(
          """
		INSERT INTO NOTIFICACION VALUES( nextval('seq_notificacion'),{contenido},
		NOW(),""" + fkAmigo.get + """,{currentUserId},{tipo})
		""").on(
            'contenido -> newNotification.getContenido,
            'tipo -> newNotification.getTipo,
            'currentUserId -> newNotification.getIdUsuarioGenerador).executeUpdate()

        lastNotificationSequenceNumber = SQL("select currval('seq_notificacion')").as(scalar[Long].single)

        SQL(
          """
	   INSERT INTO NOTIFICACION_USUARIO VALUES({notificatedUserId} ,currval('seq_notificacion'))
						""").on(
            'notificatedUserId -> notificatedUserId).executeUpdate()
      }
    } catch {
      case e: JdbcSQLException => throw DAOException.create(e.getMessage())
    }
  }

  override def getTypeNotificationNumberFormat(typeNotification: String): Int = {

    try {
      DB.withConnection { implicit connection =>
        SQL("""
				select tipo_notificacion.id 
				from TIPO_NOTIFICACION
				where nombre = {nombre} 

				""").on('nombre -> typeNotification).as(scalar[Int].single)
      }
    } catch {
      case e: JdbcSQLException => throw DAOException.create(e.getMessage())
    }

  }

  override def getNotificationsByUserAndType(userId: Long, typex: String): List[Notificacion] = {

    try {
      DB.withConnection { implicit connection =>

        val notifications = SQL(
          """
				select * from NOTIFICACION, TIPO_NOTIFICACION,NOTIFICACION_USUARIO 
				where notificacion.fk_tipo = tipo_notificacion.id 
				and NOTIFICACION_USUARIO.fk_notificacion = NOTIFICACION.id
				and NOTIFICACION_USUARIO.fk_usuario = {userId}
				and tipo_notificacion.nombre = {typex} order by NOTIFICACION.fecha_creacion

				""").on(
            'userId -> userId, 'typex -> typex).as(this.parser *)

        notifications

      }
    }

  }

  override def deleteFriendshipRequestNotifications(userId: Long, notificationId: Long) {
    try {
      DB.withConnection { implicit connection =>
        SQL("delete from notificacion_usuario where fk_usuario = {userId} and fk_notificacion = {notificationId}").on(
          'userId -> userId,
          'notificationId -> notificationId).executeUpdate()
      }
    }
  }

  override def insertNotificationMulticast(users: List[Usuario], notificationId: Long) {

    try {
      DB.withConnection { implicit connection =>

        for (usuario <- users) {
          SQL(

            "INSERT INTO NOTIFICACION_USUARIO VALUES(" + usuario.getId + ",{notificationId})").on(
              'notificationId -> notificationId).executeUpdate()
        }
      }
    } catch {
      case e: JdbcSQLException => throw DAOException.create(e.getMessage())
    }

  }

  override def getNewNotificationLastIdFromSequence(): Long = {

    return lastNotificationSequenceNumber
  }

  override def getNotificationsByUser(userId: Long): List[Notificacion] = {

    try {
      DB.withConnection { implicit connection =>

        val notifications = SQL(
          """
				select * from NOTIFICACION,NOTIFICACION_USUARIO 
				where NOTIFICACION_USUARIO.fk_notificacion = NOTIFICACION.id
				and NOTIFICACION_USUARIO.fk_usuario = {userId}
		
				""").on(
            'userId -> userId).as(this.parser *)

        notifications

      }
    } catch {
      case e: JdbcSQLException => throw DAOException.create(e.getMessage())
    }

  }

}