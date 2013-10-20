package models

import java.util.{Date}
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import models._


case class Usuario(private var id: Long, 
                   private var primerNombre: String, 
                   private var segundoNombre: Option[String],
                   private var primerApellido: String, 
                   private var segundoApellido: Option[String], 
                   private var username: String,  
                   private var fechaNacimiento: Date, 
                   private var email: String, 
                   private var foto: Option[String],
                   private var twitter: Option[String], 
                   private var facebook: Option[String], 
                   private var gmail: Option[String],
                   private var fechaRegistro: Date,
                   private var fechaBaja: Option[Date],
                   private var ultimaConexion: Date,
                   private var privacidad: Int)
{
      private var amistades: List[Usuario] = null;
      
      def this() = this(0,"",Some(""),"",Some(""),"",null,"",Some(""),Some(""),Some(""),Some(""),null,null,null,0)
     
        

  def getPrimerNombre() = this.primerNombre
  def getSegundoNombre() =  this.segundoNombre
  def getPrimerApellido() = this.primerApellido
  def getSegundoApellido() = this.segundoApellido
  def getId() = this.id
  def getUsername() = this.username
  def getFechaNacimiento() = this.fechaNacimiento;
  def getEmail() = this.email
  def getFoto() = this.foto
  def getTwitter() = this.twitter
  def getFacebook() = this.facebook
  def getGmail() = this.gmail
  def getFechaRegistro() = this.fechaRegistro
  def getFechaBaja() = this.fechaBaja
  def getUltimaConexion() = this.ultimaConexion
  def getAmistades() = this amistades
  def getPrivacidad() = this privacidad
  
  def setPrimerNombre(primerNombre: String) = {this.primerNombre = primerNombre}
  def setSegundoNombre(segundoNombre: Option[String]) = {this.segundoNombre = segundoNombre}
  def setPrimerApellido(primerApellido: String) = {this.primerApellido = primerApellido}
  def setSegundoApellido(segundoApellido: Option[String]) = {this.segundoApellido = segundoApellido}
  def setUsername(username: String) = {this.username = username}
  def setFechaNacimiento(fecha_nacimiento: Date) = {this.fechaNacimiento = fecha_nacimiento}
  def setEmail(email: String) = {this.email = email}
  def setFoto(foto: Option[String]) = {this.foto = foto}
  def setTwitter(twitter: Option[String]) = {this.twitter = twitter}
  def setFacebook(facebook: Option[String]) ={ this.facebook = facebook}
  def setGmail(gmail: Option[String]) = {this.gmail = gmail}
  def setFechaRegistro(fecha_registro: Date) = {this.fechaRegistro = fecha_registro}
  def setFechaBaja(fecha_baja : Option[Date]) = {this.fechaBaja = fecha_baja}
  def setUltimaConexion(ultima_conexion: Date) = {this.ultimaConexion = ultima_conexion}
  def setAmistades(amistades: List[Usuario]) = {this.amistades = amistades}
  def setPrivacidad(privacidad: Int) = {this.privacidad = privacidad}
  
}
