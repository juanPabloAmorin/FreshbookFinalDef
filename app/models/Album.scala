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

	def this() = this(0, "", 0, "", null, null, 0)
	def this(nombre: String, privacidad: Int, caratula: String, descripcion: Option[String], ownerId: Long) = this(0, nombre, privacidad, caratula, null, descripcion, ownerId)

	def getId() = this.id;
	def getNombre() = this.nombre;
	def getPrivacidad() = this.privacidad;
	def getCaratula() = this.caratula;
	def getFechaCreacion() = this.fechaCreacion;
	def getDescripcion() = this.descripcion;
	def getOwnerId() = this.ownerId;

	def setId(id: Long) = this.id = id;
	def setNombre(nombre: String) = this.nombre = nombre;
	def setPrivacidad(privacidad: Int) = this.privacidad = privacidad;
	def setCaratula(caratula: String) = this.caratula = caratula;
	def setFechaCreacion(fechaCreacion: Date) = this.fechaCreacion = fechaCreacion;
	def setDescripcion(descripcion: Option[String]) = this.descripcion = descripcion;
	def setOwnerId(ownerId: Long) = this.ownerId = ownerId;

}
