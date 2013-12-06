package models

import java.util.{Date}

import play.api._
import play.api.mvc._
import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._
import models._


trait AlbumDAO
{
 
/* El metodo findAlbumsByUser permite obtener una lista de albumes dado el id de un usuario*/
  
  def findAlbumsByUser(userId: Long): List[Album];
  
/* El metodo getNumberOfFilesInAlbum devuelde un numero que representa la cantidad de  *
 * archivos multimedia asociados a un album dado su id                                 */ 
 
  def getNumberOfFilesInAlbum(albumId: Long): Long;
  
  /* El metodo insertar album permite insertar un nuevo objeto de la clase album en la *
   * base de datos*/
      
  def insertAlbum(newAlbum: Album)
          
  /*El metodo deleteAlbumById permite borrar un album de la base de datos dado su id*/
      
  def deleteAlbumById(albumId: Long)
  
  /* El metodo getNewAlbumLastIdFromSequence permite obtener el id del ultimo album  *
   * insertado en la base de datos                                                   */
   
  def getNewAlbumLastIdFromSequence(): Long
  
  /*el metodo getContenidoByAlbum permite obtener una lista de contenidos multimedia de 
   * un album dado su id*/
     
  def getContenidoByAlbum(albumId: Long): List[ContenidoMultimedia];
  
  
  /*El findAlbumById permite obtener un objeto de la clase Album dado su id*/
  
  def findAlbumById(id: Long): Option[Album];
          
      
}