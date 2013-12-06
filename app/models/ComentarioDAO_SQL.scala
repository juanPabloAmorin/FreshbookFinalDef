package models

import java.util.{ Date }

import play.api._
import play.api.mvc._
import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._
import models._

class ComentarioDAO_SQL extends ComentarioDAO {

	val parser = {
			get[Long]("comentario.id") ~
			get[String]("comentario.contenido") ~
			get[Date]("comentario.fecha") ~
			get[Option[Long]]("comentario.fk_contenido") ~
			get[Option[Long]]("comentario.fk_album") ~
			get[Option[Long]]("comentario.fk_comentario") ~
			get[Long]("comentario.fk_usuario") map {
			case id ~ contenido ~ fecha ~ fkContenido ~ fkAlbum ~ 
			fkComentario ~ fkUsuario => 
			Comentario(id, contenido, fecha, fkContenido, fkAlbum, fkComentario, fkUsuario)
			}
	}
	
	override def insertComentario(newComentario: Comentario): Long = {
      
	    var fkContenido = "null"
	    var fkAlbum = "null";
	    var fkComentario= "null";
        
	    var newComentarioContenido = newComentario.getFkContenido;
	    var newComentarioAlbum = newComentario.getFkAlbum;
        var newComentarioComentario = newComentario.getFkComentario;

        newComentarioContenido match {
        case Some(contenido) =>

          fkContenido = contenido.toString

        case _ =>

          fkContenido = "null";

        }
        
        newComentarioAlbum match {
        case Some(contenido) =>

          fkAlbum = contenido.toString

        case _ =>

          fkAlbum = "null";

        }
        
        newComentarioComentario match {
        case Some(contenido) =>

          fkComentario = contenido.toString

        case _ =>

          fkComentario = "null";

        }
	  
        var commentId:Long = 0
        
		DB.withConnection { implicit connection =>
		SQL(
				"""
				INSERT INTO COMENTARIO VALUES( nextval('seq_comentario'),{contenido},NOW(),
		        """+fkContenido+""","""+fkAlbum+""","""+fkComentario+""",{fkUsuario})
				""").on('contenido -> newComentario.getContenido,
				        'fkUsuario -> newComentario.getFkUsuario
				    
				).executeUpdate()

				commentId = SQL("select currval('seq_comentario')").as(scalar[Long].single)
		}
		
		return commentId
		
	}

	override def getCommentsForAlbum(albumId: Long): List[Comentario] =
   {
	  DB.withConnection { implicit connection =>
	    
         SQL("select * from COMENTARIO where fk_album = {albumId} order by fecha desc")
         .on('albumId -> albumId).as(this.parser *)
      }
   }
	

	override def deleteCommentById(commentId: Long) {
	   
	    DB.withConnection { implicit connection =>
		SQL("delete from comentario where comentario.id = {commentId}"
		    ).on('commentId -> commentId).executeUpdate()

		}
	  
	}
	
	override def commentCountResponses(commentId: Long): Long = {
	  
	    DB.withConnection { implicit connection =>
	    
         SQL("""
              select count(*) from COMENTARIO where fk_comentario = {commentId} 
             
             """
             )
         .on('commentId -> commentId).as(scalar[Long].single)
      }
	}
	
	
    def getResponsesForComment(commentId: Long): List[Comentario] =
    {
	  DB.withConnection { implicit connection =>
	    
         SQL("select * from COMENTARIO where fk_comentario = {commentId} order by fecha desc")
         .on('commentId -> commentId).as(this.parser *)
      }
   }

	
	
	
}