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

  def registroPaso2(firstName: String, secondName: String,firstLastName: String,secondLastName: String, username: String,dia: String,mes: String,anio: String,privacy: Int,photo: String) = Action {

    newUser.setPrimerNombre(firstName)
    if(secondName != "null"){
        newUser.setSegundoNombre(Some(secondName))
    }
    newUser.setPrimerApellido(firstLastName)
    if(secondLastName != "null"){
        newUser.setSegundoApellido(Some(secondLastName))
    }
    newUser.setUsername(username)
    newUser.setPrivacidad(privacy)
    newUser.setFoto(Some(photo))
    
    var stringBirthDate = dia+"/"+mes+"/"+anio 
    var birthDay :Date = new Date(stringBirthDate)
    
    newUser.setFechaNacimiento(birthDay)
    
    Redirect("/registroPaso2Pag")
  }
  
  def showRegistroPaso2Pag = Action {

    
    Ok(html.registroPaso2())
  }
  
  def registroPaso3(country: String, state: String, city: String) = Action {
    
        var userLocation = Util.getUserLocation(country,state,city)
        newUser.setUbicacion(userLocation)
        
        Redirect("/registroPaso3Pag")
    
  }

  def showRegistroPaso3Pag = Action {

    Ok(html.registroPaso3())
  }
  
  def registroFinalUsuario() = Action{
    
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

  def showPerfil(userId: Long) = Action {
    lastFriendVistedId = userId;
    Redirect("/perfilPag")
  }

  def showPerfilPag = Action {
    var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO;
    var userSelected = userDao.findUserById(lastFriendVistedId).getOrElse { null }
    Ok(views.html.perfil(userSelected, currentUser))
  }

  def showAlbumes(userId: Long) = Action {
    lastFriendVistedId = userId;
    Redirect("/albumesPag")
  }

  def showAlbumesPag = Action {
    var albumDao: AlbumDAO = DAOFabrica.getAlbumDAO;
    var albumes = albumDao.findAlbumsByUser(lastFriendVistedId)
    var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO;
    var userSelected = userDao.findUserById(lastFriendVistedId).getOrElse { null }
    Ok(views.html.albumes(albumes, userSelected, albumDao, currentUser))
  }

  def showAmigos(userId: Long) = Action {
    lastFriendVistedId = userId;
    Redirect("/amigosPag")
  }

  def showAmigosPag = Action {
    var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO;
    var userSelected = userDao.findUserById(lastFriendVistedId).getOrElse { null }
    userSelected.setAmistades(userDao.findFriendsByUser(userSelected.getId))
    Ok(views.html.amigos(userSelected, currentUser))
  }

  def showAlbumContent(albumId: Long) = Action {
    Redirect("/albumContentPag")
  }

  def showAlbumContentPag = Action {
    Ok(views.html.carrusel())
  }

  def updateRegisteredUser(option: Int, attribute: String, userId: Long) = Action {
    var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO;

    if (option == 1) {
      userDao.updateUserNames(attribute, userId)
      currentUser.setPrimerNombre(attribute)
    }
    if (option == 2) {
      userDao.updateUserLastnames(attribute, userId)
      currentUser.setPrimerApellido(attribute)
    }
    if (option == 3) {
      userDao.updateUserNickname(attribute, userId)
      currentUser.setUsername(attribute)
    }
    if (option == 4) {
      userDao.updateUserEmail(attribute, userId)
      currentUser.setEmail(attribute)
    }

    Ok("true")
  }

  def createAlbum() = Action {
    Ok(views.html.crearAlbum(currentUser))
  }

  def deleteAlbum(albumId: Long) = Action {
    var albumDao: AlbumDAO = DAOFabrica.getAlbumDAO
    albumDao.deleteAlbumById(albumId)
    Redirect("/albumesPag")
  }

  def insertAlbum(albumName: String, albumDescription: String, albumPrivacy: Int, albumImgRoute: String) = Action {

    var album: Album = new Album(albumName, albumPrivacy, "assets/images/" + albumImgRoute, Some(albumDescription), currentUser.getId)
    var albumDao: AlbumDAO = DAOFabrica.getAlbumDAO
    albumDao.insertAlbum(album)

    Ok("true");
  }
  
  def searchForFriends(nameUserPattern: String) = Action{
    
        var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO;
        var users: List[Usuario] = userDao.searchUsersByFullNamePattern(nameUserPattern)
        Ok("true");
  }

}