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

  var newUser: Usuario = null;

  def index = Action {

    Ok(views.html.index()).withNewSession
  }

  
  
  def userAutentication(userEmail: String, link: String, 
      firstName: String, middleName: String, lastName: String,facebookId: String) = Action {

    newUser = new Usuario(firstName, middleName, lastName, null, userEmail, link, "",facebookId);

    var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO;
    var currentUser = userDao.findUserByEmail(userEmail).getOrElse { null }

    userDao.findUserByEmail(userEmail).isEmpty match {
      case true => Redirect("/registroPaso1Pag")
      case false =>
        Redirect("/principal").withSession("usuarioEmail" -> currentUser.getEmail, 
                                           "userId" -> currentUser.getId.toString,
                                           "userFirstName" -> currentUser.getPrimerNombre,
                                           "userSecondName" -> (currentUser.getSegundoNombre match {case Some(value) => value case _ => ""}),
                                           "userFirstLastname" -> currentUser.getPrimerApellido,
                                           "userSecondLastname" -> (currentUser.getSegundoApellido match {case Some(value) => value case _ => ""}),
                                           "username" -> currentUser.getUsername,
                                           "birthday" -> currentUser.getFechaNacimiento.toString(),
                                           "picture" -> (currentUser.getFoto match {case Some(value) => value case _ => ""}),
                                           "twitter" -> (currentUser.getTwitter match {case Some(value) => value case _ => ""}),
                                           "facebook" -> (currentUser.getFacebook match {case Some(value) => value case _ => ""}),
                                           "gmail" -> (currentUser.getGmail match {case Some(value) => value case _ => ""}),
                                           "privacidad" -> currentUser.getPrivacidad.toString,
                                           "latitud" -> currentUser.getLatitud,
                                           "longitud" -> currentUser.getLongitud,
                                           "FBId" -> currentUser.getFacebookId,
                                           "lastFriendVisitedId" -> "0")
    }
    

  }

  
  
  def showRegistroPaso1Pag = Action {

    Ok(html.registroPaso1(newUser))
  }

  
  
  def registroPaso2(firstName: String, secondName: String, firstLastName: String, 
                    secondLastName: String, username: String, dia: String, mes: String, 
                    anio: String, privacy: Int, photo: String,id: String) = Action {

    newUser.setPrimerNombre(firstName)
    
    if (secondName != "null") {
      newUser.setSegundoNombre(Some(secondName))
    } 
    else {
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

 
  
  def addingNewUser(country: String, state: String, city: String, latitud: String, 
                    longitud: String) = Action {

    var userLocation = Util.getUserLocation(country, state, city)
    newUser.setUbicacion(userLocation)
    newUser.setLatitud(latitud)
    newUser.setLongitud(longitud)

    var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO
    userDao.insertUser(newUser)
    newUser.setId(userDao.getNewUserLastIdFromSequence)
    var currentUser = newUser
    
    Redirect("/principal").withSession("usuarioEmail" -> currentUser.getEmail, 
                                           "userId" -> currentUser.getId.toString,
                                           "userFirstName" -> currentUser.getPrimerNombre,
                                           "userSecondName" -> (currentUser.getSegundoNombre match {case Some(value) => value case _ => ""}),
                                           "userFirstLastname" -> currentUser.getPrimerApellido,
                                           "userSecondLastname" -> (currentUser.getSegundoApellido match {case Some(value) => value case _ => ""}),
                                           "username" -> currentUser.getUsername,
                                           "birthday" -> currentUser.getFechaNacimiento.toString(),
                                           "picture" -> (currentUser.getFoto match {case Some(value) => value case _ => ""}),
                                           "twitter" -> (currentUser.getTwitter match {case Some(value) => value case _ => ""}),
                                           "facebook" -> (currentUser.getFacebook match {case Some(value) => value case _ => ""}),
                                           "gmail" -> (currentUser.getGmail match {case Some(value) => value case _ => ""}),
                                           "privacidad" -> currentUser.getPrivacidad.toString,
                                           "latitud" -> currentUser.getLatitud,
                                           "longitud" -> currentUser.getLongitud,
                                           "FBId" -> currentUser.getFacebookId,
                                           "lastFriendVisitedId" -> "0")

  }

  
  
  def showPrincipalPage = Action { request =>

    request.session.get("usuarioEmail").map { userEmail =>
        
        var user: Usuario = new Usuario();
        user = currentUserBuild(request,user)
        var notificacionDao: NotificacionDAO = DAOFabrica.getNotificacionDAO
        var notificationsList: List[Notificacion] = notificacionDao.getNotificationsByUser(user.getId)
        Ok(html.principal(user,notificationsList))
      }.getOrElse {
        Ok(views.html.index()).withNewSession
      } 
  }

  
  
  def showPerfil(userId: Long) = Action { request =>

    request.session.get("usuarioEmail").map { userEmail =>
      Redirect("/perfilPag").withSession(request.session - "lastFriendVisitedId")
                            .withSession(request.session + ("lastFriendVisitedId" -> userId.toString))
    }.getOrElse {
      Ok(views.html.index()).withNewSession
    }
  }

  
  
  def showPerfilPag = Action { request =>

    request.session.get("usuarioEmail").map { userEmail =>
      
      var lastFriendVisitedId: Long = 0
      request.session.get("lastFriendVisitedId") match{
        case Some(value) =>
             lastFriendVisitedId = value.toLong;
      }
      
      var user: Usuario = new Usuario();
      user = currentUserBuild(request,user)
      var friendshipStatus = -1
      var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO;
      var userSelected = userDao.findUserById(lastFriendVisitedId).getOrElse { null }
      var isFriendUserSelected = userDao.isThisUserAFriend(user.getId, lastFriendVisitedId)
      
      if (isFriendUserSelected) {
        var status = userDao.getUserFriendshipStatus(user.getId, lastFriendVisitedId)
        status match {
          case Some(value) => friendshipStatus = value
        }
      }

      Ok(views.html.perfil(userSelected, user, isFriendUserSelected, friendshipStatus))
    }.getOrElse {
      Ok(views.html.index()).withNewSession
    }

  }

  
  
  def showAlbumes(userId: Long) = Action { request =>

    request.session.get("usuarioEmail").map { userEmail =>
      Redirect("/albumesPag").withSession(request.session - "lastFriendVisitedId")
                            .withSession(request.session + ("lastFriendVisitedId" -> userId.toString))
    }.getOrElse {
      Ok(views.html.index()).withNewSession
    }
  }

  
  
  def showAlbumesPag = Action {
    request =>

      request.session.get("usuarioEmail").map { userEmail =>
        
        var user: Usuario = new Usuario();
        user = currentUserBuild(request,user)
        
        var lastFriendVisitedId: Long = 0
      request.session.get("lastFriendVisitedId") match{
        case Some(value) =>
             lastFriendVisitedId = value.toLong;
      }
        
        var friendshipStatus = -1
        var albumDao: AlbumDAO = DAOFabrica.getAlbumDAO;
        var albumes = albumDao.findAlbumsByUser(lastFriendVisitedId)
        var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO;
        var userSelected = userDao.findUserById(lastFriendVisitedId).getOrElse { null }
        var isFriendUserSelected = userDao.isThisUserAFriend(user.getId, lastFriendVisitedId)
      
        if (isFriendUserSelected) {
          var status = userDao.getUserFriendshipStatus(user.getId, lastFriendVisitedId)
          status match {
            case Some(value) => friendshipStatus = value
          }
        }
        
        Ok(views.html.albumes(albumes, userSelected, albumDao, user,isFriendUserSelected, friendshipStatus))
      }.getOrElse {
        Ok(views.html.index()).withNewSession
      }
  }

  
  
  def showAmigos(userId: Long) = Action {
    request =>

      request.session.get("usuarioEmail").map { userEmail =>
        Redirect("/amigosPag").withSession(request.session - "lastFriendVisitedId")
                            .withSession(request.session + ("lastFriendVisitedId" -> userId.toString))
      }.getOrElse {
        Ok(views.html.index()).withNewSession
      }
  }

  
  
  def showAmigosPag = Action { request =>

    request.session.get("usuarioEmail").map { userEmail =>
      
      var user: Usuario = new Usuario();
      user = currentUserBuild(request,user)
      
      var lastFriendVisitedId: Long = 0
      request.session.get("lastFriendVisitedId") match{
        case Some(value) =>
             lastFriendVisitedId = value.toLong;
      }
      
      var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO;
      var userSelected = userDao.findUserById(lastFriendVisitedId).getOrElse { null }
      userSelected.setAmistades(userDao.findFriendsByUser(userSelected.getId))
      
      Ok(views.html.amigos(userSelected, user))
    }.getOrElse {
      Ok(views.html.index()).withNewSession
    }
  }

  
  
  def showAlbumContent(albumId: Long) = Action {implicit request =>
    
       Redirect("/showAlbumContent").withSession(session + ("albumId" -> albumId.toString))
  }
  
  def showAlbumContentPage() = Action{
    
       implicit request =>

       request.session.get("albumId").map { albumId =>
        
        var user: Usuario = new Usuario();
        user = currentUserBuild(request,user)
        
        var albumDao: AlbumDAO = DAOFabrica.getAlbumDAO
        var albumActual: Album = albumDao.findAlbumById(albumId.toLong).getOrElse { null }    
        albumActual.setContenidoMultimedia(albumDao.getContenidoByAlbum(albumActual.getId))
        
        var comentarioDao: ComentarioDAO = DAOFabrica.getComentarioDAO
        var comments: List[Comentario] = comentarioDao.getCommentsForAlbum(albumActual.getId)
        albumActual.setComentarios(getCommentsForComments(comments))
        
        var likeDao: LikeDAO = DAOFabrica.getLikeDAO
        var likes: Long = likeDao.getLikesForAlbum(albumActual.getId)
        var unlikes: Long = likeDao.getUnlikesForAlbum(albumActual.getId)
        albumActual.setLikes(likes)
        albumActual.setUnlikes(unlikes)
        
        var lastFriendVisitedId: Long = 0
        request.session.get("lastFriendVisitedId") match{
          case Some(value) =>
             lastFriendVisitedId = value.toLong;
        }
        var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO;
        var userSelected = userDao.findUserById(lastFriendVisitedId).getOrElse { null }
        
        
        Ok(views.html.contenidoAlbum(userSelected,user,albumActual));
      
      }.getOrElse {
        Ok(views.html.index()).withNewSession
      }
  }

  
  
  def showCarrusel = Action {
    implicit request =>

      request.session.get("albumId").map { albumId =>
        
        var user: Usuario = new Usuario();
        user = currentUserBuild(request,user)
        
        var albumDao: AlbumDAO = DAOFabrica.getAlbumDAO
        var albumActual: Album = albumDao.findAlbumById(albumId.toLong).getOrElse { null }
        
        albumActual.setContenidoMultimedia(albumDao.getContenidoByAlbum(albumActual.getId))
        
        Ok(views.html.carrusel(user,albumActual,0))
      }.getOrElse {
        Ok(views.html.index()).withNewSession
      }
  }

  
  
  def updateRegisteredUser(option: Int, attribute: String, userId: Long) = Action {
    request =>

      
      
      request.session.get("usuarioEmail").map { userEmail =>
        
        var user: Usuario = new Usuario();
        user = currentUserBuild(request,user)
        
        var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO;
        
        if (option == 1) {
          userDao.updateUserFirstName(attribute, userId)
          
        } else if (option == 5) {
          userDao.updateUserSecondName(attribute, userId)
        } else if (option == 2) {
          userDao.updateUserFirstLastname(attribute, userId)
        } else if (option == 6) {
          userDao.updateUserSecondLastname(attribute, userId)
        } else if (option == 3) {
          userDao.updateUserNickname(attribute, userId)
        } else if (option == 7) {
          userDao.updateUserTwitter(attribute, userId)
        } else if (option == 8) {
          userDao.updateUserFacebook(attribute, userId)
        } else if (option == 9) {
          userDao.updateUserGoogle(attribute, userId)
        }
        
        var currentUser = userDao.findUserById(user.getId).getOrElse { null }

        Ok("true").withSession("usuarioEmail" -> currentUser.getEmail, 
                                           "userId" -> currentUser.getId.toString,
                                           "userFirstName" -> currentUser.getPrimerNombre,
                                           "userSecondName" -> (currentUser.getSegundoNombre match {case Some(value) => value case _ => ""}),
                                           "userFirstLastname" -> currentUser.getPrimerApellido,
                                           "userSecondLastname" -> (currentUser.getSegundoApellido match {case Some(value) => value case _ => ""}),
                                           "username" -> currentUser.getUsername,
                                           "birthday" -> currentUser.getFechaNacimiento.toString(),
                                           "picture" -> (currentUser.getFoto match {case Some(value) => value case _ => ""}),
                                           "twitter" -> (currentUser.getTwitter match {case Some(value) => value case _ => ""}),
                                           "facebook" -> (currentUser.getFacebook match {case Some(value) => value case _ => ""}),
                                           "gmail" -> (currentUser.getGmail match {case Some(value) => value case _ => ""}),
                                           "privacidad" -> currentUser.getPrivacidad.toString,
                                           "latitud" -> currentUser.getLatitud,
                                           "longitud" -> currentUser.getLongitud,
                                           "FBId" -> currentUser.getFacebookId,
                                           "lastFriendVisitedId" -> "0")
      }.getOrElse {
        Ok(views.html.index()).withNewSession
      }
  }

  
  
  def createAlbum() = Action { request =>

    request.session.get("usuarioEmail").map { userEmail =>
      
      var user: Usuario = new Usuario();
      user = currentUserBuild(request,user)
      
      Ok(views.html.crearAlbum(user))
    }.getOrElse {
      Ok(views.html.index()).withNewSession
    }
  }

  
  
  def deleteAlbum(albumId: Long) = Action { request =>

    request.session.get("usuarioEmail").map { userEmail =>
      
      var albumDao: AlbumDAO = DAOFabrica.getAlbumDAO
      albumDao.deleteAlbumById(albumId)
      
      Redirect("/albumesPag")
    
    }.getOrElse {
      Ok(views.html.index()).withNewSession
    }
  }

  
  def insertAlbum() = Action { implicit request =>
  
   val albumName = request.body.asFormUrlEncoded.get("name").head;
   val albumDescription = request.body.asFormUrlEncoded.get("description").head;
   val albumPrivacy = request.body.asFormUrlEncoded.get("privacy").head.toInt;
   val albumImgRoute = request.body.asFormUrlEncoded.get("imageRoute").head;
   val photos = request.body.asFormUrlEncoded.get("photos[]");
   val tumbnails = request.body.asFormUrlEncoded.get("tumbs[]");
   
   var user: Usuario = new Usuario();
   user = currentUserBuild(request,user)
   
   var album: Album = new Album(albumName, albumPrivacy, "assets/images/" + albumImgRoute, Some(albumDescription), user.getId)
   var albumDao: AlbumDAO = DAOFabrica.getAlbumDAO
   albumDao.insertAlbum(album)
   
   var idAlbum: Long = albumDao.getNewAlbumLastIdFromSequence
   var contenidoDao: ContenidoMultimediaDAO = DAOFabrica.getContenidoMultimediaDAO
   var contenidoMultimedia: ContenidoMultimedia = new ContenidoMultimedia();
  
   var tumbnailPosition = 0;
   
   for(ruta <- photos ){
     
      contenidoMultimedia.setRuta(ruta)
      contenidoMultimedia.setRedSocial("INSTAGRAM")
      contenidoMultimedia.setInAlbumId(idAlbum)
      contenidoMultimedia.setRutaTumbnail(tumbnails(tumbnailPosition))
      
      contenidoDao.insertContenidoMultimedia(contenidoMultimedia)
      
      tumbnailPosition += 1
  
   }
 
    
   createNewAlbumNotification(request)
   
    Ok("true");
  }

  def searchForFriends(nameUserPattern: String) = Action {

    var json: JsValue = null;
    var jsonString = ""
    var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO;
    var users: List[Usuario] = userDao.searchUsersByFullNamePattern(nameUserPattern)
    var usersFoundNumber = 0;

    if (users.length > 0) {
      jsonString = """ {"users": [ """
      
        for (usuario <- users) {
        usersFoundNumber += 1
        
        jsonString = jsonString + """
		{
			"primerNombre" : """" + usuario.getPrimerNombre + """",
			"segundoNombre" : """" + (usuario.getSegundoNombre match { case Some(value) => value case _ => "" }) + """",
			"primerApellido" : """" + usuario.getPrimerApellido + """",
			"segundoApellido" : """" + (usuario.getSegundoApellido match { case Some(value) => value case _ => "" }) + """",
			"username" : """" + usuario.getUsername + """",
			"id" : """" + usuario.getId + """",
			"facebookid" : """" + usuario.getFacebookId + """"

		}
		"""

        if (usersFoundNumber != users.length) {
          jsonString = jsonString + ","
        }

      }

      jsonString = jsonString + " ]}"
    }

    json = Json.parse(jsonString)

    Ok(json).as("application/json")
  }

  
  
  
  def locationModify() = Action {
    request =>

      request.session.get("usuarioEmail").map { userEmail =>
        
        var user: Usuario = new Usuario();
        user = currentUserBuild(request,user)

        Ok(views.html.modificacionUbicacion(user))
      }.getOrElse {
        Ok(views.html.index()).withNewSession
      }
  }

  
  
  def updateUserLocation(country: String, state: String, city: String, latitud: String, longitud: String) = Action {
    
    implicit request =>

    var user: Usuario = new Usuario();
    user = currentUserBuild(request,user)

    var userLocation = Util.getUserLocation(country, state, city)

    var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO
    userDao.updateUserLatitud(latitud, user.getId)
    userDao.updateUserLongitud(longitud, user.getId)
    userDao.updateUserLocation(userLocation.getId, user.getId)

    var currentUser = userDao.findUserById(user.getId).getOrElse { null }
    
    Redirect("/perfilPag").withSession("usuarioEmail" -> currentUser.getEmail, 
                                           "userId" -> currentUser.getId.toString,
                                           "userFirstName" -> currentUser.getPrimerNombre,
                                           "userSecondName" -> (currentUser.getSegundoNombre match {case Some(value) => value case _ => ""}),
                                           "userFirstLastname" -> currentUser.getPrimerApellido,
                                           "userSecondLastname" -> (currentUser.getSegundoApellido match {case Some(value) => value case _ => ""}),
                                           "username" -> currentUser.getUsername,
                                           "birthday" -> currentUser.getFechaNacimiento.toString(),
                                           "picture" -> (currentUser.getFoto match {case Some(value) => value case _ => ""}),
                                           "twitter" -> (currentUser.getTwitter match {case Some(value) => value case _ => ""}),
                                           "facebook" -> (currentUser.getFacebook match {case Some(value) => value case _ => ""}),
                                           "gmail" -> (currentUser.getGmail match {case Some(value) => value case _ => ""}),
                                           "privacidad" -> currentUser.getPrivacidad.toString,
                                           "latitud" -> currentUser.getLatitud,
                                           "longitud" -> currentUser.getLongitud,
                                           "FBId" -> currentUser.getFacebookId,
                                           "lastFriendVisitedId" -> "0")
  }

  
  
  def searchForFriendsPage() = Action { implicit request=>

    var user: Usuario = new Usuario();
    user = currentUserBuild(request,user)
    Ok(views.html.searchForFriends(user))
  }

 
  
  def createUserFriendRequestNotification = Action {implicit request =>

    var lastFriendVisitedId: Long = 0
      request.session.get("lastFriendVisitedId") match{
        case Some(value) =>
             lastFriendVisitedId = value.toLong;
      }
    
    var notificacionDao = DAOFabrica.getNotificacionDAO
    var userDao = DAOFabrica.getUsuarioDAO
    var newNotification: Notificacion = new Notificacion()
    
    var user: Usuario = new Usuario();
    user = currentUserBuild(request,user)

    userDao.insertAmistad(user.getId, lastFriendVisitedId)

    newNotification.setContenido(user.getPrimerNombre + " " + user.getPrimerApellido + " quiere ser tu amigo en Feeshbook")
    newNotification.setTipo(notificacionDao.getTypeNotificationNumberFormat("SOLICITUD"))
    newNotification.setIdAmigo(Some(user.getId))
    newNotification.setIdUsuarioGenerador(user.getId)

    notificacionDao.insertNotification(newNotification, lastFriendVisitedId)

    Ok("true")

  }

  
  
  def getSolicitudesAmistad(userId: Long) = Action { implicit request =>

    var user: Usuario = new Usuario();
    user = currentUserBuild(request,user)
        
    var json: JsValue = null;
    var jsonString = ""
    var notificacionDao: NotificacionDAO = DAOFabrica.getNotificacionDAO
    var notifications: List[Notificacion] = notificacionDao.getNotificationsByUserAndType(user.getId, "SOLICITUD")
    var notificationsFoundNumber = 0;
    var userId: Option[Long] = null;

    if (notifications.length > 0) {
      jsonString = """ {"notification": [ """
      for (notification <- notifications) {
        var amigoId: Long = 0
        notificationsFoundNumber += 1
        userId = notification.getIdAmigo

        userId match {
          case Some(id) => amigoId = id
        }

        var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO
        var user: Option[Usuario] = userDao.findUserById(amigoId)
        var notificationUser = new Usuario()

        user match {
          case Some(person) => notificationUser = person
        }
        jsonString = jsonString + """
		{
			"primerNombre" : """" + notificationUser.getPrimerNombre + """",
		    "segundoNombre" : """" + (notificationUser.getSegundoNombre match { case Some(value) => value case _ => "" }) + """",
			"primerApellido" : """" + notificationUser.getPrimerApellido + """",
			"segundoApellido" : """" + (notificationUser.getSegundoApellido match { case Some(value) => value case _ => "" }) + """",
			"id" : """" + notificationUser.getId + """",
			"notificationid" : """" + notification.getId + """",
			"facebookid" : """" + notificationUser.getFacebookId + """"
			

		}
		"""

        if (notificationsFoundNumber != notifications.length) {
          jsonString = jsonString + ","
        }

      }

      jsonString = jsonString + " ]}"
    }

    json = Json.parse(jsonString)

    Ok(json).as("application/json")

  }

  
  
  def confirmFriendship(friendId: Long, notificationId: Long) = Action {implicit request=>
    
    var user: Usuario = new Usuario();
    user = currentUserBuild(request,user)
        
    var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO
    userDao.confimFriendship(user.getId, friendId)
    
    var friendOp: Option[Usuario] = userDao.findUserById(friendId)
    var friend: Usuario = new Usuario()
    
    friendOp match{
      case Some(user) =>
        
        friend = user
        
    }

    var notificacionDao: NotificacionDAO = DAOFabrica.getNotificacionDAO
    notificacionDao.deleteFriendshipRequestNotifications(user.getId, notificationId)

    var newNotification: Notificacion = new Notificacion()
    newNotification.setContenido(user.getPrimerNombre +" " + user.getPrimerApellido + " ahora es amigo de " + friend.getPrimerNombre +" " + friend.getPrimerApellido )
    newNotification.setTipo(notificacionDao.getTypeNotificationNumberFormat("AMISTAD"))
    newNotification.setIdAmigo(Some(friendId))
    newNotification.setIdUsuarioGenerador(user.getId)
    notificacionDao.insertNotification(newNotification, user.getId)

    user.setAmistades(userDao.findFriendsByUser(user.getId))

    val newNotificationId = notificacionDao.getNewNotificationLastIdFromSequence
    notificacionDao.insertNotificationMulticast(user.getAmistades, newNotificationId)

  
    Ok("true")

  }

  
  
  def searchFriends(namePart: String) = Action {

    var json: JsValue = null;
    var jsonString = ""
    var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO;
    var users: List[Usuario] = userDao.getUsersByFullNamePart(namePart)
    var usersFoundNumber = 0;

    if (users.length > 0) {
      jsonString = """ {"users": [ """
      for (usuario <- users) {
        usersFoundNumber += 1
        jsonString = jsonString + """
		{
		   "primerNombre" : """" + usuario.getPrimerNombre + """",
			"segundoNombre" : """" + (usuario.getSegundoNombre match { case Some(value) => value case _ => "" }) + """",
			"primerApellido" : """" + usuario.getPrimerApellido + """",
			"segundoApellido" : """" + (usuario.getSegundoApellido match { case Some(value) => value case _ => "" }) + """",
			"username" : """" + usuario.getUsername + """",
			"id" : """" + usuario.getId + """",
			"foto" : """" + usuario.getFoto + """",
			"facebookid" : """" + usuario.getFacebookId + """"

			}
			"""

        if (usersFoundNumber != users.length) {
          jsonString = jsonString + ","
        }

      }

      jsonString = jsonString + " ]}"
    }

    json = Json.parse(jsonString)

    Ok(json).as("application/json")
  }

  
  
  def searchPage(namePart: String) = Action {

    Redirect("/searchForFriendsPage");
  }
  
  
  def createNewAlbumNotification(request: Request[AnyContent])  {
      
    var user: Usuario = new Usuario();
    user = currentUserBuild(request,user)
    
    var notificacionDao: NotificacionDAO = DAOFabrica.getNotificacionDAO
    var newNotification: Notificacion = new Notificacion()
    
    var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO
    
    newNotification.setContenido(user.getPrimerNombre +" " + user.getPrimerApellido +  " ha creado un nuevo Album")
    newNotification.setTipo(notificacionDao.getTypeNotificationNumberFormat("ALBUM"))
    newNotification.setIdAmigo(null)
    newNotification.setIdUsuarioGenerador(user.getId)
    notificacionDao.insertNotification(newNotification, user.getId)

    user.setAmistades(userDao.findFriendsByUser(user.getId))

    val newNotificationId = notificacionDao.getNewNotificationLastIdFromSequence
    notificacionDao.insertNotificationMulticast(user.getAmistades, newNotificationId)


  }
  
  def deleteFriendship(userId: Long,friendId: Long) = Action { request =>

    request.session.get("usuarioEmail").map { userEmail =>
      
      var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO
      userDao.deleteAmistadByUsersId(userId, friendId)
      
      Redirect("/amigosPag")
    
    }.getOrElse {
      Ok(views.html.index()).withNewSession
    }
  }
  
  def showPerfilPublic(userId: Long) = Action { implicit request =>
      
      Redirect("/perfilPublicPag").withSession(session - "lastFriendVisitedId")
                            .withSession(session + ("lastFriendVisitedId" -> userId.toString))
    
  }
  
  def showPerfilPublicPag = Action {implicit request =>
     
      var lastFriendVisitedId: Long = 0
      request.session.get("lastFriendVisitedId") match{
        case Some(value) =>
             lastFriendVisitedId = value.toLong;
      }
      
      var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO;
      var userSelected = userDao.findUserById(lastFriendVisitedId).getOrElse { null }
        
      Ok(views.html.perfilPublic(userSelected))
   
  }
  
  
   def createNewComment() = Action { implicit request =>
  
      var user: Usuario = new Usuario();
      user = currentUserBuild(request,user)
      
      val elementId = request.body.asFormUrlEncoded.get("elementId").head.toLong;
      val contenido = request.body.asFormUrlEncoded.get("contenido").head;
      val tipo = request.body.asFormUrlEncoded.get("tipo").head;
      
      var comentario = new Comentario();
      
      if(tipo == "ALBUM"){
          comentario.setFkAlbum(Some(elementId))
          comentario.setFkContenido(null);
          comentario.setFkComentario(null);
      }
      else if(tipo == "CONTENIDO"){
          comentario.setFkAlbum(null)
          comentario.setFkContenido(Some(elementId));
          comentario.setFkComentario(null);
      }
      else{
          comentario.setFkAlbum(null)
          comentario.setFkContenido(null);
          comentario.setFkComentario(Some(elementId));
      }
      
      comentario.setContenido(contenido);
      comentario.setFkUsuario(user.getId)
      
      var comentarioDao: ComentarioDAO = DAOFabrica.getComentarioDAO
      
      var commentId = comentarioDao.insertComentario(comentario);
      
     
        Ok(commentId.toString);
    }
   
   
   
    def createNewLike() = Action { implicit request =>
  
      var user: Usuario = new Usuario();
      user = currentUserBuild(request,user)
      
      val elementId = request.body.asFormUrlEncoded.get("elementId").head.toLong;
      val element = request.body.asFormUrlEncoded.get("element").head;
      
      var like = new Like();
      
      if(element == "ALBUM"){
          like.setFkAlbum(Some(elementId))
          like.setFkContenido(null);
          like.setFkComentario(null);
      }
      else if(element == "CONTENIDO"){
          like.setFkAlbum(null)
          like.setFkContenido(Some(elementId));
          like.setFkComentario(null);
      }
      else{
          like.setFkAlbum(null)
          like.setFkContenido(null);
          like.setFkComentario(Some(elementId));
      }
      
      like.setTipo("L");
      like.setFkUsuario(user.getId)
      
      var likeDao: LikeDAO = DAOFabrica.getLikeDAO
      
      likeDao.insertLike(like); 
      
     
        Ok("true");
    }

    
    def createNewUnlike() = Action { implicit request =>
  
      var user: Usuario = new Usuario();
      user = currentUserBuild(request,user)
      
      val elementId = request.body.asFormUrlEncoded.get("elementId").head.toLong;
      val element = request.body.asFormUrlEncoded.get("element").head;
      
      var like = new Like();
      
      if(element == "ALBUM"){
          like.setFkAlbum(Some(elementId))
          like.setFkContenido(null);
          like.setFkComentario(null);
      }
      else if(element == "CONTENIDO"){
          like.setFkAlbum(null)
          like.setFkContenido(Some(elementId));
          like.setFkComentario(null);
      }
      else{
          like.setFkAlbum(null)
          like.setFkContenido(null);
          like.setFkComentario(Some(elementId));
      }
      
      like.setTipo("U");
      like.setFkUsuario(user.getId)
      
      var likeDao: LikeDAO = DAOFabrica.getLikeDAO
      
      likeDao.insertLike(like); 
      
     
        Ok("true");
    }
    
    def deleteComment(commentId: Long) = Action {
      var commentDao: ComentarioDAO = DAOFabrica.getComentarioDAO
      commentDao.deleteCommentById(commentId)
      
      Ok("true");
    }
    
  def currentUserBuild(request: Request[AnyContent], user: Usuario): Usuario = {

    request.session.get("userId").map { id => user.setId(id.toLong) }
    request.session.get("userFirstName").map { primerNombre => user.setPrimerNombre(primerNombre) }
    request.session.get("userSecondName").map { segundoNombre => user.setSegundoNombre(Some(segundoNombre)) }
    request.session.get("userFirstLastname").map { primerApellido => user.setPrimerApellido(primerApellido) }
    request.session.get("userSecondLastname").map { segundoApellido => user.setSegundoApellido(Some(segundoApellido)) }
    request.session.get("usuarioEmail").map { email => user.setEmail(email) }
    // request.session.get("birthday").map{ birthday =>user.setFechaNacimiento()}  TO_DATE
    request.session.get("userFacebook").map { facebook => user.setFacebook(Some(facebook)) }
    request.session.get("userTwitter").map { twitter => user.setTwitter(Some(twitter)) }
    request.session.get("userGmail").map { gmail => user.setGmail(Some(gmail)) }
    request.session.get("privacidad").map { privacidad => user.setPrivacidad(privacidad.toInt) }
    request.session.get("latitud").map { latitud => user.setLatitud(latitud) }
    request.session.get("longitud").map { longitud => user.setLongitud(longitud) }
    request.session.get("FBId").map { FBId => user.setFacebookId(FBId) }
    
    return user;

  }
  
  def getCommentsForComments(commentList: List[Comentario]): List[Comentario] = {
    
    var numeroComentarios = 0;
    for(comment <- commentList)
    {
        var commentDao: ComentarioDAO = DAOFabrica.getComentarioDAO
        var comments: List[Comentario] = commentDao.getResponsesForComment(comment.getId)
        commentList(numeroComentarios).setRespuestas(getCommentsForComments(comments))
        
        var likeDao: LikeDAO = DAOFabrica.getLikeDAO
        var likes: Long = likeDao.getLikesForComment(commentList(numeroComentarios).getId)
        var unlikes: Long = likeDao.getUnlikesForComment(commentList(numeroComentarios).getId)
        commentList(numeroComentarios).setLikes(likes)
        commentList(numeroComentarios).setUnlikes(unlikes)
        
         
        numeroComentarios = numeroComentarios+1;
    }
    
    return commentList
    
  }
  
  def albumAddContent() = Action { implicit request =>
    
      var user: Usuario = new Usuario();
      user = currentUserBuild(request,user)
      
      var albumId: Long = 0
      request.session.get("albumId").map { id => albumId = id.toLong }
      
      var albumDao: AlbumDAO = DAOFabrica.getAlbumDAO
      var albumActual: Album = albumDao.findAlbumById(albumId.toLong).getOrElse { null }

       
       Ok(views.html.albumAddContent(user,albumActual))
    
  }
  
  def albumDeleteContent() = Action { implicit request =>
    
      var user: Usuario = new Usuario();
      user = currentUserBuild(request,user)
      
      var albumId: Long = 0
      request.session.get("albumId").map { id => albumId = id.toLong }
      
      var albumDao: AlbumDAO = DAOFabrica.getAlbumDAO
      var albumActual: Album = albumDao.findAlbumById(albumId.toLong).getOrElse { null }
      albumActual.setContenidoMultimedia(albumDao.getContenidoByAlbum(albumActual.getId))

       
       Ok(views.html.albumDeleteContent(user,albumActual))
    
  }
  
  def addContentToAlbum() = Action { implicit request =>
   
     val photos = request.body.asFormUrlEncoded.get("photos[]");
     val tumbnails = request.body.asFormUrlEncoded.get("tumbs[]");
   
     var user: Usuario = new Usuario();
     user = currentUserBuild(request,user)
     
     var albumId: Long = 0
     request.session.get("albumId").map { id => albumId = id.toLong }      
     var albumDao: AlbumDAO = DAOFabrica.getAlbumDAO
     var albumActual: Album = albumDao.findAlbumById(albumId.toLong).getOrElse { null }
     
     var idAlbum: Long = albumActual.getId
     var contenidoDao: ContenidoMultimediaDAO = DAOFabrica.getContenidoMultimediaDAO
     var contenidoMultimedia: ContenidoMultimedia = new ContenidoMultimedia();
  
     var tumbnailPosition = 0;
   
     for(ruta <- photos ){
     
        contenidoMultimedia.setRuta(ruta)
        contenidoMultimedia.setRedSocial("INSTAGRAM")
        contenidoMultimedia.setInAlbumId(idAlbum)
        contenidoMultimedia.setRutaTumbnail(tumbnails(tumbnailPosition))
      
        contenidoDao.insertContenidoMultimedia(contenidoMultimedia)
      
        tumbnailPosition += 1
  
     }
 
   
      Ok("true");
  }
  
  def deleteContentInAlbum() = Action { implicit request =>
    
    val photos = request.body.asFormUrlEncoded.get("photos[]");
    
    var user: Usuario = new Usuario();
    user = currentUserBuild(request,user)
    
    var albumId: Long = 0
    request.session.get("albumId").map { id => albumId = id.toLong }   
    
    var contenidoDao: ContenidoMultimediaDAO = DAOFabrica.getContenidoMultimediaDAO
    
     var tumbnailPosition = 0;
   
     for(photo <- photos ){
     
        contenidoDao.deleteContenidoMultimedia(photo.toLong)
      
        tumbnailPosition += 1
  
     }
   
    Ok("true");
    
  }
  

}