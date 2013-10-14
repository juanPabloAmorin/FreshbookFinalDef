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
  var friendId: Long = 0;
  
  def index = Action {
    Ok(views.html.index()).withNewSession
  }
  
  def autenticationRegisteredUser(username: String,pass: String) = Action 
  {
      var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO;
      currentUser = userDao.findByLog(username, pass).getOrElse{null}
      friendId = currentUser.getId
      userDao.findByLog(username,pass).isEmpty match {
        case true => Ok("false")
        case false => Ok("true").withSession(
                    "usuario" -> username
          )
      }
    
  }
  
  def showPrincipal = Action
  {
    request => request.session.get("usuario").map { usuario =>
            Ok(html.principal(currentUser))
    }.getOrElse 
    {
       Ok(views.html.index()).withNewSession
    }
  }
  
  def showPerfil(id :Long) = Action
  {
      friendId = id;
      Redirect("/perfilPag")
  }
  
  def showPerfilPag = Action
  {
      var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO;
      var userSelected = userDao.findUserById(friendId).getOrElse{null}
      Ok(views.html.perfil(userSelected))
  }
  
  
  def showAlbumes(id :Long) = Action
  {
      friendId = id;
      Redirect("/albumesPag")
  } 
  
  def showAlbumesPag = Action
  {   
      var albumDao: AlbumDAO = DAOFabrica.getAlbumDAO;
      var albumes = albumDao.findAlbumsByUser(friendId) 
      var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO;
      var userSelected = userDao.findUserById(friendId).getOrElse{null}
      Ok(views.html.albumes(albumes,userSelected,albumDao))
  }
  
  def showAmigos(id :Long) = Action
  {
      friendId = id;
      Redirect("/amigosPag")
  } 
  
  def showAmigosPag = Action
  {
      var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO;
      var userSelected = userDao.findUserById(friendId).getOrElse{null}
      userSelected.setAmistades(userDao.findFriendsByUser(userSelected.getId))
      Ok(views.html.amigos(userSelected))
  }
  
  def showAlbumContent(id :Long) = Action
  {
      Redirect("/albumContentPag")
  } 
  
   def showAlbumContentPag = Action
   {
      Ok(views.html.carrusel()) 
   }
   
   
   def updateRegisteredUser(option: Int,attr: String,id: Long) = Action
   {
         var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO;
         
         if(option == 1)
         {
             userDao.updateUserNames(attr,id)
             currentUser.setNombres(attr)
         }
         if(option == 2)
         {
             userDao.updateUserLastnames(attr,id)
             currentUser.setApellidos(attr)
         }
         if(option == 3)
         {
             userDao.updateUserNickname(attr,id)
             currentUser.setUsername(attr)
         }
         if(option == 4)
         {
             userDao.updateUserEmail(attr,id)
             currentUser.setEmail(attr)
         }
         
         Ok("true")
   }
   
   def createAlbum() = Action
   {
         Ok(views.html.crearAlbum(currentUser))
   }
  
  

}