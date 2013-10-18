package models

import java.util.{ Date }

import play.api._
import play.api.mvc._
import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._
import models._

class AlbumDAO_SQL extends AlbumDAO {

  val parser = {
    get[Long]("album.id") ~
      get[String]("album.nombre") ~
      get[Int]("album.privacidad") ~
      get[String]("album.caratula") ~
      get[Date]("album.fecha_creacion") ~
      get[Option[String]]("album.descripcion") ~
      get[Long]("album.fk_usuario") map {
        case id ~ nombre ~ privacidad ~ caratula ~ fechaCreacion ~ descripcion ~ ownerId => Album(id, nombre, privacidad, caratula, fechaCreacion, descripcion,ownerId)
      }
  }

  override def findAlbumsByUser(userId: Long): List[Album] = {

    DB.withConnection { implicit connection =>

      val albumes = SQL(
        """
          select * from album where album.fk_usuario = {userId}
        """).on(
          'userId -> userId).as(this.parser *)

      albumes

    }

  }

  override def getNumberOfFilesInAlbum(albumId: Long): Long = {

    DB.withConnection { implicit connection =>

      val totalArchivos = SQL(
        """
          SELECT COUNT(*) FROM ALBUM,CONTENIDO_MULTIMEDIA 
          WHERE contenido_multimedia.FK_ALBUM = album.ID AND album.ID = {albumId}
        """).on(
          'albumId -> albumId).as(scalar[Long].single)

      totalArchivos
    }
  }

  override def insertAlbum(newAlbum: Album) {

     DB.withConnection { implicit connection =>
      SQL(
        """
          INSERT INTO ALBUM VALUES( (select next value for seq_album),{nombre},{privacy},
          {imgRoute},'1986-02-10',{description},{ownerId})
        """
      
      ).on(
          'nombre->newAlbum.getNombre,
          'privacy->newAlbum.getPrivacidad,
          'imgRoute->newAlbum.getCaratula,
          'description->newAlbum.getDescripcion,
          'ownerId->newAlbum.getOwnerId
          
          
      ).executeUpdate() 
    }
  }
  
  override def deleteAlbumById(albumId: Long) {

    DB.withConnection { implicit connection =>
      SQL("delete from album where album.id = {albumId}"   
        ).on(
        'albumId -> albumId
  
      ).executeUpdate()
    }
  }
}