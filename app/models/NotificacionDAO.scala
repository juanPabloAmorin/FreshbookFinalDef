package models

import java.util.{Date}

import play.api._
import play.api.mvc._
import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._
import models._


trait NotificacionDAO
{
      def insertNotification(newNotification: Notificacion,notificatedUserId: Long)
      def getTypeNotificationNumberFormat(typeNotification: String): Int
      def getNotificationsByUserAndType(userId: Long, typex: String): List[Notificacion]
      def deleteFriendshipRequestNotifications(userId: Long, friendId: Long)
      def insertNotificationMulticast(users: List[Usuario],notificationId: Long)
      def getNewNotificationLastIdFromSequence(): Long
      def getNotificationsByUser(userId: Long): List[Notificacion]
      
}