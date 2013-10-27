/* La clas notificacion representa las notificaciones enviadas a los usuarios luego   *
 * de que un amigo ejecute un evento (nuevo amigo, crear album, solicitud de amistad) *
 * en la aplicacion*/

package models

import java.util.{ Date }
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import models._

case class Notificacion(private var id: Long,
  private var contenido: String,
  private var fechaCreacion: Date,
  private var tipo: Long,
  private var usuarioGenerador: Long,
  private var idAmigo: Option[Long]) {
  
  def this() = this(0, "", null, 0,0, Some(0))
 

  def getId() = this.id;
  def getContenido() = this.contenido;
  def getFechaCreacion() = this.fechaCreacion;
  def getTipo() = this.tipo;
  def getIdAmigo() = this.idAmigo;
  def getIdUsuarioGenerador() = this.usuarioGenerador;
  
  def setId(id: Long) = this.id = id;
  def setContenido(contenido: String) = this.contenido = contenido;
  def setFechaCreacion(fechaCreacion: Date) = this.fechaCreacion = fechaCreacion;
  def setTipo(tipo: Long) = this.tipo = tipo;
  def setIdAmigo(idAmigo: Option[Long]) = this.idAmigo = idAmigo;
  def setIdUsuarioGenerador(idUsuario: Long) = this.usuarioGenerador = idUsuario;
 

}
