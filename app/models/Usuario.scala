package models

import java.util.{Date}
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import models._


case class Usuario(var id: Long, 
                   var nombres: String, 
                   var apellidos: String, 
                   var username: String,
                   var pass: String,  
                   var fecha_nacimiento: Date, 
                   var email: String, 
                   var foto: Option[String],
                   var twitter: Option[String], 
                   var facebook: Option[String], 
                   var gmail: Option[String],
                   var fecha_registro: Date,
                   var fecha_baja: Option[Date],
                   var ultima_conexion: Date)
{
      private var amistades: List[Usuario] = null;
      
      def this() = this(0,"","","","",null,"",null,null,null,null,null,null,null)
     
        

  def getNombres() = this.nombres
  def getApellidos() = this.apellidos
  def getId() = this.id
  def getUsername() = this.username
  def getFechaNacimiento() = this.fecha_nacimiento;
  def getEmail() = this.email
  def getFoto() = this.foto
  def getTwitter() = this.twitter
  def getFacebook() = this.facebook
  def getGmail() = this.gmail
  def getFechaRegistro() = this.fecha_registro
  def getFechaBaja() = this.fecha_baja
  def getUltimaConexion() = this.ultima_conexion
  def getAmistades() = this amistades
  
  def setNombres(nombres: String) = {this.nombres = nombres}
  def setApellidos(apellidos: String) = {this.apellidos = apellidos}
  def setUsername(username: String) = {this.username = username}
  def setFechaNacimiento(fecha_nacimiento: Date) = {this.fecha_nacimiento = fecha_nacimiento}
  def setEmail(email: String) = {this.email = email}
  def setFoto(foto: Option[String]) = {this.foto = foto}
  def setTwitter(twitter: Option[String]) = {this.twitter = twitter}
  def setFacebook(facebook: Option[String]) ={ this.facebook = facebook}
  def setGmail(gmail: Option[String]) = {this.gmail = gmail}
  def setFechaRegistro(fecha_registro: Date) = {this.fecha_registro = fecha_registro}
  def setFechaBaja(fecha_baja : Option[Date]) = {this.fecha_baja = fecha_baja}
  def setUltimaConexion(ultima_conexion: Date) = {this.ultima_conexion = ultima_conexion}
  def setAmistades(amistades: List[Usuario]) = {this.amistades = amistades}
  
}
