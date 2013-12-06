package models

import java.util.{Date}

import play.api._
import play.api.mvc._
import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._
import models._


trait ContenidoMultimediaDAO
{
 
  /*El metodo insertContenidoMultimedia permite insertar contenido multimedia asociado a un 
   * album */
   def insertContenidoMultimedia(newContenidoMultimedia: ContenidoMultimedia)
   
   /*Metodo getParser devuelve el parser para contenido multimedia obtenido de la base de datos*/
   
   def getParser(): RowParser[ContenidoMultimedia]
}