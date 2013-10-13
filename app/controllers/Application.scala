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

}