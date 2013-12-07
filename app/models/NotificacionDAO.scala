package models

import java.util.{ Date }

import play.api._
import play.api.mvc._
import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._
import models._

trait NotificacionDAO {
  /*El metodo insertNotificacion permite insertar un nuevo objeto de la clase notificacion
   * en la base de datos */

  def insertNotification(newNotification: Notificacion, notificatedUserId: Long)

  /*el metodo getTypeNotificationNumberFormat devuelve el numero enter correspondiente 
   * al tipo  de notificacion especificado */

  def getTypeNotificationNumberFormat(typeNotification: String): Int

  /*el metodo getNotificationsByUserAndType permite obtener una lista de objetos de la 
   * clase Notificacion que deban mostrarse a un usuario*/

  def getNotificationsByUserAndType(userId: Long, typex: String): List[Notificacion]

  /*el metodo deleteFriendshipRequestNotifications permite borrar una notificacion de
   * solicitud de amistad luego que esta ha sido confirmada */

  def deleteFriendshipRequestNotifications(userId: Long, friendId: Long)

  /*el metodo insertNotificationMulticast permite enviar una notificacion a un conjunto de
   * usuarios */

  def insertNotificationMulticast(users: List[Usuario], notificationId: Long)

  /*el metodo getNewNotificationLastIdFromSequence devuelve el id de la ultima notificacion
   * creado durante la sesion actual*/

  def getNewNotificationLastIdFromSequence(): Long

  /* el metodo getNotificationsByUser permite obtener las notificaciones que deben ser  
   * mostradas a un usuario en particular */

  def getNotificationsByUser(userId: Long): List[Notificacion]

}