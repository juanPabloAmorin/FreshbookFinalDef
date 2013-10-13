package models

import java.util.{ Date }
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import models._

case class Album(id: Long,
  nombre: String,
  privacidad: Short,
  caratula: String,
  fecha_creacion: Date,
  descripcion: Option[String]) {
  
  def this() = this(0, "", 0, "", null, null)


  def getId() = this.id;
  def getNombre() = this.nombre;
  def getPrivacidad() = this.privacidad;
  def getCaratula() = this.caratula;
  def getFechaCreacion() = this.fecha_creacion;
  def getDescripcion() = this.descripcion;


}
