package models

import java.util.{ Date }
import play.api.db._
import play.api.Play.current
import anorm._
import anorm.SqlParser._
import scala.language.postfixOps
import models._

class UsuarioDAO_SQL() extends UsuarioDAO {

  var lastUserSequenceNumber: Long = 0;
  
  val parser = {
    get[Long]("usuario.id") ~
      get[String]("usuario.primer_nombre") ~
      get[Option[String]]("usuario.segundo_nombre") ~
      get[String]("usuario.primer_apellido") ~
      get[Option[String]]("usuario.segundo_apellido") ~
      get[String]("usuario.username") ~
      get[Date]("usuario.fecha_nacimiento") ~
      get[String]("usuario.email") ~
      get[Option[String]]("usuario.foto") ~
      get[Option[String]]("usuario.twitter") ~
      get[Option[String]]("usuario.facebook") ~
      get[Option[String]]("usuario.gmail") ~
      get[Date]("usuario.fecha_registro") ~
      get[Option[Date]]("usuario.fecha_baja") ~
      get[Date]("usuario.ultima_conexion") ~
      get[Int]("usuario.privacidad") map {
        case id ~ primerNombre ~ segundoNombre ~ primerApellido ~ segundoApellido ~ username ~ fechaNacimiento ~ email ~ foto ~ twitter ~ facebook ~ gmail ~ fechaRegistro ~ fechaBaja ~ ultimaConexion ~ privacidad => Usuario(id, primerNombre, segundoNombre, primerApellido, segundoApellido, username, fechaNacimiento, email, foto, twitter, facebook, gmail, fechaRegistro, fechaBaja, ultimaConexion, privacidad)
      }
  }

  override def findUserByEmail(email: String): Option[Usuario] = {
    DB.withConnection { implicit connection =>
      SQL("select * from USUARIO where email = {email} ").on('email -> email).as(this.parser.singleOpt)
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
          SELECT distinct usuario.id,usuario.primer_nombre,usuario.segundo_nombre,usuario.primer_apellido, usuario.segundo_apellido,usuario.username,usuario.fecha_nacimiento,
       usuario.email,usuario.foto,usuario.twitter,usuario.facebook,usuario.gmail,
       usuario.fecha_registro,usuario.fecha_baja,usuario.ultima_conexion,usuario.privacidad
       FROM usuario,amistad
       WHERE (amistad.fk_usuario1 = usuario.id or amistad.fk_usuario2 = usuario.id) 
          and (amistad.fk_usuario1 = {id} or amistad.fk_usuario2 = {id}) and usuario.id != {id}
       
        """).on(
          'id -> userId).as(this.parser *)

      amigos

    } 

  }
   
   override def insertUser(newUsuario: Usuario)
   {
       DB.withConnection { implicit connection =>
      SQL(
        """
          INSERT INTO USUARIO VALUES( nextval('seq_usuario'),{primer_nombre},
          {segundo_nombre},{primer_apellido},{segundo_apellido},{username},{fecha_nacimiento},
          {email},{foto},{twitter},{facebook},{gmail},NOW(),null,NOW(),{privacidad},{fk_lugar})
        """
      
      ).on(
          'primer_nombre->newUsuario.getPrimerNombre,
          'segundo_nombre->newUsuario.getSegundoNombre.getOrElse{"null"},
          'primer_apellido->newUsuario.getPrimerApellido,
          'segundo_apellido->newUsuario.getSegundoApellido.getOrElse{"null"},
          'username->newUsuario.getUsername,
          'fecha_nacimiento->newUsuario.getFechaNacimiento,
          'email->newUsuario.getEmail,
          'foto->newUsuario.getFoto,
          'twitter->newUsuario.getTwitter.getOrElse{"null"},
          'facebook->newUsuario.getFacebook.getOrElse{"null"},
          'gmail->newUsuario.getGmail.getOrElse{"null"},
          'privacidad->newUsuario.getPrivacidad,
          'fk_lugar->newUsuario.getUbicacion.getId
         
      ).executeUpdate() 
      
      lastUserSequenceNumber = SQL("select currval('seq_usuario')").as(scalar[Long].single)
    }
   }
   
   override def getNewUserLastIdFromSequence(): Long = {
    
      return lastUserSequenceNumber
  }
   
   override def searchUsersByFullNamePattern(namePattern: String): List[Usuario] = {
         
         DB.withConnection { implicit connection =>

      val users = SQL(
        """
          select * from usuario where  (lower(primer_nombre) like lower('"""+namePattern+"""%')) 
          or (lower(segundo_nombre) like lower('"""+namePattern+"""%')) 
          or (lower(primer_apellido) like lower('"""+namePattern+"""%')) 
          or (lower(segundo_apellido) like lower('"""+namePattern+"""%')) 
          or (lower(username) like lower('"""+namePattern+"""%')) 
          
        """).as(this.parser *)

      users

    } 
   }

}
