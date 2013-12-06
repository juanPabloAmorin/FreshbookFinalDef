/* La clase like permite representar si a un usuario le gusta o no le gusta un contenido o 
 * comentario.
 */

package models

import java.util.{ Date }
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import models._

case class Like(private var id: Long,
		private var tipo: String,
		private var fecha: Date,
		private var fkContenido: Option[Long],
		private var fkAlbum: Option[Long],
		private var fkComentario: Option[Long],
		private var fkUsuario: Long) {
 

	def this() = this(0,"",null,Some(0),Some(0),Some(0),0)

	def getId() = this.id;
	def getTipo() = this.tipo;
	def getFecha() = this.fecha;
	def getFkContenido() = this.fkContenido;
	def getFkAlbum() = this.fkAlbum;
	def getFkComentario() = this.fkComentario;
	def getFkUsuario() = this.fkUsuario;

	def setId(id: Long) = this.id = id;
	def setTipo(tipo: String) = this.tipo = tipo;
	def setFecha(fecha: Date) = this.fecha = fecha;
	def setFkContenido(fkContenido: Option[Long]) = this.fkContenido = fkContenido;
	def setFkAlbum(fkAlbum: Option[Long]) = this.fkAlbum = fkAlbum;
	def setFkComentario(fkComentario: Option[Long]) = this.fkComentario = fkComentario;
    def setFkUsuario(fkUsuario: Long) = this.fkUsuario = fkUsuario;


}
