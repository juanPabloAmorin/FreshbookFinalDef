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

    Logger.info("Se han recibido los fatos de FB: email="+userEmail+" enlace="+link+ 
                "primer nombre="+firstName + "segundo nombre =" +middleName+
                "primer Apellido="+lastName+"facebook Id="+facebookId)
    
    newUser = new Usuario(firstName, middleName, lastName, null, userEmail, link, "",facebookId);

    try
    {
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
    }catch{
      
      case e: DAOException => 
                  Logger.error("El usuario "+userEmail+ "no pudo ser encontrado")
                  throw BusinessException.create("El usuario no pudo ser encontrado",e)
      case e: NullPointerException => 
                  Logger.error("Los datos de usuario estan incompletos. Se han recibido los fatos de FB: email="+userEmail+" enlace="+link+ 
                "primer nombre="+firstName + "segundo nombre =" +middleName+
                "primer Apellido="+lastName+"facebook Id="+facebookId)

                  throw new Exception("Se debe conta con nombre, apellido, mail y username en la cuenta de facebook")
    }
    
    

  }

  
  
  def showRegistroPaso1Pag = Action {

    Logger.debug("AL registro paso 1 con exito")
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
    
    Logger.info("Se guardaron los datos de nuevo usuario: primer nombre="+firstName+" segundonombre="+
                secondName+" primerApellido="+firstLastName+" segundoApellido="+secondLastName +
                "username="+username+"fecha naciemiento="+dia+"/"+mes+"/"+anio+" privacdad="+privacy+
                " ruta foto="+photo+" idFB="+id)
    
    Redirect("/registroPaso2Pag")
  }

  
  
  def showRegistroPaso2Pag = Action {

    Logger.debug("Al registro paso 2 con exito")
    Ok(html.registroPaso2())
  }

 
  
  def addingNewUser(country: String, state: String, city: String, latitud: String, 
                    longitud: String) = Action {
    
    Logger.info("Se recibieron los siguientes datos de localizacion: pais="+country+
                " estado="+state+" ciudad="+city+" latitud="+latitud+" longitud="+longitud)

    var userLocation = Util.getUserLocation(country, state, city)
    newUser.setUbicacion(userLocation)
    newUser.setLatitud(latitud)
    newUser.setLongitud(longitud)
    
    try
    {
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
    catch{
      
      case e: DAOException => 
                  Logger.error("El usuario "+newUser.getPrimerNombre + " " +newUser.getPrimerApellido +
                              newUser.getEmail + "no pudo ser creado")
                  throw BusinessException.create("El usuario "+newUser.getPrimerNombre + " " +newUser.getPrimerApellido +
                              newUser.getEmail + "no pudo ser creado",e)
    }
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
      
      try
      {
      var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO;
      var userSelected = userDao.findUserById(lastFriendVisitedId).getOrElse { null }
      
      Logger.debug("Perfil.UserSelected = "+ userSelected.getPrimerNombre + " " + userSelected.getPrimerApellido)
      
      var isFriendUserSelected = userDao.isThisUserAFriend(user.getId, lastFriendVisitedId)
      
      if (isFriendUserSelected) {
        var status = userDao.getUserFriendshipStatus(user.getId, lastFriendVisitedId)
        status match {
          case Some(value) => friendshipStatus = value
        }
      }

      Ok(views.html.perfil(userSelected, user, isFriendUserSelected, friendshipStatus))
      } catch{
        
        case e: DAOException => 
                  Logger.error("Acceso negado al perfil del usuario "+lastFriendVisitedId + "error:"+e.getMessage())
                  throw BusinessException.create("No es posible acceder al perfil del usuario solicitado" ,e)
        
      }
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
        
        try
        {
        var albumDao: AlbumDAO = DAOFabrica.getAlbumDAO;
        var albumes = albumDao.findAlbumsByUser(lastFriendVisitedId)
        var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO;
        var userSelected = userDao.findUserById(lastFriendVisitedId).getOrElse { null }
        
        Logger.debug("Albumes.UserSelected = "+ userSelected.getPrimerNombre + " " + userSelected.getPrimerApellido)
     
        var isFriendUserSelected = userDao.isThisUserAFriend(user.getId, lastFriendVisitedId)
      
        if (isFriendUserSelected) {
          var status = userDao.getUserFriendshipStatus(user.getId, lastFriendVisitedId)
          status match {
            case Some(value) => friendshipStatus = value
          }
        }
        
        Ok(views.html.albumes(albumes, userSelected, albumDao, user,isFriendUserSelected, friendshipStatus))
        }catch{
        
        case e: DAOException => 
                  Logger.error("No es posible el acceso los albumes del usuario "+lastFriendVisitedId + "error:"+e.getMessage())
                  throw BusinessException.create("No es posible acceder a la lista de albumes solicitada" ,e)
        
      }
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
      
      try
      {
      var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO;
      var userSelected = userDao.findUserById(lastFriendVisitedId).getOrElse { null }
      
      Logger.debug("Albumes.UserSelected = "+ userSelected.getPrimerNombre + " " + userSelected.getPrimerApellido)
    
      userSelected.setAmistades(userDao.findFriendsByUser(userSelected.getId))
      
      Ok(views.html.amigos(userSelected, user))
      }catch{
        
        case e: DAOException => 
                  Logger.error("No es posible el acceso a la lista de amigos de "+lastFriendVisitedId + "error:"+e.getMessage())
                  throw BusinessException.create("No es posible acceder a la lista de amigos solicitada" ,e)
        
      }
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
         
       try{
        var albumDao: AlbumDAO = DAOFabrica.getAlbumDAO
        var comentarioDao: ComentarioDAO = DAOFabrica.getComentarioDAO
        var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO
        var likeDao: LikeDAO = DAOFabrica.getLikeDAO
     
        var user: Usuario = new Usuario();
        user = currentUserBuild(request,user)
        
        var albumActual: Album = albumDao.findAlbumById(albumId.toLong).getOrElse { null }    
        albumActual.setContenidoMultimedia(albumDao.getContenidoByAlbum(albumActual.getId))
        
        Logger.debug("AlbumContent.album: nombre:"+albumActual.getNombre)
        
        var comments: List[Comentario] = comentarioDao.getCommentsForAlbum(albumActual.getId)
        albumActual.setComentarios(getCommentsForComments(comments,user.getId))
        
        Logger.debug("Se obtuvo la lista de comentarios")
               
        var likes: Long = likeDao.getLikesForAlbum(albumActual.getId)
        var unlikes: Long = likeDao.getUnlikesForAlbum(albumActual.getId)
        
        albumActual.setLikes(likes)
        albumActual.setUnlikes(unlikes)
        albumActual.setIsLiked(likeDao.iLikeAlbum(albumActual.getId,user.getId))
        albumActual.setIsUnliked(likeDao.iDontLikeAlbum(albumActual.getId,user.getId))
        
        Logger.debug("Se cargaron los like y unlike del album")
        
        var numeroComentario = 0
        for(comment <- comments)
        {
             var usuario: Usuario = userDao.findUserById(comment.getFkUsuario).getOrElse(null)
             comments(numeroComentario).setOwner(usuario)
             comments(numeroComentario).setIsLiked(likeDao.iLikeComment(comment.getId,user.getId))
             comments(numeroComentario).setIsUnLiked(likeDao.iDontLikeComment(comment.getId,user.getId))

             numeroComentario = numeroComentario+1
             
        }
        
        
        var lastFriendVisitedId: Long = 0
        request.session.get("lastFriendVisitedId") match{
          case Some(value) =>
             lastFriendVisitedId = value.toLong;
        }
        
        var userSelected = userDao.findUserById(lastFriendVisitedId).getOrElse { null }
        
        
        Ok(views.html.contenidoAlbum(userSelected,user,albumActual));
       }catch{
        
        case e: DAOException => 
                  Logger.error("No es posible el acceso al contenido del album"+e.getMessage())
                  throw BusinessException.create("No es posible acceder al contenido del album solicitado" ,e)
        
      }
      
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
        
        try{
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
        
        Logger.info("Se ha modificado el "+attribute+" del usuario "+userId+" "+user.getPrimerNombre+" "+user.getPrimerApellido)
        
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
        }catch{
          case e: DAOException => 
                   Logger.info("No es posible modificar el "+attribute+" del usuario "+userId+" "+user.getPrimerNombre+" "+user.getPrimerApellido)
                   throw BusinessException.create("No es posible modificar su "+attribute ,e)

       }
        
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
      
      try{
      var albumDao: AlbumDAO = DAOFabrica.getAlbumDAO
      albumDao.deleteAlbumById(albumId)
      
      Logger.info("Se ha eliminado el album "+albumId)
      
      Redirect("/albumesPag")
      
      }catch{
        case e: DAOException => 
      
                   Logger.info("No es posible eliminar el album "+albumId)
                   throw BusinessException.create("No es posible eliminar el album" ,e)

      }
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
   
   Logger.debug("Se reibieron los datos para el album: "+albumName+" "+albumDescription)
   
   var user: Usuario = new Usuario();
   user = currentUserBuild(request,user)
   
   try{
   var album: Album = new Album(albumName, albumPrivacy, "assets/images/" + albumImgRoute, Some(albumDescription), user.getId)
   var albumDao: AlbumDAO = DAOFabrica.getAlbumDAO
   albumDao.insertAlbum(album)
   
   var idAlbum: Long = albumDao.getNewAlbumLastIdFromSequence
   
   Logger.info("Se ha creado el album "+ albumName +" id = "+idAlbum)
   
   var contenidoDao: ContenidoMultimediaDAO = DAOFabrica.getContenidoMultimediaDAO
   var contenidoMultimedia: ContenidoMultimedia = new ContenidoMultimedia();
  
   var tumbnailPosition = 0;
   
   for(ruta <- photos ){
     
      contenidoMultimedia.setRuta(ruta)
      contenidoMultimedia.setRedSocial("INSTAGRAM")
      contenidoMultimedia.setInAlbumId(idAlbum)
      contenidoMultimedia.setRutaTumbnail(tumbnails(tumbnailPosition))
      
      contenidoDao.insertContenidoMultimedia(contenidoMultimedia)
      
      Logger.info("Se agrego la foto "+ruta+" al album "+albumName)
      
      tumbnailPosition += 1
  
   }
 
    
   createNewAlbumNotification(request)
   
    Ok("true");
   }catch{
     case e: DAOException =>
            Logger.error("No se ha creado el album "+albumName)
            throw BusinessException.create("No ha sido posible crear el album "+albumName,e)
   }
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
    
    try
    {
    var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO
    userDao.updateUserLatitud(latitud, user.getId)
    userDao.updateUserLongitud(longitud, user.getId)
    userDao.updateUserLocation(userLocation.getId, user.getId)
    
    Logger.info("Se ha actualizado la localizacion del usuario " + user.getPrimerNombre + " " + user.getPrimerApellido +
                "localizacion = " +country + " " + state + " " + city)

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
    }catch{
      
        case e: DAOException =>
            Logger.error("No se ha podido actualizar la ubicacion de " + user.getPrimerNombre + " " + user.getPrimerApellido +
                "localizacion = " +country + " " + state + " " + city)
            throw BusinessException.create("No ha sido posible actualizar la localizacion",e)
    }
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
      
      try
      {
      var commentId = comentarioDao.insertComentario(comentario);
      
      Logger.info("Se ha creado el comentario para el "+tipo+" "+elementId)
      
        Ok(commentId.toString);
      }catch{
        case e: DAOException =>
                 Logger.error("No ha sido posible crear el comentario para "+tipo+" seleccionado")
                 throw BusinessException.create("No ha sido posible crear el coentario para "+tipo+" seleccionado",e)
      }
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
      
      try{
      likeDao.insertLike(like); 
      
      Logger.info("Se ha creado el nevo like del usuario"+user.getPrimerNombre+" "+user.getPrimerApellido)
      
        Ok("true");
      }catch{
        case e: DAOException =>
                 Logger.error("No ha sido posible crear el like para "+element+" seleccionado")
                 throw BusinessException.create("No ha sido posible crear el like para "+element+" seleccionado",e)
      }
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
      
      try
      {
      likeDao.insertLike(like); 
      
      Logger.info("Se ha creado el nevo unlike del usuario"+user.getPrimerNombre+" "+user.getPrimerApellido)
      
     
        Ok("true");
      }catch{
        case e: DAOException =>
                 Logger.error("No ha sido posible crear el unlike para "+element+" seleccionado")
                 throw BusinessException.create("No ha sido posible crear el unlike para "+element+" seleccionado",e)
      }
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
    request.session.get("userFacebook").map { facebook => user.setFacebook(Some(facebook)) }
    request.session.get("userTwitter").map { twitter => user.setTwitter(Some(twitter)) }
    request.session.get("userGmail").map { gmail => user.setGmail(Some(gmail)) }
    request.session.get("privacidad").map { privacidad => user.setPrivacidad(privacidad.toInt) }
    request.session.get("latitud").map { latitud => user.setLatitud(latitud) }
    request.session.get("longitud").map { longitud => user.setLongitud(longitud) }
    request.session.get("FBId").map { FBId => user.setFacebookId(FBId) }
   
    return user;

  }
  
  def getCommentsForComments(commentList: List[Comentario], userId: Long): List[Comentario] = {
    
    var numeroComentarios = 0;
    
    
    for(comment <- commentList)
    {
        var commentDao: ComentarioDAO = DAOFabrica.getComentarioDAO
        var comments: List[Comentario] = commentDao.getResponsesForComment(comment.getId)
        commentList(numeroComentarios).setRespuestas(getCommentsForComments(comments,userId))
        
        var likeDao: LikeDAO = DAOFabrica.getLikeDAO
        var likes: Long = likeDao.getLikesForComment(commentList(numeroComentarios).getId)
        var unlikes: Long = likeDao.getUnlikesForComment(commentList(numeroComentarios).getId)
        commentList(numeroComentarios).setLikes(likes)
        commentList(numeroComentarios).setUnlikes(unlikes)
        commentList(numeroComentarios).setIsLiked(likeDao.iLikeComment(comment.getId,userId))
        commentList(numeroComentarios).setIsUnLiked(likeDao.iDontLikeComment(comment.getId,userId))

        
        var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO
        commentList(numeroComentarios).setOwner(userDao.findUserById(comment.getFkUsuario).getOrElse(null))
        
         
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
     
     Logger.debug("Photos y Tumbnails recibidos via POST")
   
     var user: Usuario = new Usuario();
     user = currentUserBuild(request,user)
     
     var albumId: Long = 0
     request.session.get("albumId").map { id => albumId = id.toLong }      
     
     try
     {
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
      
        try
        {
        contenidoDao.insertContenidoMultimedia(contenidoMultimedia)
        }
        catch{
        case e:DAOException => 
                Logger.error("La foto con ruta" + ruta + "no pudo ser agregada al album "+albumId)
                throw BusinessException.create("La foto "+ruta+ " no pudo ser agregada al album",e)
        }
      
        tumbnailPosition += 1
  
     }
 
   
      Ok("true");
      }
     catch{
       case e:DAOException => 
                Logger.error("El album "+albumId+ "no pudo ser encontrado")
                throw BusinessException.create("El album no pudo ser encontrado",e)
     }
     
  }
  
  def deleteContentInAlbum() = Action { implicit request =>
    
    val photos = request.body.asFormUrlEncoded.get("photos[]");
    
    Logger.debug("Id's de fotos recibidos via POST")
    
    var user: Usuario = new Usuario();
    user = currentUserBuild(request,user)
    
    var albumId: Long = 0
    request.session.get("albumId").map { id => albumId = id.toLong }   
    
    var contenidoDao: ContenidoMultimediaDAO = DAOFabrica.getContenidoMultimediaDAO
    
     var tumbnailPosition = 0;
   
     for(photo <- photos ){
     
       try
       {
        contenidoDao.deleteContenidoMultimedia(photo.toLong)
       
      
        tumbnailPosition += 1
        
        Logger.info("Se ha eliminado la foto " + photo +" del album " + albumId + " de " + user.getPrimerNombre + " " + user.getPrimerApellido)
       }catch{
      
      case e: DAOException => 
                  Logger.error("La foto "+photo+ "del album" +albumId+  "no pudo ser eliminada")
                  throw BusinessException.create("El like no pudo ser eliminado",e)
     
       }
     }
   
    Ok("true");
    
  }
  
  def deleteLikeFromAlbum() = Action { implicit request =>
  
      val albumId = request.body.asFormUrlEncoded.get("elementId").head.toLong;

      try{
      var user: Usuario = new Usuario();
      user = currentUserBuild(request,user)
            
      var likeDao: LikeDAO = DAOFabrica.getLikeDAO
      
      likeDao.deleteAlbumLike(user.getId,albumId); 
      
     
        Ok("true");
      }catch{
      
      case e: DAOException => 
                  Logger.error("El like del album "+albumId+ "no pudo ser eliminado")
                  throw BusinessException.create("El like no pudo ser eliminado",e)
      case e: NullPointerException => 
                  Logger.error("Usuario no ha iniciado sesion")
                  throw new Exception("Se ha cerrado la sesion del usuario")
    }
    }
  
  def deleteUnlikeFromAlbum() = Action { implicit request =>
  
      val albumId = request.body.asFormUrlEncoded.get("elementId").head.toLong;

      try{
      
      var user: Usuario = new Usuario();
      user = currentUserBuild(request,user)
      
      var likeDao: LikeDAO = DAOFabrica.getLikeDAO
      
      likeDao.deleteAlbumUnlike(user.getId,albumId); 
      
     
        Ok("true");
      }
      catch{
      
      case e: DAOException => 
                  Logger.error("El like del album "+albumId+ "no pudo ser eliminado")
                  throw BusinessException.create("El like no pudo ser eliminado",e)
      case e: NullPointerException => 
                  Logger.error("Usuario no ha iniciado sesion")
                  throw new Exception("Se ha cerrado la sesion del usuario")
    }
    }
  
  
   def deleteLikeFromComment() = Action { implicit request =>
  
      val commentId = request.body.asFormUrlEncoded.get("elementId").head.toLong;

    try
    {
      var user: Usuario = new Usuario();
      user = currentUserBuild(request,user)
           
      var likeDao: LikeDAO = DAOFabrica.getLikeDAO
      
      likeDao.deleteCommentLike(user.getId,commentId); 
      
     
        Ok("true");
    }catch{
      
      case e: DAOException => 
                  Logger.error("El like del comentario "+commentId+ "no pudo ser eliminado")
                  throw BusinessException.create("El like no pudo ser eliminado",e)
      case e: NullPointerException => 
                  Logger.error("Usuario no ha iniciado sesion")
                  throw new Exception("Se ha cerrado la sesion del usuario")
    }
    }
  
  def deleteUnlikeFromComment() = Action { implicit request =>
  
    val commentId = request.body.asFormUrlEncoded.get("elementId").head.toLong;
    
    try
    {
      var user: Usuario = new Usuario();
      user = currentUserBuild(request,user)
      
      
      var likeDao: LikeDAO = DAOFabrica.getLikeDAO
      
      likeDao.deleteCommentUnlike(user.getId,commentId); 
      
     
        Ok("true");
    }catch{
      
      case e: DAOException => 
                  Logger.error("El unlike del comentario "+commentId+ "no pudo ser eliminado")
                  throw BusinessException.create("El unlike no pudo ser eliminado",e)
      case e: NullPointerException => 
                  Logger.error("Usuario no ha iniciado sesion")
                  throw new Exception("Se ha cerrado la sesion del usuario")
    }
    }
  
  

}