import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import play.api.test._
import play.api.test.Helpers._
import models._
import play.api._
import java.util.Date

@RunWith(classOf[JUnitRunner])
class LugarTest extends Specification {
  
 
  //before-init
  var lugar: Lugar = null;
  var lugarDao: LugarDAO = DAOFabrica.getLugarDAO;
  
  abstract class WithDbData extends WithApplication() {
      def setupData() {}
  }
  
    //test
    
    "Lugar class methods" should {
      
      "insertLugar with correct values" in new WithDbData{
        
             lugar = new Lugar(0,"Venezuela","Pais")
             
             try
             {
                   beRight(lugarDao.insertLugar(lugar))
             } 
             catch {
                 case e: DAOException =>
                 Logger.error("No se ha creado el lugar " + lugar.getNombre)
                 
             }
       }
      
      "insertLugar with wrong name must throw an exception" in new WithDbData{
        
             lugar = new Lugar(0,"Venezuelaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa","Pais")
             
             try
             {
                   lugarDao.insertLugar(lugar) must throwA[DAOException]
             } 
             catch {
                 case e: DAOException =>
                 Logger.error("No se ha creado el lugar " + lugar.getNombre)
                 
             }
       }
      
      "insertLugar with wrong tipo must throw an exception" in new WithDbData{
        
             lugar = new Lugar(0,"Venezuela","Paiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiis")
             
             try
             {
                   lugarDao.insertLugar(lugar) must throwA[DAOException]
             } 
             catch {
                 case e: DAOException =>
                 Logger.error("No se ha creado el lugar " + lugar.getNombre)
                 
             }
       }
      
      "insertLugar with invalid Lugar must throw an exception" in new WithDbData{
        
             var newLugar: Lugar = new Lugar(10,"Colombia","Pais")
             
             lugar = new Lugar("Venezuela","Pais",Some(newLugar))
             
             try
             {
                   lugarDao.insertLugar(lugar) must throwA[DAOException]
             } 
             catch {
                 case e: DAOException =>
                 Logger.error("No se ha creado el lugar " + lugar.getNombre)
                 
             }
       }
      
      
     "getAlbumById with not existing lugar must return null" in new WithDbData {     
         lugar = lugarDao.getLugarById(200).getOrElse(null) 
         lugar mustEqual(null)
     } 
    
     "findLugarById with existing lugar must not return null" in new WithDbData {     
         lugar = lugarDao.getLugarById(1).getOrElse(null) 
         lugar mustNotEqual(null)
     }
      
      
    }
}