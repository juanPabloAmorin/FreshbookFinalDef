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
        var foundState = lugarDao.getLugarByNameAndTypeAndZone(state, "estado",countryValue.getId)
        
        foundState match{
          
          case Some(stateValue) =>
            
            userLugar = stateValue
            var foundCity = lugarDao.getLugarByNameAndTypeAndZone(city, "ciudad",stateValue.getId)
            
            foundCity match{
              
              case Some(cityValue) =>
                
                userLugar = cityValue
                
              case _ =>
                if(city != "null"){
                  
                    newCity = new Lugar(city, "ciudad", Some(stateValue))
                    lugarDao.insertLugar(newCity)
                    newCity.setId(lugarDao.getNewLugarLastIdFromSequence)
                    userLugar = newCity
                }
            }
            
            
                  
          case _ => 
            
            if(state != "null"){
               newState = new Lugar(state, "estado", Some(countryValue))
               lugarDao.insertLugar(newState)
               newState.setId(lugarDao.getNewLugarLastIdFromSequence)
               userLugar = newState
            }        
            if(city != "null"){
          
               newCity = new Lugar(city, "ciudad", Some(newState))
               lugarDao.insertLugar(newCity)
               newCity.setId(lugarDao.getNewLugarLastIdFromSequence)
               userLugar = newCity
            }
          
        }
       
      case _ =>
        
         newCountry = new Lugar(country, "pais", null)
         lugarDao.insertLugar(newCountry)
         newCountry.setId(lugarDao.getNewLugarLastIdFromSequence)
         userLugar = newCountry
         
         if(state != "null") {
            newState = new Lugar(state, "estado", Some(newCountry))
            lugarDao.insertLugar(newState)   
            newState.setId(lugarDao.getNewLugarLastIdFromSequence)
            userLugar = newState
         }
         if(city != "null"){
         
            newCity = new Lugar(city, "ciudad", Some(newState))
            lugarDao.insertLugar(newCity) 
            newCity.setId(lugarDao.getNewLugarLastIdFromSequence)
            userLugar = newCity
      }
        
        
    }   
        
       
    return userLugar
  }

}