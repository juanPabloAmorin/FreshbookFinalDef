package controllers

import play.api._
import play.api.mvc._

import models._

object Util {

  def getUserLocation(country: String, state: String, city: String): Lugar = {
    
    var userLugar: Lugar = null
    var newState: Lugar = null
    var newCountry: Lugar = null
    var newCity: Lugar = null
    
    var lugarDao: LugarDAO = DAOFabrica.getLugarDAO;

    var foundCountry = lugarDao.getLugarByNameAndType(country, "pais")
   
    foundCountry match{
    
      case Some(countryValue) =>
      
        userLugar = countryValue
        var foundState = lugarDao.getLugarByNameAndType(state, "estado")
        
        foundState match{
          
          case Some(stateValue) =>
            
            userLugar = stateValue
            var foundCity = lugarDao.getLugarByNameAndType(city, "ciudad")
            
            foundCity match{
              
              case Some(cityValue) =>
                
                userLugar = cityValue
                
              case _ =>
                if(city != "null"){
                  
                    newCity = new Lugar(city, "ciudad", stateValue)
                    lugarDao.insertLugar(newCity)
                    userLugar = newCity
                }
            }
            
            
                  
          case _ => 
            
            if(state != "null"){
               newState = new Lugar(state, "estado", countryValue)
               lugarDao.insertLugar(newState)
               userLugar = newState
            }        
            if(city != "null"){
          
               newCity = new Lugar(city, "ciudad", newState)
               lugarDao.insertLugar(newCity)
               userLugar = newCity
            }
          
        }
       
      case _ =>
        
         newCountry = new Lugar(country, "pais", null)
         lugarDao.insertLugar(newCountry)
         userLugar = newCountry
         
         if(state != "null") {
            newState = new Lugar(state, "estado", newCountry)
            lugarDao.insertLugar(newState)         
            userLugar = newState
         }
         else if(city != "null"){
         
            newCity = new Lugar(city, "ciudad", newState)
            lugarDao.insertLugar(newCity)    
            userLugar = newCity
      }
        
        
    }   
        
       
    return userLugar
  }

}