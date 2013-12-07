package models

import java.util.{ Date }

import play.api._
import play.api.mvc._
import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._
import models._

import org.h2.jdbc.JdbcSQLException


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
	  
        try
        {
		DB.withConnection { implicit connection =>
		SQL(
				"""
				INSERT INTO TLIKE VALUES( nextval('seq_like'),{tipo},NOW(),
		        """+fkContenido+""","""+fkAlbum+""","""+fkComentario+""",{fkUsuario})
				""").on('tipo -> newLike.getTipo,
				        'fkUsuario -> newLike.getFkUsuario
				    
				).executeUpdate()

		}
        }catch {
      case e: JdbcSQLException => throw DAOException.create(e.getMessage())
    }
        
		
		
	}

	override def getLikesForAlbum(albumId: Long): Long =
   {
	  try{
	  DB.withConnection { implicit connection =>
	    
         SQL("""
               select count(*) from TLIKE  where fk_album = {albumId} and tipo = 'L'
             """
              
             )
         .on('albumId -> albumId).as(scalar[Long].single)
      }
	  }catch {
      case e: JdbcSQLException => throw DAOException.create(e.getMessage())
    }
   }
	
   override def getUnlikesForAlbum(albumId: Long): Long =
   {
     try{
	  DB.withConnection { implicit connection =>
	    
         SQL("""
              select count(*) from TLIKE where fk_album = {albumId} and tipo = 'U' 
             
             """
             )
         .on('albumId -> albumId).as(scalar[Long].single)
      }
     }
     catch {
      case e: JdbcSQLException => throw DAOException.create(e.getMessage())
    }
   }
   
   
   override def getLikesForComment(commentId: Long): Long =
   {
     try{
	  DB.withConnection { implicit connection =>
	    
         SQL("""
               select count(*) from TLIKE  where fk_comentario = {commentId} and tipo = 'L'
             """
              
             )
         .on('commentId -> commentId).as(scalar[Long].single)
      }
     }catch {
      case e: JdbcSQLException => throw DAOException.create(e.getMessage())
    }
   }
	
   override def getUnlikesForComment(commentId: Long): Long =
   {
     try{
	  DB.withConnection { implicit connection =>
	    
         SQL("""
              select count(*) from TLIKE where fk_comentario = {commentId} and tipo = 'U' 
             
             """
             )
         .on('commentId -> commentId).as(scalar[Long].single)
      }
     }catch {
      case e: JdbcSQLException => throw DAOException.create(e.getMessage())
    }
   }
   
   
  override def iLikeAlbum(idAlbum: Long, idUsuario: Long): Long = {
    
       var like: Long = 0
       
       try
       {
       DB.withConnection { implicit connection =>

	   like = SQL("""
	                    select count(*) from tlike where tlike.fk_album = {idAlbum} 
	                    and tlike.fk_usuario = {idUsuario} and tipo = 'L'
                     """).on(
							'idUsuario -> idUsuario, 'idAlbum->idAlbum).as(scalar[Long].single)

		}
       
   
       if(like == 0)
       {
         return 0
       }
         
       return 1
       
       }catch {
      case e: JdbcSQLException => throw DAOException.create(e.getMessage())
    }
       
    
  }
  

  override def iDontLikeAlbum(idAlbum: Long, idUsuario: Long): Long = {
    
       var unlike: Long = 0
       
       try{
       DB.withConnection { implicit connection =>

	    unlike = SQL("""
	                    select count(*) from tlike where tlike.fk_album = {idAlbum} 
	                    and tlike.fk_usuario = {idUsuario} and tipo = 'U'
                     """).on(
							'idUsuario -> idUsuario, 'idAlbum->idAlbum).as(scalar[Long].single)

		}
       
    
       if( unlike == 0 )
       {
        
         return 0
       }
         
       return 1
       
       }catch {
      case e: JdbcSQLException => throw DAOException.create(e.getMessage())
    }
       
    
  }
  
  override def deleteAlbumLike(idUsuario: Long,idAlbum: Long) {
    
    try{
    DB.withConnection { implicit connection =>
		SQL("delete from tlike where fk_usuario = {idUsuario} and fk_album = {idAlbum} and tipo = 'L' "
		    ).on('idUsuario -> idUsuario,
				 'idAlbum -> idAlbum
				    
				).executeUpdate()

		}
    }catch {
      case e: JdbcSQLException => throw DAOException.create(e.getMessage())
    }
    
  }
  
  override def deleteAlbumUnlike(idUsuario: Long,idAlbum: Long) {
    
    try{
      
    DB.withConnection { implicit connection =>
		SQL("delete from tlike where fk_usuario = {idUsuario} and fk_album = {idAlbum} and tipo = 'U' "
		    ).on('idUsuario -> idUsuario,
				 'idAlbum -> idAlbum
				    
				).executeUpdate()

		}
    }catch {
      case e: JdbcSQLException => throw DAOException.create(e.getMessage())
    }
    
  }
  
  override def deleteCommentLike(idUsuario: Long,idComment: Long) {
    
    try{
    DB.withConnection { implicit connection =>
		SQL("delete from tlike where fk_usuario = {idUsuario} and fk_comentario = {idComment} and tipo = 'L' "
		    ).on('idUsuario -> idUsuario,
				 'idComment -> idComment
				    
				).executeUpdate()

		}
    }catch {
      case e: JdbcSQLException => throw DAOException.create(e.getMessage())
    }
    
  }
  
  override def deleteCommentUnlike(idUsuario: Long,idComment: Long) {
    
    try{
    DB.withConnection { implicit connection =>
		SQL("delete from tlike where fk_usuario = {idUsuario} and fk_comentario = {idComment} and tipo = 'U' "
		    ).on('idUsuario -> idUsuario,
				 'idComment -> idComment
				    
				).executeUpdate()

		}
    }catch {
      case e: JdbcSQLException => throw DAOException.create(e.getMessage())
    }
    
  }
   
  override def iLikeComment(idComment: Long, idUsuario: Long): Long = {
    
    try{
       var like: Long = 0
       
       DB.withConnection { implicit connection =>

	   like = SQL("""
	                    select count(*) from tlike where tlike.fk_comentario = {idComment} 
	                    and tlike.fk_usuario = {idUsuario} and tipo = 'L'
                     """).on(
							'idUsuario -> idUsuario, 'idComment->idComment).as(scalar[Long].single)

		}
       
   
       if(like == 0)
       {
         return 0
       }
         
       return 1
       
    }catch {
      case e: JdbcSQLException => throw DAOException.create(e.getMessage())
    }
    
  }
  

  override def iDontLikeComment(idComment: Long, idUsuario: Long): Long = {
    
    try{
       var unlike: Long = 0
       
       DB.withConnection { implicit connection =>

	    unlike = SQL("""
	                    select count(*) from tlike where tlike.fk_comentario = {idComment} 
	                    and tlike.fk_usuario = {idUsuario} and tipo = 'U'
                     """).on(
							'idUsuario -> idUsuario, 'idComment->idComment).as(scalar[Long].single)

		}
       
    
       if( unlike == 0 )
       {
        
         return 0
       }
         
       return 1
       
    } catch {
      case e: JdbcSQLException => throw DAOException.create(e.getMessage())
    }
       
    
  }
  
	
}