package models

import java.util.{ Date }

import play.api._
import play.api.mvc._
import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._
import models._

trait ComentarioDAO {
  /* El metodo insertComentario permite insertar un nuevo objeto de la clase Comentario en la *
   * base de datos y retorna su id*/

  def insertComentario(newComentario: Comentario): Long

  /* el metodo getCommentsForAlbum  permite obtener una lista de comentarios asociados a un 
   * album */

  def getCommentsForAlbum(albumId: Long): List[Comentario]

  /* el metodo getResponsesForComment  permite obtener una lista de comentarios asociados a un 
   * album */

  def getResponsesForComment(commentId: Long): List[Comentario]

  /*El metodo deleteComment elimina un comentario de la base de datos*/

  def deleteCommentById(commentId: Long)

  /*El metodo commentCountResponses devuelve la cantidad de respuestas sobre otro 
     * comentario */

  def commentCountResponses(commentId: Long): Long
}