/* La clase album representa el album creado por un usuario en el cual se guardara *
 * el contenido multimedia                                                         */

package models

import java.util.{ Date }
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import models._

case class Album(private var id: Long,
		private var nombre: String,
		private var privacidad: Int,
		private var caratula: String,
		private var fechaCreacion: Date,
		private var descripcion: Option[String],
		private var ownerId: Long) {
  
    private var contenido: List[ContenidoMultimedia] = null;
    private var comentarios: List[Comentario] = null;
    private var likes: Long = 0;
    private var unlikes: Long = 0;
    private var isLiked: Long = 0;
    private var isUnLiked: Long = 0;

	def this() = this(0, "", 0, "", null, null, 0)
	def this(nombre: String, privacidad: Int, caratula: String, descripcion: Option[String], ownerId: Long) = this(0, nombre, privacidad, caratula, null, descripcion, ownerId)

	def getId() = this.id;
	def getNombre() = this.nombre;
	def getPrivacidad() = this.privacidad;
	def getCaratula() = this.caratula;
	def getFechaCreacion() = this.fechaCreacion;
	def getDescripcion() = this.descripcion;
	def getOwnerId() = this.ownerId;
	def getContenidoMultimedia() = this contenido
	def getComentarios() = this comentarios
	def getLikes() = this likes
	def getUnlikes() = this unlikes
	def getIsLiked() = this isLiked
    def getIsUnliked() = this isUnLiked

	def setId(id: Long) = this.id = id;
	def setNombre(nombre: String) = this.nombre = nombre;
	def setPrivacidad(privacidad: Int) = this.privacidad = privacidad;
	def setCaratula(caratula: String) = this.caratula = caratula;
	def setFechaCreacion(fechaCreacion: Date) = this.fechaCreacion = fechaCreacion;
	def setDescripcion(descripcion: Option[String]) = this.descripcion = descripcion;
	def setOwnerId(ownerId: Long) = this.ownerId = ownerId;
	def setContenidoMultimedia(contenido: List[ContenidoMultimedia]) = {this.contenido = contenido}
    def setComentarios(comentarios: List[Comentario]) = {this.comentarios = comentarios}
    def setLikes(likes: Long) = {this.likes = likes}
    def setUnlikes(unlikes: Long) = {this.unlikes = unlikes}
    def setIsLiked(isLiked: Long) = {this.isLiked = isLiked}
    def setIsUnliked(isUnLiked: Long) = {this.isUnLiked = isUnLiked}
}
