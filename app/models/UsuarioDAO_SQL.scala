package models

import java.util.{ Date }
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import scala.language.postfixOps
import models._

class UsuarioDAO_SQL() extends UsuarioDAO {

  val parser = {
    get[Long]("usuario.id") ~
      get[String]("usuario.nombres") ~
      get[String]("usuario.apellidos") ~
      get[String]("usuario.username") ~
      get[String]("usuario.pass") ~
      get[Date]("usuario.fecha_nacimiento") ~
      get[String]("usuario.email") ~
      get[Option[String]]("usuario.foto") ~
      get[Option[String]]("usuario.twitter") ~
      get[Option[String]]("usuario.facebook") ~
      get[Option[String]]("usuario.gmail") ~
      get[Date]("usuario.fecha_registro") ~
      get[Option[Date]]("usuario.fecha_baja") ~
      get[Date]("usuario.ultima_conexion") map {
        case id ~ nombres ~ apellidos ~ username ~ pass ~ fecha_nacimiento ~ email ~ foto ~ twitter ~ facebook ~ gmail ~ fecha_registro ~ fecha_baja ~ ultima_conexion  => Usuario(id, nombres, apellidos, username, pass, fecha_nacimiento, email, foto, twitter, facebook, gmail, fecha_registro, fecha_baja, ultima_conexion)
      }
  }

  override def findByLog(username: String, pass: String): Option[Usuario] = {
    DB.withConnection { implicit connection =>
      SQL("select * from USUARIO where username = {username} and pass = {pass}").on('username -> username, 'pass -> pass).as(this.parser.singleOpt)
    }

  }
  
   def updateUserNames(names: String, userId: Long)
   {
      DB.withConnection { implicit connection =>
      SQL(
        """
          update usuario
          set nombres = {nombres}
          where id = {id}
        """
      ).on(
        'id -> userId,
        'nombres -> names
      ).executeUpdate()
    }
   }
   
   def updateUserLastnames(lastnames: String, userId: Long)
   {
      DB.withConnection { implicit connection =>
      SQL(
        """
          update usuario
          set apellidos = {apellidos}
          where id = {id}
        """
      ).on(
        'id -> userId,
        'apellidos -> lastnames
      ).executeUpdate()
    }
      
   }
   def updateUserNickname(nickname: String, userId: Long)
   {
      DB.withConnection { implicit connection =>
      SQL(
        """
          update usuario
          set username = {username}
          where id = {id}
        """
      ).on(
        'id -> userId,
        'username -> nickname
      ).executeUpdate()
    }
   }
   def updateUserEmail(email: String, userId: Long)
   {
        DB.withConnection { implicit connection =>
      SQL(
        """
          update usuario
          set email = {email}
          where id = {id}
        """
      ).on(
        'id -> userId,
        'email -> email
      ).executeUpdate()
    }
   }
   
   override def findUserById(id: Long): Option[Usuario] = {
    DB.withConnection { implicit connection =>
      SQL("select * from USUARIO where id = {id}").on('id -> id).as(this.parser.singleOpt)
    }

  }
   
   
   override def findFriendsByUser(userId: Long): List[Usuario] = {

    DB.withConnection { implicit connection =>

      val amigos = SQL(
        """
          SELECT distinct usuario.id,usuario.nombres,usuario.apellidos,usuario.username,usuario.pass,usuario.fecha_nacimiento,
       usuario.email,usuario.foto,usuario.twitter,usuario.facebook,usuario.gmail,
       usuario.fecha_registro,usuario.fecha_baja,usuario.ultima_conexion
       FROM usuario,amistad
       WHERE (amistad.fk_usuario1 = usuario.id or amistad.fk_usuario2 = usuario.id) and usuario.id != {id}
       
        """).on(
          'id -> userId).as(this.parser *)

      amigos

    } 

  }  

}
