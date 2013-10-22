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
}
