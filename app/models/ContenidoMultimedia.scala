/* La clase ContenidoMultimedia representa el contenido obtenido a traves de las redes sociales
 * y que sera asociado a un album  */

package models

import java.util.{ Date }
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import models._

case class ContenidoMultimedia(private var id: Long,
		private var ruta: String,
		private var fechaSubida: Date,
		private var redSocial: String,
		private var rutaTumbnail: String,
		private var inAlbumId: Long) {

	def this() = this(0, "",null,"","",0)
	def this(ruta: String, redSocial: String,rutaTumbnail: String, inAlbumId: Long) = this(0,ruta,null,redSocial,rutaTumbnail,inAlbumId)

	def getId() = this.id;
	def getRuta() = this.ruta;
	def getFechaSubida() = this.fechaSubida;
	def getRedSocial() = this.redSocial;
	def getRutaTumbnail() = this.rutaTumbnail;
	def getInAlbumId() = this.inAlbumId;
	
	def setId(id: Long) = this.id = id;
	def setRuta(ruta: String) = this.ruta = ruta;
	def setFechaSubida(fechaSubida: Date) = this.fechaSubida = fechaSubida;
	def setRedSocial(redSocial: String) = this.redSocial = redSocial;
	def setRutaTumbnail(rutaTumbnail: String) = this.rutaTumbnail = rutaTumbnail;
	def setInAlbumId(inAlbumId: Long) = this.inAlbumId = inAlbumId;

}
