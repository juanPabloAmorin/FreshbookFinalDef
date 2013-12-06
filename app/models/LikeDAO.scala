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
    
 
}