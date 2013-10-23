package models

import play.api._
import play.api.mvc._
import play.api.db._

import anorm._
import anorm.SqlParser._

trait UsuarioDAO
{
     def findUserByEmail(email: String): Option[Usuario];
     def updateUserNames(names: String, userId: Long);
     def updateUserLastnames(lastnames: String, userId: Long);
     def updateUserNickname(nickname: String, userId: Long);
     def updateUserEmail(email: String, userId: Long);
     def findUserById(id: Long): Option[Usuario];
     def findFriendsByUser(userId: Long): List[Usuario];
     def insertUser(newUsuario: Usuario)
     
     
}