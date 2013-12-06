package models

import java.util.{ Date }

import play.api._
import play.api.mvc._
import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._
import models._

class LikeDAO_SQL extends LikeDAO {

	val parser = {
			get[Long]("tlike.id") ~
			get[String]("tlike.tipo") ~
			get[Date]("tlike.fecha") ~
			get[Option[Long]]("tlike.fk_contenido") ~
			get[Option[Long]]("tlike.fk_album") ~
			get[Option[Long]]("tlike.fk_comentario") ~
			get[Long]("tlike.fk_usuario") map {
			case id ~ tipo ~ fecha ~ fkContenido ~ fkAlbum ~ 
			fkComentario ~ fkUsuario => 
			Like(id, tipo, fecha, fkContenido, fkAlbum, fkComentario, fkUsuario)
			}
	}
	
	override def insertLike(newLike: Like) {
      
	    var fkContenido = "null"
	    var fkAlbum = "null";
	    var fkComentario= "null";
        
	    var newLikeContenido = newLike.getFkContenido;
	    var newLikeAlbum = newLike.getFkAlbum;
        var newLikeComentario = newLike.getFkComentario;

        newLikeContenido match {
        case Some(like) =>

          fkContenido = like.toString

        case _ =>

          fkContenido = "null";

        }
        
        newLikeAlbum match {
        case Some(like) =>

          fkAlbum = like.toString

        case _ =>

          fkAlbum = "null";

        }
        
        newLikeComentario match {
        case Some(like) =>

          fkComentario = like.toString

        case _ =>

          fkComentario = "null";

        }
	  
		DB.withConnection { implicit connection =>
		SQL(
				"""
				INSERT INTO TLIKE VALUES( nextval('seq_like'),{tipo},NOW(),
		        """+fkContenido+""","""+fkAlbum+""","""+fkComentario+""",{fkUsuario})
				""").on('tipo -> newLike.getTipo,
				        'fkUsuario -> newLike.getFkUsuario
				    
				).executeUpdate()

		}
		
		
	}

	override def getLikesForAlbum(albumId: Long): Long =
   {
	  DB.withConnection { implicit connection =>
	    
         SQL("""
               select count(*) from TLIKE  where fk_album = {albumId} and tipo = 'L'
             """
              
             )
         .on('albumId -> albumId).as(scalar[Long].single)
      }
   }
	
   override def getUnlikesForAlbum(albumId: Long): Long =
   {
	  DB.withConnection { implicit connection =>
	    
         SQL("""
              select count(*) from TLIKE where fk_album = {albumId} and tipo = 'U' 
             
             """
             )
         .on('albumId -> albumId).as(scalar[Long].single)
      }
   }
   
   
   override def getLikesForComment(commentId: Long): Long =
   {
	  DB.withConnection { implicit connection =>
	    
         SQL("""
               select count(*) from TLIKE  where fk_comentario = {commentId} and tipo = 'L'
             """
              
             )
         .on('commentId -> commentId).as(scalar[Long].single)
      }
   }
	
   override def getUnlikesForComment(commentId: Long): Long =
   {
	  DB.withConnection { implicit connection =>
	    
         SQL("""
              select count(*) from TLIKE where fk_comentario = {commentId} and tipo = 'U' 
             
             """
             )
         .on('commentId -> commentId).as(scalar[Long].single)
      }
   }
   
  
	
}