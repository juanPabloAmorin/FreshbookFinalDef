/* La clase comentario representa el comentario realizado por un usuario sobre el contenido *
 * multimedia propip o de otro usuario, y las respuestas a los comentarios                   */

package models

import java.util.{ Date }
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import models._

case class Comentario(private var id: Long,
		private var contenido: String,
		private var fecha: Date,
		private var fkContenido: Option[Long],
		private var fkAlbum: Option[Long],
		private var fkComentario: Option[Long],
		private var fkUsuario: Long) {
  
    private var owner :Usuario = null   
    private var respuestas: List[Comentario] = null;
    private var likes: Long = 0;
    private var unlikes: Long = 0;
    private var isLiked: Long = 0;
    private var isUnLiked: Long = 0;

	def this() = this(0,"",null,Some(0),Some(0),Some(0),0)

	def getId() = this.id;
	def getContenido() = this.contenido;
	def getFecha() = this.fecha;
	def getFkContenido() = this.fkContenido;
	def getFkAlbum() = this.fkAlbum;
	def getFkComentario() = this.fkComentario;
	def getFkUsuario() = this.fkUsuario;
	def getRespuestas() = this respuestas;
	def getLikes() = this likes
	def getUnlikes() = this unlikes
	def getOwner() = this owner
    def getIsLiked() = this isLiked
    def getIsUnliked = this isUnLiked


	def setId(id: Long) = this.id = id;
	def setContenido(contenido: String) = this.contenido = contenido;
	def setFecha(fecha: Date) = this.fecha = fecha;
	def setFkContenido(fkContenido: Option[Long]) = this.fkContenido = fkContenido;
	def setFkAlbum(fkAlbum: Option[Long]) = this.fkAlbum = fkAlbum;
	def setFkComentario(fkComentario: Option[Long]) = this.fkComentario = fkComentario;
    def setFkUsuario(fkUsuario: Long) = this.fkUsuario = fkUsuario;
	def setRespuestas(respuestas: List[Comentario]) = {this.respuestas = respuestas}
	def setLikes(likes: Long) = {this.likes = likes}
    def setUnlikes(unlikes: Long) = {this.unlikes = unlikes}
    def setOwner(owner: Usuario) = {this.owner = owner}
    def setIsLiked(isLiked: Long) = {this.isLiked = isLiked}
    def setIsUnLiked(isUnLiked: Long) = {this.isUnLiked = isUnLiked}





}
