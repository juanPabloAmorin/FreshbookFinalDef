import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import play.api.test._
import play.api.test.Helpers._
import models._
import play.api._
import java.util.Date


@RunWith(classOf[JUnitRunner])
class UsuarioTest extends Specification {
  
 
  //after-init
  var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO;
  var user: Usuario = new Usuario();
  
  abstract class WithDbData extends WithApplication() {
      def setupData() {}
  }
  
  //test
  
  "Usuario class methods" should {
  
    "findUserByEmail with not existing user must return null" in new WithDbData {     
         user = userDao.findUserByEmail("otro@nulo.com").getOrElse(null) 
         user mustEqual(null)
     } 
    
    "findUserByEmail with existing user must not return null" in new WithDbData {     
         user = userDao.findUserByEmail("juan_amorin@gmail.com").getOrElse(null) 
         user mustNotEqual(null)
     }
    
    "updateUserFirstName with invalid name must throw an exception" in new WithDbData {
      
          user = userDao.findUserById(1).getOrElse(null)
          val newName: String = "Pedrooooooooooooooooooooooooooooooooooooooooooooooooo"
            
          user mustNotEqual(null)  
          
          try{
              userDao.updateUserFirstName(newName,user.getId) must throwA[DAOException]
          }
          catch {          
             case e: DAOException =>
             Logger.info("No es posible modificar el primer nombre del usuario " + 1)
             throw BusinessException.create("No es posible modificar el primer nombre", e)

          }
          
          
         
    }
    
    "updateUserFirstName with no name must throw an exception" in new WithDbData {
      
          user = userDao.findUserById(1).getOrElse(null)
          val newName: String = null
            
          user mustNotEqual(null)  
          
          try{
              userDao.updateUserFirstName(newName,user.getId) must throwA[DAOException]
          }
          catch {          
             case e: DAOException =>
             Logger.info("No es posible modificar el primer nombre del usuario " + 1)
             throw BusinessException.create("No es posible modificar el primer nombre", e)

          }
          
          
         
    }
    
    "updateUserFirstName with valid name must be right" in new WithDbData {
      
          user = userDao.findUserById(1).getOrElse(null)
          val newName: String = "Pedro"
            
          user mustNotEqual(null) 
          
          try {
              beRight(userDao.updateUserFirstName(newName,user.getId))
          }
          catch {          
             case e: DAOException =>
             Logger.info("No es posible modificar el primer nombre del usuario " + 1)
             throw BusinessException.create("No es posible modificar el primer nombre", e)

          }
          
          
         
    }
    
    "updateUserFirstName with invalid user must throw an exception" in new WithDbData {
      
          user = userDao.findUserById(200).getOrElse(null)
          val newName: String = "Pedro"
            
          user mustEqual(null) 
          
          try {
              userDao.updateUserFirstName(newName,user.getId) must throwA[NullPointerException]
          }
          catch {          
             case e: NullPointerException =>
             Logger.info("El usuario no existe")
             

          }
          
          
         
    }
    
    
    "updateUserSecondName with invalid second name must throw an exception" in new WithDbData {
      
          user = userDao.findUserById(1).getOrElse(null)
          val newName: String = "Juanitooooooooooooooooooooooooooooooooooooooooooooooooooooooooo"
            
          user mustNotEqual(null) 
          
          try{
              userDao.updateUserSecondName(newName,user.getId) must throwA[DAOException]
          }
          catch {          
             case e: DAOException =>
             Logger.info("No es posible modificar el segundo nombre del usuario " + 1)
             throw BusinessException.create("No es posible modificar el segundo nombre", e)

          }
          
          
         
    }
    
    "updateUserSecondName with valid name must be right" in new WithDbData {
      
          user = userDao.findUserById(1).getOrElse(null)
          val newName: String = "Juanito"
            
          user mustNotEqual(null) 
          
          try {
              beRight(userDao.updateUserSecondName(newName,user.getId))
          }
          catch {          
             case e: DAOException =>
             Logger.info("No es posible modificar el segundo nombre del usuario " + 1)
             throw BusinessException.create("No es posible modificar el segundo nombre", e)

          }
          
          
         
    }
    
    "updateUserSecondName with no name must be right" in new WithDbData {
      
          user = userDao.findUserById(1).getOrElse(null)
          val newName: String = null
            
          user mustNotEqual(null) 
          
          try {
              beRight(userDao.updateUserSecondName(newName,user.getId))
          }
          catch {          
             case e: DAOException =>
             Logger.info("No es posible modificar el segundo nombre del usuario " + 1)
             throw BusinessException.create("No es posible modificar el segundo nombre", e)

          }
          
          
         
    }
    
    "updateUserSecondName with invalid user must throw an exception" in new WithDbData {
      
          user = userDao.findUserById(200).getOrElse(null)
          val newName: String = "Juanito"
            
          user mustEqual(null) 
          
          try {
              userDao.updateUserSecondName(newName,user.getId) must throwA[NullPointerException]
          }
          catch {          
             case e: NullPointerException =>
             Logger.info("NO existe el usuario")
          
          }
          
          
         
    }
    
    "updateUserFirstLastname with invalid lastname must throw an exception" in new WithDbData {
      
          user = userDao.findUserById(1).getOrElse(null)
          val newLastname: String = "Pereeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeez"
            
          user mustNotEqual(null) 
          
          try{
              userDao.updateUserFirstLastname(newLastname,user.getId) must throwA[DAOException]
          }
          catch {          
             case e: DAOException =>
             Logger.info("No es posible modificar el primer apellido del usuario " + 1)
             throw BusinessException.create("No es posible modificar el primer apellido", e)

          }
          
          
         
    }
    
    "updateUserFirstLastname with valid lastname must be right" in new WithDbData {
      
          user = userDao.findUserById(1).getOrElse(null)
          val newLastname: String = "Perez"
            
          user mustNotEqual(null) 
          
          try {
              beRight(userDao.updateUserFirstLastname(newLastname,user.getId))
          }
          catch {          
             case e: DAOException =>
             Logger.info("No es posible modificar el primer apellido del usuario " + 1)
             throw BusinessException.create("No es posible modificar el primer apellido", e)

          }
          
          
         
    }
    
    "updateUserFirstLastname with invalid user must throw an exception" in new WithDbData {
      
          user = userDao.findUserById(200).getOrElse(null)
          val newLastname: String = "Perez"
            
          user mustEqual(null) 
          
          try {
              userDao.updateUserFirstLastname(newLastname,user.getId) must throwA[NullPointerException]
          }
          catch {          
             case e: NullPointerException =>
             Logger.info("NO existe el usuario")
          
          }
          
          
         
    }
    
    "updateUserFirstLastname with no name must throw an exception" in new WithDbData {
      
          user = userDao.findUserById(1).getOrElse(null)
          val newLastname: String = null
            
          user mustNotEqual(null)  
          
          try{
              userDao.updateUserFirstLastname(newLastname,user.getId) must throwA[DAOException]
          }
          catch {          
             case e: DAOException =>
             Logger.info("No es posible modificar el primer apellido del usuario " + 1)
             throw BusinessException.create("No es posible modificar el primer apellido", e)

          }
          
          
         
    }
    
    
    "updateUserSecondLastname with invalid lastname must throw an exception" in new WithDbData {
      
          user = userDao.findUserById(1).getOrElse(null)
          val newLastname: String = "Martineeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeez"
            
          user mustNotEqual(null) 
          
          try{
              userDao.updateUserSecondLastname(newLastname,user.getId) must throwA[DAOException]
          }
          catch {          
             case e: DAOException =>
             Logger.info("No es posible modificar el segundo apellido del usuario " + 1)
             throw BusinessException.create("No es posible modificar el segundo apellido ", e)

          }
          
          
         
    }
    
    "updateUserSecondLastname with valid lastname must be right" in new WithDbData {
      
          user = userDao.findUserById(1).getOrElse(null)
          val newLastname: String = "Martinez"
            
          user mustNotEqual(null) 
          
          try {
              beRight(userDao.updateUserSecondLastname(newLastname,user.getId))
          }
          catch {          
             case e: DAOException =>
             Logger.info("No es posible modificar el segundo apellido del usuario " + 1)
             throw BusinessException.create("No es posible modificar el segundo apellido", e)

          }
          
          
         
    }
    
    "updateUserSecondLastname with invalid user must throw an exception" in new WithDbData {
      
          user = userDao.findUserById(200).getOrElse(null)
          val newLastname: String = "Martinez"
            
          user mustEqual(null) 
          
          try {
              userDao.updateUserSecondLastname(newLastname,user.getId) must throwA[NullPointerException]
          }
          catch {          
             case e: NullPointerException =>
             Logger.info("NO existe el usuario")
          
          }
          
          
         
    }
    
    
    "updateUserSecondLastname with no name must be right" in new WithDbData {
      
          user = userDao.findUserById(1).getOrElse(null)
          val newLastname: String = null
            
          user mustNotEqual(null) 
          
          try {
              beRight(userDao.updateUserSecondLastname(newLastname,user.getId))
          }
          catch {          
             case e: DAOException =>
             Logger.info("No es posible modificar el segundo apellido del usuario " + 1)
             throw BusinessException.create("No es posible modificar el segundo apellido", e)

          }
          
          
         
    }
    
    
    "updateUserNickname with invalid nickname must throw an exception" in new WithDbData {
      
          user = userDao.findUserById(1).getOrElse(null)
          val newNickname: String = "The Big Booooooooooooooooooooooooooooooooooooooooooooooooosss"
            
          user mustNotEqual(null) 
          
          try{
              userDao.updateUserNickname(newNickname,user.getId) must throwA[DAOException]
          }
          catch {          
             case e: DAOException =>
             Logger.info("No es posible modificar el nickname del usuario " + 1)
             throw BusinessException.create("No es posible modificar el nickname", e)

          }
          
          
         
    }
    
    "updateUserNickname with valid lastname must be right" in new WithDbData {
      
          user = userDao.findUserById(1).getOrElse(null)
          val newNickname: String = "The Big Boss"
            
          user mustNotEqual(null) 
          
          try {
              beRight(userDao.updateUserNickname(newNickname,user.getId))
          }
          catch {          
             case e: DAOException =>
             Logger.info("No es posible modificar el nickname del usuario " + 1)
             throw BusinessException.create("No es posible modificar el nickname", e)

          }
          
          
         
    }
    
    "updateUserNickname with invalid user must throw an exception" in new WithDbData {
      
          user = userDao.findUserById(200).getOrElse(null)
          val newNickname: String = "The Big Boss"
            
          user mustEqual(null) 
          
          try {
              userDao.updateUserNickname(newNickname,user.getId) must throwA[NullPointerException]
          }
          catch {          
             case e: NullPointerException =>
             Logger.info("NO existe el usuario")
          
          }
          
          
         
    }
    
    "updateUserNickname with no name must throw an exception" in new WithDbData {
      
          user = userDao.findUserById(1).getOrElse(null)
          val newNickname: String = null
            
          user mustNotEqual(null)  
          
          try{
              userDao.updateUserNickname(newNickname,user.getId) must throwA[DAOException]
          }
          catch {          
             case e: DAOException =>
             Logger.info("No es posible modificar el nickname " + 1)
             throw BusinessException.create("No es posible modificar el nickname", e)

          }
          
          
         
    }
    
    
    "updateUserGoogle with invalid value must throw an exception" in new WithDbData {
      
          user = userDao.findUserById(1).getOrElse(null)
          val newGoogle: String = "http://algunlinkdepruebaquesobrepasaloscaracterespermitidos"
            
          user mustNotEqual(null) 
          
          try{
              userDao.updateUserGoogle(newGoogle,user.getId) must throwA[DAOException]
          }
          catch {          
             case e: DAOException =>
             Logger.info("No es posible modificar el enlace google del usuario " + 1)
             throw BusinessException.create("No es posible modificar el enlace google ", e)

          }
          
          
         
    }
    
    "updateUserGoogle with valid value must be right" in new WithDbData {
      
          user = userDao.findUserById(1).getOrElse(null)
          val newGoogle: String = "http://enlace-valido-a-google"
            
          user mustNotEqual(null) 
          
          try {
              beRight(userDao.updateUserGoogle(newGoogle,user.getId))
          }
          catch {          
             case e: DAOException =>
             Logger.info("No es posible modificar el enlace a google del usuario " + 1)
             throw BusinessException.create("No es posible modificar el enlace a google", e)

          }
          
          
         
    }
    
    "updateUserGoogle with invalid user must throw an exception" in new WithDbData {
      
          user = userDao.findUserById(200).getOrElse(null)
          val newGoogle: String = "http://enlace-valido-a-google"
            
          user mustEqual(null) 
          
          try {
              userDao.updateUserGoogle(newGoogle,user.getId) must throwA[NullPointerException]
          }
          catch {          
             case e: NullPointerException =>
             Logger.info("NO existe el usuario")
          
          }
          
          
         
    }
    
    
    "updateUserGoogle with no value must be right" in new WithDbData {
      
          user = userDao.findUserById(1).getOrElse(null)
          val newGoogle: String = null
            
          user mustNotEqual(null) 
          
          try {
              beRight(userDao.updateUserGoogle(newGoogle,user.getId))
          }
          catch {          
             case e: DAOException =>
             Logger.info("No es posible modificar el enlace a google del usuario " + 1)
             throw BusinessException.create("No es posible modificar el enlace a google", e)

          }
          
          
         
    }
    
    "updateUserFacebook with invalid value must throw an exception" in new WithDbData {
      
          user = userDao.findUserById(1).getOrElse(null)
          val newFacebook: String = "http://algunlinkdepruebaquesobrepasaloscaracterespermitidos"
            
          user mustNotEqual(null) 
          
          try{
              userDao.updateUserFacebook(newFacebook,user.getId) must throwA[DAOException]
          }
          catch {          
             case e: DAOException =>
             Logger.info("No es posible modificar el enlace Facebook del usuario " + 1)
             throw BusinessException.create("No es posible modificar el enlace Facebook ", e)

          }
          
          
         
    }
    
    "updateUserFacebook with valid value must be right" in new WithDbData {
      
          user = userDao.findUserById(1).getOrElse(null)
          val newFacebook: String = "http://enlace-valido-a-Facebook"
            
          user mustNotEqual(null) 
          
          try {
              beRight(userDao.updateUserFacebook(newFacebook,user.getId))
          }
          catch {          
             case e: DAOException =>
             Logger.info("No es posible modificar el enlace a Facebook del usuario " + 1)
             throw BusinessException.create("No es posible modificar el enlace a Facebook", e)

          }
          
          
         
    }
    
    "updateUserFacebook with invalid user must throw an exception" in new WithDbData {
      
          user = userDao.findUserById(200).getOrElse(null)
          val newFacebook: String = "http://enlace-valido-a-facebook"
            
          user mustEqual(null) 
          
          try {
              userDao.updateUserFacebook(newFacebook,user.getId) must throwA[NullPointerException]
          }
          catch {          
             case e: NullPointerException =>
             Logger.info("NO existe el usuario")
          
          }
          
          
         
    }
    
    
    "updateUserFacebook with no value must be right" in new WithDbData {
      
          user = userDao.findUserById(1).getOrElse(null)
          val newFacebook: String = null
            
          user mustNotEqual(null) 
          
          try {
              beRight(userDao.updateUserFacebook(newFacebook,user.getId))
          }
          catch {          
             case e: DAOException =>
             Logger.info("No es posible modificar el enlace a Facebook del usuario " + 1)
             throw BusinessException.create("No es posible modificar el enlace a Facebook", e)

          }
          
          
         
    }
    
    
    "updateUserTwitter with invalid value must throw an exception" in new WithDbData {
      
          user = userDao.findUserById(1).getOrElse(null)
          val newTwitter: String = "http://algunlinkdepruebaquesobrepasaloscaracterespermitidos"
            
          user mustNotEqual(null) 
          
          try{
              userDao.updateUserTwitter(newTwitter,user.getId) must throwA[DAOException]
          }
          catch {          
             case e: DAOException =>
             Logger.info("No es posible modificar el enlace Twitter del usuario " + 1)
             throw BusinessException.create("No es posible modificar el enlace Twitter ", e)

          }
          
          
         
    }
    
    "updateUserTwitter with valid value must be right" in new WithDbData {
      
          user = userDao.findUserById(1).getOrElse(null)
          val newTwitter: String = "http://enlace-valido-a-Twitter"
            
          user mustNotEqual(null) 
          
          try {
              beRight(userDao.updateUserTwitter(newTwitter,user.getId))
          }
          catch {          
             case e: DAOException =>
             Logger.info("No es posible modificar el enlace a Twitter del usuario " + 1)
             throw BusinessException.create("No es posible modificar el enlace a Twitter", e)

          }
          
          
         
    }
    
    "updateUserTwitter with invalid user must throw an exception" in new WithDbData {
      
          user = userDao.findUserById(200).getOrElse(null)
          val newTwitter: String = "http://enlace-valido-a-Twitter"
            
          user mustEqual(null) 
          
          try {
              userDao.updateUserTwitter(newTwitter,user.getId) must throwA[NullPointerException]
          }
          catch {          
             case e: NullPointerException =>
             Logger.info("NO existe el usuario")
          
          }
          
          
         
    }
    
    
    "updateUserTwitter with no value must be right" in new WithDbData {
      
          user = userDao.findUserById(1).getOrElse(null)
          val newTwitter: String = null
            
          user mustNotEqual(null) 
          
          try {
              beRight(userDao.updateUserTwitter(newTwitter,user.getId))
          }
          catch {          
             case e: DAOException =>
             Logger.info("No es posible modificar el enlace a Twitter del usuario " + 1)
             throw BusinessException.create("No es posible modificar el enlace a Twitter", e)

          }
          
          
         
    }
    
    "updateUserLocation with not exist lugar must throw an exception" in new WithDbData {
      
          user = userDao.findUserById(1).getOrElse(null)
          val newFkLugar: Long = 100
            
          user mustNotEqual(null) 
          
          try {
              userDao.updateUserLocation(newFkLugar,user.getId) must throwA[DAOException]
          }
          catch {          
             case e: DAOException =>
             Logger.info("No es posible modificar la ubicacion del usuario " + 1)
             throw BusinessException.create("No es posible modificar la ubicacion", e)

          }
          
          
         
    }
    
    "updateUserLocation with invalid user must throw an exception" in new WithDbData {
      
          user = userDao.findUserById(200).getOrElse(null)
          val newFkLugar: Long = 1
            
          user mustEqual(null) 
          
          try {
              userDao.updateUserLocation(newFkLugar,user.getId) must throwA[NullPointerException]
          }
          catch {          
             case e: NullPointerException =>
             Logger.info("NO existe el usuario")
          
          }
          
          
         
    }
    
    "updateUserLocation with exist lugar must be right" in new WithDbData {
      
          user = userDao.findUserById(1).getOrElse(null)
          val newFkLugar: Long = 1
            
          user mustNotEqual(null) 
          
          try {
              beRight(userDao.updateUserLocation(newFkLugar,user.getId))
          }
          catch {          
             case e: DAOException =>
             Logger.info("No es posible modificar la ubicacion de l usuario " + 1)
             throw BusinessException.create("No es posible modificar la ubicacion", e)

          }
          
          
         
    }
    
    "updateUserLocation with no fkLugar must throw an exception" in new WithDbData {
      
          user = userDao.findUserById(1).getOrElse(null)
          val newFkLugar: Long = 0
            
          user mustNotEqual(null) 
          
          try {
              userDao.updateUserLocation(newFkLugar,user.getId) must throwA[DAOException]
          }
          catch {          
             case e: DAOException =>
             Logger.info("No es posible modificar la ubicacion del usuario " + 1)
             throw BusinessException.create("No es posible modificar la ubicacion", e)
          
          }
          
          
         
    }
    
     "updateUserLatitud with invalid value must throw an exception" in new WithDbData {
      
          user = userDao.findUserById(1).getOrElse(null)
          val newLatitud: String = "-982749263876238764539499999999999999999999233424242345"
            
          user mustNotEqual(null) 
          
          try{
              userDao.updateUserLatitud(newLatitud,user.getId) must throwA[DAOException]
          }
          catch {          
             case e: DAOException =>
             Logger.info("No es posible modificar el enlace Latitud del usuario " + 1)
             throw BusinessException.create("No es posible modificar el enlace Latitud ", e)

          }
          
          
         
    }
    
    "updateUserLatitud with valid value must be right" in new WithDbData {
      
          user = userDao.findUserById(1).getOrElse(null)
          val newLatitud: String = "-99384345"
            
          user mustNotEqual(null) 
          
          try {
              beRight(userDao.updateUserLatitud(newLatitud,user.getId))
          }
          catch {          
             case e: DAOException =>
             Logger.info("No es posible modificar el enlace a Latitud del usuario " + 1)
             throw BusinessException.create("No es posible modificar el enlace a Latitud", e)

          }
          
          
         
    }
    
    "updateUserLatitud with invalid user must throw an exception" in new WithDbData {
      
          user = userDao.findUserById(200).getOrElse(null)
          val newLatitud: String = "2347872634"
            
          user mustEqual(null) 
          
          try {
              userDao.updateUserLatitud(newLatitud,user.getId) must throwA[NullPointerException]
          }
          catch {          
             case e: NullPointerException =>
             Logger.info("NO existe el usuario")
          
          }
          
          
         
    }
    
    
    "updateUserLatitud with no value must be right" in new WithDbData {
      
          user = userDao.findUserById(1).getOrElse(null)
          val newLatitud: String = null
            
          user mustNotEqual(null) 
          
          try {
              beRight(userDao.updateUserLatitud(newLatitud,user.getId))
          }
          catch {          
             case e: DAOException =>
             Logger.info("No es posible modificar el enlace a Latitud del usuario " + 1)
             throw BusinessException.create("No es posible modificar el enlace a Latitud", e)

          }
                   
         
    }
    
     "updateUserLongitud with invalid value must throw an exception" in new WithDbData {
      
          user = userDao.findUserById(1).getOrElse(null)
          val newLongitud: String = "-982749263876238764539499999999999999999999233424242345"
            
          user mustNotEqual(null) 
          
          try{
              userDao.updateUserLongitud(newLongitud,user.getId) must throwA[DAOException]
          }
          catch {          
             case e: DAOException =>
             Logger.info("No es posible modificar el enlace Longitud del usuario " + 1)
             throw BusinessException.create("No es posible modificar el enlace Longitud ", e)

          }
          
          
         
    }
    
    "updateUserLongitud with valid value must be right" in new WithDbData {
      
          user = userDao.findUserById(1).getOrElse(null)
          val newLongitud: String = "-99384345"
            
          user mustNotEqual(null) 
          
          try {
              beRight(userDao.updateUserLongitud(newLongitud,user.getId))
          }
          catch {          
             case e: DAOException =>
             Logger.info("No es posible modificar el enlace a Longitud del usuario " + 1)
             throw BusinessException.create("No es posible modificar el enlace a Longitud", e)

          }
          
          
         
    }
    
    "updateUserLongitud with invalid user must throw an exception" in new WithDbData {
      
          user = userDao.findUserById(200).getOrElse(null)
          val newLongitud: String = "2347872634"
            
          user mustEqual(null) 
          
          try {
              userDao.updateUserLongitud(newLongitud,user.getId) must throwA[NullPointerException]
          }
          catch {          
             case e: NullPointerException =>
             Logger.info("NO existe el usuario")
          
          }
          
          
         
    }
    
    
    "updateUserLongitud with no value must be right" in new WithDbData {
      
          user = userDao.findUserById(1).getOrElse(null)
          val newLongitud: String = null
            
          user mustNotEqual(null) 
          
          try {
              beRight(userDao.updateUserLongitud(newLongitud,user.getId))
          }
          catch {          
             case e: DAOException =>
             Logger.info("No es posible modificar el enlace a Longitud del usuario " + 1)
             throw BusinessException.create("No es posible modificar el enlace a Longitud", e)

          }
                   
         
    }
    
    "findUserById with not existing user must return null" in new WithDbData {     
         user = userDao.findUserById(200).getOrElse(null) 
         user mustEqual(null)
     } 
    
    "findUserById with existing user must not return null" in new WithDbData {     
         user = userDao.findUserById(1).getOrElse(null) 
         user mustNotEqual(null)
     }
    
    
    "findFriendsByUser with invalid user must be empty" in new WithDbData {
      
          var friendList: List[Usuario] = null;
      
          try {
              friendList = userDao.findFriendsByUser(200)
              
              friendList must be empty
          }
          catch {          
             case e: DAOException =>
             Logger.info("NO existe el usuario")
          
          }
          
          
         
    }
    
    "findFriendsByUser with valid user must be right" in new WithDbData {
      
          var friendList: List[Usuario] = null;
      
          try {
              beRight(friendList = userDao.findFriendsByUser(1))
          }
          catch {          
             case e: DAOException =>
             Logger.info("NO existe el usuario")
          
          }
          
          
         
    }
    
    "findFriendsByUser with valid user must be right" in new WithDbData {
      
          var friendList: List[Usuario] = null;
      
          try {
              beRight(friendList = userDao.findFriendsByUser(4))
              
              friendList mustEqual null
          }
          catch {          
             case e: DAOException =>
             Logger.info("NO existe el usuario")
          
          }
                  
    }
    
  
  }

 
}
  
  
