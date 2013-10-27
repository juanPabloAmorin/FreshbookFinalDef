package models

import play.api._
import play.api.mvc._
import play.api.db._

import anorm._
import anorm.SqlParser._

trait UsuarioDAO
{
  /*El metodo findUserByEmail permite obtener un objeto de la clase usuario dado su
   * correo eelectronico */
     
  def findUserByEmail(email: String): Option[Usuario];
  
  /*El metodo updateUserFirstName permite actualizar el primer nombre de un usuario*/
     
  def updateUserFirstName(firstName: String, userId: Long);
  
  /*El metodo updateUserSecondName permite actualizar el segundo nombre de un usuario*/
     
  def updateUserSecondName(secondName: String, userId: Long);
  
  /*El metodo updateUserFirstLastname permite actualizar el primer apellido de un usuario*/
    
  def updateUserFirstLastname(firstLastname: String, userId: Long);
     
  /*El metodo updateUserSecondLastname permite actualizar el srgundo apellido de un usuario*/
  
  def updateUserSecondLastname(secondLastname: String, userId: Long);
     
  /*El metodo updateNickname permite actualizar el nombre de usuario apellido de un usuario*/
  
  def updateUserNickname(nickname: String, userId: Long);
  
  /*El findUserById permite obtener un objeto de la clase usuario dado su id*/
  
  def findUserById(id: Long): Option[Usuario];
  
  /*el metodo findFriendsByUser permite obtener una lista de amigos de un usuario dado su id*/
     
  def findFriendsByUser(userId: Long): List[Usuario];
  
  /*el metodo insertUser permite insertar un nuevo objeto de la clase usuario en 
   * la base de datos*/
  
  def insertUser(newUsuario: Usuario)
  
  /* el metodo getNewUserLastIdFromSequence permite obtener el id del ultimo usuario
   * insertado en la base de datos en la sesion actual*/
     
  def getNewUserLastIdFromSequence(): Long
  
  /*el metodo searchUsersByFullNamePattern permite obtener una lista de usuarios cuyos nombres
   * o apellidos comiencen con la combinacion de caracteres indicada en namePattern*/
     
  def searchUsersByFullNamePattern(namePattern: String): List[Usuario]
     
  /*el metodo updateUserTwitter permite actualizar el link a la cuenta de twitter del 
   * usuario principal */
  
  def updateUserTwitter(twitter: String, userId: Long)
  
  /*el metodo updateUserFacebook permite actualizar el link a la cuenta de facebook del 
   * usuario principal */
  
  def updateUserFacebook(facebook: String, userId: Long)
     
  /*el metodo updateUserGoogle permite actualizar el link a la cuenta de google del 
   * usuario principal */
  
  def updateUserGoogle(google: String, userId: Long)
  
  /*el metodo updateUserLatitud permite actualizar la coordenada de latitud en la ubicacion 
   * geografica del usuario */
     
  def updateUserLatitud(latitud: String, userId: Long)
  
  /*el metodo updateUserLongitud permite actualizar la coordenada de longitud en la 
   * ubicacion geografica del usuario */
  
  def updateUserLongitud(longitud: String, userId: Long)
  
  /*El metodo updateUserLocation permite actualizar la ubicacion geografica del usuario*/
     
  def updateUserLocation(fkLugar: Long, userId: Long) 
  
  /*el metodo isThisUserAFriend devuelve true si un usuario es amigo de otro y false en caso
   * contrario dados sus id*/
     
  def isThisUserAFriend(userId: Long, friendId: Long): Boolean
  
  /*el metodo getUserFriendshipStatus permite determinar a traves de un numero entero (0,1)
   * el estatus de la solicitud de una solicitud de amistad (en espera, aprobado)*/
     
  def getUserFriendshipStatus(userId: Long, friendId: Long): Option[Int]
  
  /*el metodo insertAmistad permite agregar a la base de dato un nuevo vinculo de amistad
   * entre dos usuarios dados sus id*/
     
  def insertAmistad(friend1Id: Long, friend2Id: Long)
  
  /*el metodo confimFriendship cambia el estatus de la amistad a aprobado(1) */
    
  def confimFriendship(friend1Id: Long, friend2Id: Long)
  
  /*el metodo getUsersByFullNamePart devuelve un objeto de la clase usuario si el parametro
   * namePart coincide con alguno de sus nombres o apellidos*/
     
  def getUsersByFullNamePart(namePart: String): List[Usuario]
  
  /*el metodo deleteAmistadByUsersId permite eliminar un vinculo de amistad dados los id
   * de los usuarios*/
  
  def deleteAmistadByUsersId(friend1Id: Long, friend2Id: Long)
     
}