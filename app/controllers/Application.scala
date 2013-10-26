package controllers

import play.api._
import play.api.mvc._

import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import routes.javascript._

import views._
import models._
import controllers._

import anorm._
import anorm.SqlParser._

import java.util.{ Date }

import play.api.libs.json._

import akka.actor.Actor
import akka.actor.Props

object Application extends Controller {

  var currentUser: Usuario = null;
  var lastFriendVistedId: Long = 0;
  var newUser: Usuario = null;  

  def index = Action {
    
    Ok(views.html.index()).withNewSession
  }

  def userAutentication(userEmail: String, link: String, firstName: String, middleName: String, lastName: String) = Action {

    newUser = new Usuario(firstName, middleName, lastName, null, userEmail, link, "");

    var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO;
    currentUser = userDao.findUserByEmail(userEmail).getOrElse { null }

    userDao.findUserByEmail(userEmail).isEmpty match {
      case true => Redirect("/registroPaso1Pag")
      case false =>
        lastFriendVistedId = currentUser.getId
        Redirect("/principal").withSession("usuarioEmail" -> currentUser.getEmail)
    }

  }

  def showRegistroPaso1Pag = Action {

    Ok(html.registroPaso1(newUser))
  }

  def registroPaso2(firstName: String, secondName: String, firstLastName: String, secondLastName: String, username: String, dia: String, mes: String, anio: String, privacy: Int, photo: String) = Action {

    newUser.setPrimerNombre(firstName)
    if (secondName != "null") {
      newUser.setSegundoNombre(Some(secondName))
    }
    else
    {
       newUser.setSegundoNombre(Some(""))
    }
    newUser.setPrimerApellido(firstLastName)
    if (secondLastName != "null") {
      newUser.setSegundoApellido(Some(secondLastName))
    }
    newUser.setUsername(username)
    newUser.setPrivacidad(privacy)
    newUser.setFoto(Some(photo))

    var stringBirthDate = dia + "/" + mes + "/" + anio
    var birthDay: Date = new Date(stringBirthDate)

    newUser.setFechaNacimiento(birthDay)

    Redirect("/registroPaso2Pag")
  }

  def showRegistroPaso2Pag = Action {

    Ok(html.registroPaso2())
  }

  def addingNewUser(country: String, state: String, city: String, latitud: String, longitud: String) = Action {

    var userLocation = Util.getUserLocation(country, state, city)
    newUser.setUbicacion(userLocation)
    newUser.setLatitud(latitud)
    newUser.setLongitud(longitud)
    
    var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO
    userDao.insertUser(newUser)
    newUser.setId(userDao.getNewUserLastIdFromSequence)
    currentUser = newUser
    lastFriendVistedId = currentUser.getId
    Redirect("/principal").withSession("usuarioEmail" -> currentUser.getEmail)

  }

  def showPrincipalPage = Action {
    request =>
      request.session.get("usuarioEmail").map { userEmail =>
        Ok(html.principal(currentUser))
      }.getOrElse {
        Ok(views.html.index()).withNewSession
      }
  }

  def showPerfil(userId: Long) = Action { request => 
    
    request.session.get("usuarioEmail").map { userEmail =>
    lastFriendVistedId = userId;
    Redirect("/perfilPag")
    }.getOrElse {
        Ok(views.html.index()).withNewSession
    }
  }

  def showPerfilPag = Action { request => 
    
    request.session.get("usuarioEmail").map { userEmail =>
    var friendshipStatus = -1
    var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO;
    var userSelected = userDao.findUserById(lastFriendVistedId).getOrElse { null }
    var isFriendUserSelected = userDao.isThisUserAFriend(currentUser.getId, lastFriendVistedId)
    if(isFriendUserSelected)
    {
        var status = userDao.getUserFriendshipStatus(currentUser.getId, lastFriendVistedId)
        status match{
          case Some(value) => friendshipStatus = value
          
        }
         
    }
     
        Ok(views.html.perfil(userSelected, currentUser,isFriendUserSelected,friendshipStatus))
    }.getOrElse {
        Ok(views.html.index()).withNewSession
    }
    
  }

  def showAlbumes(userId: Long) = Action { request => 
    
    request.session.get("usuarioEmail").map { userEmail =>
    lastFriendVistedId = userId;
    Redirect("/albumesPag")
    }.getOrElse {
        Ok(views.html.index()).withNewSession
    }
  }

  def showAlbumesPag = Action {
    request => 
    
    request.session.get("usuarioEmail").map { userEmail =>
    var albumDao: AlbumDAO = DAOFabrica.getAlbumDAO;
    var albumes = albumDao.findAlbumsByUser(lastFriendVistedId)
    var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO;
    var userSelected = userDao.findUserById(lastFriendVistedId).getOrElse { null }
    Ok(views.html.albumes(albumes, userSelected, albumDao, currentUser))
    }.getOrElse {
        Ok(views.html.index()).withNewSession
    }
  }

  def showAmigos(userId: Long) = Action {
    request => 
    
    request.session.get("usuarioEmail").map { userEmail =>
    lastFriendVistedId = userId;
    Redirect("/amigosPag")
    }.getOrElse {
        Ok(views.html.index()).withNewSession
    }
  }

  def showAmigosPag = Action { request => 
    
    request.session.get("usuarioEmail").map { userEmail =>
    var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO;
    var userSelected = userDao.findUserById(lastFriendVistedId).getOrElse { null }
    userSelected.setAmistades(userDao.findFriendsByUser(userSelected.getId))
    Ok(views.html.amigos(userSelected, currentUser))
    }.getOrElse {
        Ok(views.html.index()).withNewSession
    }
  }

  def showAlbumContent(albumId: Long) = Action {request => 
    
    request.session.get("usuarioEmail").map { userEmail =>
    Redirect("/albumContentPag")
    }.getOrElse {
        Ok(views.html.index()).withNewSession
    }
  }

  def showAlbumContentPag = Action {
    request => 
    
    request.session.get("usuarioEmail").map { userEmail =>
    Ok(views.html.carrusel())
    }.getOrElse {
        Ok(views.html.index()).withNewSession
    }
  }

  def updateRegisteredUser(option: Int, attribute: String, userId: Long) = Action {
    request => 
    
    request.session.get("usuarioEmail").map { userEmail =>
    var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO;

    if (option == 1) {
      userDao.updateUserFirstName(attribute, userId)
      currentUser.setPrimerNombre(attribute)
    }
    else if (option == 5) {
      userDao.updateUserSecondName(attribute, userId)
      currentUser.setSegundoNombre(Some(attribute))
    }
    else if (option == 2) {
      userDao.updateUserFirstLastname(attribute, userId)
      currentUser.setPrimerApellido(attribute)
    }
    else if (option == 6) {
      userDao.updateUserSecondLastname(attribute, userId)
      currentUser.setSegundoApellido(Some(attribute))
    }
    else if (option == 3) {
      userDao.updateUserNickname(attribute, userId)
      currentUser.setUsername(attribute)
    }
    
    else if (option == 7) {
      userDao.updateUserTwitter(attribute, userId)
      currentUser.setTwitter(Some(attribute))
    }
    
    else if (option == 8) {
      userDao.updateUserFacebook(attribute, userId)
      currentUser.setFacebook(Some(attribute))
    }
    
    else if (option == 9) {
      userDao.updateUserGoogle(attribute, userId)
      currentUser.setGmail(Some(attribute))
    }
    
    Ok("true")
    }.getOrElse {
        Ok(views.html.index()).withNewSession
    }
  }

  def createAlbum() = Action {request => 
    
    request.session.get("usuarioEmail").map { userEmail =>
    Ok(views.html.crearAlbum(currentUser))
    }.getOrElse {
        Ok(views.html.index()).withNewSession
    }
  }

  def deleteAlbum(albumId: Long) = Action {request => 
    
    request.session.get("usuarioEmail").map { userEmail =>
    var albumDao: AlbumDAO = DAOFabrica.getAlbumDAO
    albumDao.deleteAlbumById(albumId)
    Redirect("/albumesPag")
    }.getOrElse {
        Ok(views.html.index()).withNewSession
    }
  }

  def insertAlbum(albumName: String, albumDescription: String, albumPrivacy: Int, albumImgRoute: String) = Action {

    var album: Album = new Album(albumName, albumPrivacy, "assets/images/" + albumImgRoute, Some(albumDescription), currentUser.getId)
    var albumDao: AlbumDAO = DAOFabrica.getAlbumDAO
    albumDao.insertAlbum(album)

    Ok("true");
  }

  def searchForFriends(nameUserPattern: String) = Action {

    var json: JsValue = null;
    var jsonString = ""
    var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO;
    var users: List[Usuario] = userDao.searchUsersByFullNamePattern(nameUserPattern)
    var usersFoundNumber = 0;

    if(users.length > 0)
    {
      jsonString = """ {"users": [ """
      for(usuario<-users)
      {
        usersFoundNumber += 1
        jsonString = jsonString + """
        {
               "primerNombre" : """"+usuario.getPrimerNombre+"""",
               "segundoNombre" : """"+(usuario.getSegundoNombre match{ case Some(value) => value case _ => ""})+"""",
               "primerApellido" : """"+usuario.getPrimerApellido+"""",
               "segundoApellido" : """"+(usuario.getSegundoApellido match{ case Some(value) => value case _ => ""})+"""",
               "username" : """"+usuario.getUsername+"""",
               "id" : """"+usuario.getId+""""
     
        }
        """
               
        if(usersFoundNumber != users.length)
        {
            jsonString = jsonString + ","
        }
        
      }
      
      jsonString = jsonString + " ]}"
    }
    
    json = Json.parse(jsonString)
    

    Ok(json).as("application/json")
  }
  
  def locationModify() = Action{
    request => 
    
    request.session.get("usuarioEmail").map { userEmail =>
    
       Ok(views.html.modificacionUbicacion(currentUser))
    }.getOrElse {
        Ok(views.html.index()).withNewSession
    }
  }
  
  def updateUserLocation(country: String,state: String,city: String,latitud: String, longitud: String) = Action{
    
       var userLocation = Util.getUserLocation(country, state, city)
       
       var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO
       userDao.updateUserLatitud(latitud,currentUser.getId)
       userDao.updateUserLongitud(longitud,currentUser.getId)
       userDao.updateUserLocation(userLocation.getId, currentUser.getId)
       
       currentUser.setUbicacion(userLocation)
       currentUser.setLatitud(latitud)
       currentUser.setLongitud(longitud)
       
       Redirect("/perfilPag")
  }
  
  def searchForFriendsPage() = Action{
    
       Ok(views.html.searchForFriends(currentUser))
  }
  
  
  def createUserFriendRequestNotification = Action {
    
       var notificacionDao = DAOFabrica.getNotificacionDAO
       var userDao = DAOFabrica.getUsuarioDAO
       var newNotification: Notificacion = new Notificacion()
       
       userDao.insertAmistad(currentUser.getId, lastFriendVistedId)
       
       newNotification.setContenido(currentUser.getPrimerNombre + " " + currentUser.getPrimerApellido + " quiere ser tu amigo en Feeshbook")
       newNotification.setTipo(notificacionDao.getTypeNotificationNumberFormat("SOLICITUD"))
       newNotification.setIdAmigo(Some(currentUser.getId))
       newNotification.setIdUsuarioGenerador(currentUser.getId)

       notificacionDao.insertNotification(newNotification,lastFriendVistedId)
       
       
       Ok("true")
       
  }
  
  def getSolicitudesAmistad(userId: Long) = Action {

    var json: JsValue = null;
    var jsonString = ""
    var notificacionDao: NotificacionDAO = DAOFabrica.getNotificacionDAO
    var notifications: List[Notificacion] = notificacionDao.getNotificationsByUserAndType(currentUser.getId,"SOLICITUD")
    var notificationsFoundNumber = 0;
    var userId: Option[Long] = null;
    
   if(notifications.length > 0)
    {
      jsonString = """ {"notification": [ """
      for(notification<-notifications)
      {
        var amigoId: Long = 0
        notificationsFoundNumber += 1
        userId = notification.getIdAmigo
        
        userId match{
          case Some(id) => amigoId = id
        }
        
        var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO
        var user: Option[Usuario] = userDao.findUserById(amigoId)
        var notificationUser = new Usuario()
        
        user match{
          case Some(person) => notificationUser = person
        }
        jsonString = jsonString + """
        {
               "primerNombre" : """"+notificationUser.getPrimerNombre+"""",
               "segundoNombre" : """"+(notificationUser.getSegundoNombre match{ case Some(value) => value case _ => ""})+"""",
               "primerApellido" : """"+notificationUser.getPrimerApellido+"""",
               "segundoApellido" : """"+(notificationUser.getSegundoApellido match{ case Some(value) => value case _ => ""})+"""",
               "id" : """"+notificationUser.getId+"""",
               "notificationid" : """"+notification.getId+""""
     
        }
        """
               
        if(notificationsFoundNumber != notifications.length)
        {
            jsonString = jsonString + ","
        }
        
      }
      
      jsonString = jsonString + " ]}"
    }
    
    json = Json.parse(jsonString)
    

    Ok(json).as("application/json")
    
    
  }
  
  def confirmFriendship(friendId: Long, notificationId: Long) = Action
  {
    var userDao: UsuarioDAO= DAOFabrica.getUsuarioDAO
    userDao.confimFriendship(currentUser.getId, friendId)
    
    var notificacionDao: NotificacionDAO = DAOFabrica.getNotificacionDAO
    notificacionDao.deleteFriendshipRequestNotifications(currentUser.getId,notificationId)
   
    var newNotification: Notificacion = new Notificacion()
    newNotification.setContenido("ahora es amigo de")
    newNotification.setTipo(notificacionDao.getTypeNotificationNumberFormat("AMISTAD"))
    newNotification.setIdAmigo(Some(friendId))
     newNotification.setIdUsuarioGenerador(currentUser.getId)
    notificacionDao.insertNotification(newNotification,currentUser.getId)
    
    currentUser.setAmistades(userDao.findFriendsByUser(currentUser.getId))
 
    val newNotificationId = notificacionDao.getNewNotificationLastIdFromSequence
    notificacionDao.insertNotificationMulticast(currentUser.getAmistades, newNotificationId)
    
    //falta notificar amigos de mi nuevo amigo qu no sean mis amigos
    Ok("true")
    
  }
  

}