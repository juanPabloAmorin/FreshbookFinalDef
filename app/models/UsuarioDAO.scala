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
     def updateUserLatitud(latitud: String, userId: Long)
     def updateUserLongitud(longitud: String, userId: Long)
     def updateUserLocation(fkLugar: Long, userId: Long)
     def isThisUserAFriend(userId: Long, friendId: Long): Boolean
     def getUserFriendshipStatus(userId: Long, friendId: Long): Option[Int]
     def insertAmistad(friend1Id: Long, friend2Id: Long)
     def confimFriendship(friend1Id: Long, friend2Id: Long)
     def getUsersByFullNamePart(namePart: String): List[Usuario]
     
}