package models

import play.api._
import play.api.mvc._
import models._

object DAOFabrica {

  def getUsuarioDAO(): UsuarioDAO = {

    new UsuarioDAO_SQL
  }

  def getAlbumDAO(): AlbumDAO = {

    new AlbumDAO_SQL
  }

  def getLugarDAO(): LugarDAO = {

    new LugarDAO_SQL
  }

  def getNotificacionDAO(): NotificacionDAO = {

    new NotificacionDAO_SQL
  }

  def getContenidoMultimediaDAO(): ContenidoMultimediaDAO = {

    new ContenidoMultimediaDAO_SQL
  }

  def getComentarioDAO(): ComentarioDAO = {

    new ComentarioDAO_SQL
  }

  def getLikeDAO(): LikeDAO = {

    new LikeDAO_SQL
  }
}
