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
      get[Int]("usuario.privacidad") ~
      get[String]("usuario.latitud") ~ 
      get[String]("usuario.longitud") map  {
        case id ~ primerNombre ~ segundoNombre ~ primerApellido ~ segundoApellido ~ username ~ fechaNacimiento ~ email ~ foto ~ twitter ~ facebook ~ gmail ~ fechaRegistro ~ fechaBaja ~ ultimaConexion ~ privacidad ~ latitud ~ longitud => Usuario(id, primerNombre, segundoNombre, primerApellido, segundoApellido, username, fechaNacimiento, email, foto, twitter, facebook, gmail, fechaRegistro, fechaBaja, ultimaConexion, privacidad,latitud,longitud)
      }
  }

  override def findUserByEmail(email: String): Option[Usuario] = {
    DB.withConnection { implicit connection =>
      SQL("select * from USUARIO where email = {email} ").on('email -> email).as(this.parser.singleOpt)
    }

  }
  
   override def updateUserFirstName(firstName: String, userId: Long)
   {
      DB.withConnection { implicit connection =>
      SQL(
        """
          update usuario
          set primer_nombre = {primerNombre}
          where id = {id}
        """
      ).on(
        'id -> userId,
        'primerNombre -> firstName
      ).executeUpdate()
    }
   }
   
   override def updateUserSecondName(secondName: String, userId: Long)
   {
      DB.withConnection { implicit connection =>
      SQL(
        """
          update usuario
          set segundo_nombre = {segundoNombre}
          where id = {id}
        """
      ).on(
        'id -> userId,
        'segundoNombre -> secondName
      ).executeUpdate()
    }
   }
   
   override def updateUserFirstLastname(firstLastname: String, userId: Long)
   {
      DB.withConnection { implicit connection =>
      SQL(
        """
          update usuario
          set primer_apellido = {primerApellido}
          where id = {id}
        """
      ).on(
        'id -> userId,
        'primerApellido -> firstLastname
      ).executeUpdate()
    }
      
   }
   
   override def updateUserSecondLastname(secondLastname: String, userId: Long)
   {
      DB.withConnection { implicit connection =>
      SQL(
        """
          update usuario
          set segundo_apellido = {segundoApellido}
          where id = {id}
        """
      ).on(
        'id -> userId,
        'segundoApellido -> secondLastname
      ).executeUpdate()
    }
      
   }
   
   override def updateUserNickname(nickname: String, userId: Long)
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
   
   override def updateUserGoogle(google: String, userId: Long)
   {
      DB.withConnection { implicit connection =>
      SQL(
        """
          update usuario
          set gmail = {gmail}
          where id = {id}
        """
      ).on(
        'id -> userId,
        'gmail -> google
      ).executeUpdate()
    }
   }
   
   
   override def updateUserFacebook(facebook: String, userId: Long)
   {
      DB.withConnection { implicit connection =>
      SQL(
        """
          update usuario
          set facebook = {facebook}
          where id = {id}
        """
      ).on(
        'id -> userId,
        'facebook -> facebook
      ).executeUpdate()
    }
   }
   
   override def updateUserTwitter(twitter: String, userId: Long)
   {
      DB.withConnection { implicit connection =>
      SQL(
        """
          update usuario
          set twitter = {twitter}
          where id = {id}
        """
      ).on(
        'id -> userId,
        'twitter -> twitter
      ).executeUpdate()
    }
   }
   
   override def updateUserLocation(fkLugar: Long, userId: Long)
   {
      DB.withConnection { implicit connection =>
      SQL(
        """
          update usuario
          set fk_lugar = {fk_lugar}
          where id = {id}
        """
      ).on(
        'id -> userId,
        'fk_lugar -> fkLugar
      ).executeUpdate()
    }
   }
   
   override def updateUserLatitud(latitud: String, userId: Long)
   {
      DB.withConnection { implicit connection =>
      SQL(
        """
          update usuario
          set latitud = {latitud}
          where id = {id}
        """
      ).on(
        'id -> userId,
        'latitud -> latitud
      ).executeUpdate()
    }
   }
   
   override def updateUserLongitud(longitud: String, userId: Long)
   {
      DB.withConnection { implicit connection =>
      SQL(
        """
          update usuario
          set longitud = {longitud}
          where id = {id}
        """
      ).on(
        'id -> userId,
        'longitud -> longitud
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
       usuario.fecha_registro,usuario.fecha_baja,usuario.ultima_conexion,usuario.privacidad,usuario.latitud,usuario.longitud
       FROM usuario,amistad
       WHERE (amistad.fk_usuario1 = usuario.id or amistad.fk_usuario2 = usuario.id) 
          and (amistad.fk_usuario1 = {id} or amistad.fk_usuario2 = {id}) and usuario.id != {id}
          and amistad.status = 1
       
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
          {email},{foto},{twitter},{facebook},{gmail},NOW(),null,NOW(),{privacidad},{fk_lugar},{latitud},{longitud})
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
          'fk_lugar->newUsuario.getUbicacion.getId,
          'latitud->newUsuario.getLatitud,
          'longitud->newUsuario.getLongitud
         
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
   
    override def isThisUserAFriend(userId: Long, friendId: Long): Boolean ={
      
         var response: Boolean = false
          DB.withConnection { implicit connection =>

         val isFriend = SQL(
           """
           select count(*) from AMISTAD where (amistad.fk_usuario1 = {userId} and amistad.fk_usuario2 = {friendId}) 
           or (amistad.fk_usuario1 = {friendId} and amistad.fk_usuario2 ={userId})
           """).on(
          'userId -> userId,'friendId -> friendId).as(scalar[Long].single)

         if(isFriend != 0)  {
           response = true
         }
         else {
           response = false
         }
         
         response
       }
    }
    
    override def getUserFriendshipStatus(userId: Long, friendId: Long): Option[Int] ={
          DB.withConnection { implicit connection =>

         val friendshipStatus = SQL(
           """
           select amistad.status from AMISTAD where (amistad.fk_usuario1 = {userId} and amistad.fk_usuario2 = {friendId}) 
           or (amistad.fk_usuario1 = {friendId} and amistad.fk_usuario2 ={userId})
           """).on(
          'userId -> userId,'friendId -> friendId).as(scalar[Option[Int]].single)

         friendshipStatus
       }
    }
    
     override def insertAmistad(friend1Id: Long, friend2Id: Long) {

     DB.withConnection { implicit connection =>
      SQL(
        """
          INSERT INTO amistad VALUES({friend1Id},{friend2Id},0)
        """
      
      ).on(
          'friend1Id->friend1Id,
          'friend2Id->friend2Id
          
          
      ).executeUpdate() 
    }
  }
  
     
     override def confimFriendship(friend1Id: Long, friend2Id: Long){
       
       DB.withConnection { implicit connection =>
      SQL(
        """
          update amistad
          set status = 1
          where (amistad.fk_usuario1 = {friend1Id} and amistad.fk_usuario2 = {friend2Id}) 
           or (amistad.fk_usuario1 = {friend2Id} and amistad.fk_usuario2 ={friend1Id})
        """
      ).on(
        'friend1Id -> friend1Id,
        'friend2Id -> friend2Id
      ).executeUpdate()
    }
     
   }
     
     override def getUsersByFullNamePart(namePart: String): List[Usuario] = {
        
       DB.withConnection { implicit connection =>

      val users = SQL(
        """
          select * from usuario where  (lower(primer_nombre) = lower('"""+namePart+"""')) 
          or (lower(segundo_nombre) = lower('"""+namePart+"""')) 
          or (lower(primer_apellido) = lower('"""+namePart+"""')) 
          or (lower(segundo_apellido) = lower('"""+namePart+"""')) 
          or (lower(username) = lower('"""+namePart+"""')) 
          
        """).as(this.parser *)

      users

    } 
       
       
   }


}
