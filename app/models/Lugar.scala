package models

import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import models._

case class Lugar(private var id: Long,
  private var nombre: String,
  private var tipo: String) {
  
  private var lugar: Option[Lugar] = null
  
  def this(nombre: String,tipo: String,lugar: Option[Lugar]) = {this(0,nombre,tipo)
                                                        this.setLugar(lugar)}
 
  
  def getId() = this.id;
  def getNombre() = this.nombre;
  def getTipo() = this.tipo;
  def getLugar(): Option[Lugar] = this.lugar;
  
  def setId(id: Long) = this.id = id;
  def setNombre(nombre: String) = this.nombre = nombre;
  def setTipo(privacidad: Int) = this.tipo = tipo;
  def setLugar(lugar: Option[Lugar]) = this.lugar = lugar;
  

}
