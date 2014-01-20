import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import play.api.test._
import play.api.test.Helpers._
import models._
import play.api._
import java.util.Date

@RunWith(classOf[JUnitRunner])
class ContenidoTest extends Specification {
  
 
  //before-init
  var contenidoDao: ContenidoMultimediaDAO = DAOFabrica.getContenidoMultimediaDAO;
  var contenido: ContenidoMultimedia = new ContenidoMultimedia();
  
  abstract class WithDbData extends WithApplication() {
      def setupData() {}
  }
  
    //test
    
    "Contenido class methods" should {
      
         "insertContenido with correct values" in new WithDbData{
         
             var stringDate = "05/12/2013"
             var date: Date = new Date(stringDate)
             var contenido: ContenidoMultimedia = new ContenidoMultimedia(20,"http://ruta-del-contenido",date,"INSTAGRAM","http://ruta-del-tumbnail","nombre-del-contenido",1)
             
             try
             {
                   beRight(contenidoDao.insertContenidoMultimedia(contenido))
             } 
             catch {
                 case e: DAOException =>
                 Logger.error("No se ha creado el contenido " + contenido.getNombre)
                 
             }
       }
         
       "insertContenido with wrong ruta must throw an exception" in new WithDbData{
         
             var stringDate = "05/12/2013"
             var date: Date = new Date(stringDate)
             var contenido: ContenidoMultimedia = new ContenidoMultimedia()
             
             contenido.setRuta( """
                                http://ruta-del-contenidooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
                                ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
                                ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
                                """)
             contenido.setRedSocial("INSTAGRAM")
             contenido.setRutaTumbnail("http://ruta-del-tumbnail")
             contenido.setNombre("nombre del contenido")
             contenido.setInAlbumId(1)
             
             try   
             {
                   contenidoDao.insertContenidoMultimedia(contenido) must throwA[Exception]
             } 
             catch {
                 case e: DAOException =>
                 Logger.error("No se ha creado el contenido " + contenido.getNombre)
                 
             }
       }
       
       "insertContenido with wrong rutaTumbnail must throw an exception" in new WithDbData{
         
             var stringDate = "05/12/2013"
             var date: Date = new Date(stringDate)
             var contenido: ContenidoMultimedia = new ContenidoMultimedia()
             
             contenido.setRuta( "http://ruta-del-contenido")
             contenido.setRedSocial("INSTAGRAM")
             contenido.setRutaTumbnail("""
                                       http://ruta-del-tumbnailooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
                                       ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
                                       ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
                                       """)
             contenido.setNombre("nombre del contenido")
             contenido.setInAlbumId(1)
             
             try   
             {
                   contenidoDao.insertContenidoMultimedia(contenido) must throwA[Exception]
             } 
             catch {
                 case e: DAOException =>
                 Logger.error("No se ha creado el contenido " + contenido.getNombre)
                 
             }
       }
       
       
       "insertContenido with wrong nombre must throw an exception" in new WithDbData{
         
             var stringDate = "05/12/2013"
             var date: Date = new Date(stringDate)
             var contenido: ContenidoMultimedia = new ContenidoMultimedia()
             
             contenido.setRuta( "http://ruta-del-contenido")
             contenido.setRedSocial("INSTAGRAM")
             contenido.setRutaTumbnail("http://ruta-del-contenido")
             contenido.setNombre("""
                                 nombre-del-contenidoooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
                                 ooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
                                 oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
                                 """)
             contenido.setInAlbumId(1)
             
             try   
             {
                   contenidoDao.insertContenidoMultimedia(contenido) must throwA[Exception]
             } 
             catch {
                 case e: DAOException =>
                 Logger.error("No se ha creado el contenido " + contenido.getNombre)
                 
             }
       }
       
       "insertContenido with wrong redSocial must throw an exception" in new WithDbData{
         
             var stringDate = "05/12/2013"
             var date: Date = new Date(stringDate)
             var contenido: ContenidoMultimedia = new ContenidoMultimedia()
             
             contenido.setRuta( "http://ruta-del-contenido")
             contenido.setRedSocial("INSTAGRAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAM")
             contenido.setRutaTumbnail("http://ruta-del-contenido")
             contenido.setNombre("nombre del conenido")
             contenido.setInAlbumId(1)
             
             try   
             {
                   contenidoDao.insertContenidoMultimedia(contenido) must throwA[Exception]
             } 
             catch {
                 case e: DAOException =>
                 Logger.error("No se ha creado el contenido " + contenido.getNombre)
                 
             }
       }
       
       "insertContenido with invalid um idalb must throw an exception" in new WithDbData{
         
             var stringDate = "05/12/2013"
             var date: Date = new Date(stringDate)
             var contenido: ContenidoMultimedia = new ContenidoMultimedia()
             
             contenido.setRuta( "http://ruta-del-contenido")
             contenido.setRedSocial("INSTAGRAM")
             contenido.setRutaTumbnail("http://ruta-del-contenido")
             contenido.setNombre("nombre del conenido")
             contenido.setInAlbumId(200)
             
             try   
             {
                   contenidoDao.insertContenidoMultimedia(contenido) must throwA[Exception]
             } 
             catch {
                 case e: DAOException =>
                 Logger.error("No se ha creado el contenido " + contenido.getNombre)
                 
             }
       }
       
       
       "deleteContent whit valid id must be right" in new WithDbData{
         
           try{
                beRight(contenidoDao.deleteContenidoMultimedia(1))
           }
           catch {
                 case e: DAOException =>
                 Logger.error("No se ha eliminado el contenido con id = " + 1)
                
             }
       }
    
    }
    
}