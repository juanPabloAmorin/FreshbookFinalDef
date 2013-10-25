package models

import play.api._
import play.api.mvc._
import play.api.db._

import anorm._
import anorm.SqlParser._

trait UsuarioDAO
{
     def findUserByEmail(email: String): Option[Usuario];
     def updateUserFirstName(firstName: String, userId: Long);
     def updateUserSecondName(secondName: String, userId: Long);
     def updateUserFirstLastname(firstLastname: String, userId: Long);
     def updateUserSecondLastname(secondLastname: String, userId: Long);
     def updateUserNickname(nickname: String, userId: Long);
     def findUserById(id: Long): Option[Usuario];
     def findFriendsByUser(userId: Long): List[Usuario];
     def insertUser(newUsuario: Usuario)
     def getNewUserLastIdFromSequence(): Long
     def searchUsersByFullNamePattern(namePattern: String): List[Usuario]
     def updateUserTwitter(twitter: String, userId: Long)
     def updateUserFacebook(facebook: String, userId: Long)
     def updateUserGoogle(google: String, userId: Long)
     
}