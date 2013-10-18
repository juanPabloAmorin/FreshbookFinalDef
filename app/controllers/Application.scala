package controllers

import play.api._
import play.api.mvc._

import play.api.data._
import play.api.data.Forms._
import play.api.data.validation.Constraints._
import routes.javascript._

import views._
import models._

import anorm._
import anorm.SqlParser._


object Application extends Controller {
  
  var currentUser: Usuario = null;
  var lastFriendVistedId: Long = 0;
  
  def index = Action {
    Ok(views.html.index()).withNewSession
  }
  
  def autenticationRegisteredUser(username: String,pass: String) = Action 
  {
      var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO;
      currentUser = userDao.findUserByLog(username, pass).getOrElse{null}
      lastFriendVistedId = currentUser.getId
      userDao.findUserByLog(username,pass).isEmpty match {
        case true => Ok("false")
        case false => Ok("true").withSession(
                    "usuario" -> username
          )
      }
    
  }
  
  def showPrincipalPage = Action
  {
    request => request.session.get("usuario").map { usuario =>
            Ok(html.principal(currentUser))
    }.getOrElse 
    {
       Ok(views.html.index()).withNewSession
    }
  }
  
  def showPerfil(userId :Long) = Action
  {
      lastFriendVistedId = userId;
      Redirect("/perfilPag")
  }
  
  def showPerfilPag = Action
  {
      var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO;
      var userSelected = userDao.findUserById(lastFriendVistedId).getOrElse{null}
      Ok(views.html.perfil(userSelected,currentUser))
  }
  
  
  def showAlbumes(userId :Long) = Action
  {
      lastFriendVistedId = userId;
      Redirect("/albumesPag")
  } 
  
  def showAlbumesPag = Action
  {   
      var albumDao: AlbumDAO = DAOFabrica.getAlbumDAO;
      var albumes = albumDao.findAlbumsByUser(lastFriendVistedId) 
      var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO;
      var userSelected = userDao.findUserById(lastFriendVistedId).getOrElse{null}
      Ok(views.html.albumes(albumes,userSelected,albumDao,currentUser))
  }
  
  def showAmigos(userId :Long) = Action
  {
      lastFriendVistedId = userId;
      Redirect("/amigosPag")
  } 
  
  def showAmigosPag = Action
  {
      var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO;
      var userSelected = userDao.findUserById(lastFriendVistedId).getOrElse{null}
      userSelected.setAmistades(userDao.findFriendsByUser(userSelected.getId))
      Ok(views.html.amigos(userSelected,currentUser))
  }
  
  def showAlbumContent(albumId :Long) = Action
  {
      Redirect("/albumContentPag")
  } 
  
   def showAlbumContentPag = Action
   {
      Ok(views.html.carrusel()) 
   }
   
   
   def updateRegisteredUser(option: Int,attribute: String,userId: Long) = Action
   {
         var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO;
         
         if(option == 1)
         {
             userDao.updateUserNames(attribute,userId)
             currentUser.setNombres(attribute)
         }
         if(option == 2)
         {
             userDao.updateUserLastnames(attribute,userId)
             currentUser.setApellidos(attribute)
         }
         if(option == 3)
         {
             userDao.updateUserNickname(attribute,userId)
             currentUser.setUsername(attribute)
         }
         if(option == 4)
         {
             userDao.updateUserEmail(attribute,userId)
             currentUser.setEmail(attribute)
         }
         
         Ok("true")
   }
   
   def createAlbum() = Action
   {
         Ok(views.html.crearAlbum(currentUser))
   }
   
   def deleteAlbum(albumId: Long) = Action
   {
       var albumDao: AlbumDAO = DAOFabrica.getAlbumDAO
       albumDao.deleteAlbumById(albumId)
       Redirect("/albumesPag")
   }
   
   def insertAlbum(albumName: String,albumDescription: String,albumPrivacy: Int,albumImgRoute: String)= Action {
      
      var album: Album = new Album(albumName,albumPrivacy,"assets/images/"+albumImgRoute,Some(albumDescription),currentUser.getId)
      var albumDao: AlbumDAO = DAOFabrica.getAlbumDAO
      albumDao.insertAlbum(album)
       
       Ok("true");
   }
  
  

}