package models

import java.util.{Date}

import play.api._
import play.api.mvc._
import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._
import models._


trait LikeDAO
{
  /* El metodo insertLike permite insertar un nuevo objeto de la clase Like en la *
   * base de datos*/
      
    def insertLike(newLike: Like)
   
  /* el metodo getLikesForAlbum  permite obtener la cantidad de likes asociados a un 
   * album */
    
    def getLikesForAlbum(albumId: Long): Long
    
     /* el metodo getUnlikesForAlbum  permite obtener la cantidad de unlikes asociados a un 
   * album */
    
    def getUnlikesForAlbum(albumId: Long): Long
    
    
    /* el metodo getLikesForComment  permite obtener la cantidad de likes asociados a un 
   * comentario */
    
    def getLikesForComment(commentId: Long): Long
    
     /* el metodo getUnlikesForComment  permite obtener la cantidad de unlikes asociados a un 
   * comentario */
    
    def getUnlikesForComment(commentId: Long): Long
    
     /*El metodo iLikeAlbum retorna 1 si al usuario le gusta el album y 0 si no ha seleccionado
   * ninguna preferencia*/
  
    def iLikeAlbum(idAlbum: Long, idUsuario: Long): Long
  
  /*El metodo iDontLikeAlbum retorna 1 si al usuario no le gusta el album y 0 si no ha seleccionado
   * ninguna preferencia*/
  
    def iDontLikeAlbum(idAlbum: Long, idUsuario: Long): Long
    
    /*el metodo deleteAlbumLike permite eleiminar un like de la base de datos dado el usuario que lo
     * genero y el album al que fue asociado*/
    
    def deleteAlbumLike(idUsuario: Long,idAlbum: Long)
    
     /*el metodo deleteAlbumUnlike permite eleiminar un unlike de la base de datos dado el usuario que lo
     * genero y el album al que fue asociado*/
    
    def deleteAlbumUnlike(idUsuario: Long,idAlbum: Long)
    
    /*el metodo deleteCommentLike permite eleiminar un like de la base de datos asociado a un comentario*/
    
    def deleteCommentLike(idUsuario: Long,idComment: Long)    
    
     /*el metodo deleteCommentUnlike permite eleiminar un unlike de la base de datos asociado a un comentario*/
    
    def deleteCommentUnlike(idUsuario: Long,idComment: Long) 
    
     /*El metodo iLikeComment retorna 1 si al usuario le gusta el comentario y 0 si no ha seleccionado
   * ninguna preferencia*/
  
    def iLikeComment(idComment: Long, idUsuario: Long): Long
  
  /*El metodo iDontLikeComment retorna 1 si al usuario no le gusta el comentario y 0 si no ha seleccionado
   * ninguna preferencia*/
  
    def iDontLikeComment(idComment: Long, idUsuario: Long): Long
     
   
 
}