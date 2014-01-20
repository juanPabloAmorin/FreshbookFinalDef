import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import play.api.test._
import play.api.test.Helpers._
import models._
import play.api._
import java.util.Date

@RunWith(classOf[JUnitRunner])
class AlbumTest extends Specification {
  
 
  //before-init
  var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO;
  var user: Usuario = new Usuario();
  var album: Album = new Album();
  var albumDao: AlbumDAO = DAOFabrica.getAlbumDAO;
  
  abstract class WithDbData extends WithApplication() {
      def setupData() {}
  }
  
    //test
    
    "Album class methods" should {
      
       "findAlbumsByUser with not exist user: friendList must be empty" in new WithDbData { 
         
           var albumList: List[Album] = null
           
           albumList = albumDao.findAlbumsByUser(200)
           
           albumList must be empty
           
       }
       
     
       "findAlbumsByUser with exist user and no album: friendList must be empty" in new WithDbData { 
         
           var albumList: List[Album] = null
           
           albumList = albumDao.findAlbumsByUser(4)
           
           albumList must be empty
           
       }
       
       "findAlbumsByUser with exist user and exist album: friendList must be not empty" in new WithDbData { 
         
           var albumList: List[Album] = null
           
           albumList = albumDao.findAlbumsByUser(1)
           
           albumList must not be empty
           
       }
       
       "getNumberOfFilesInAlbum whit exist album and exists content must be > 0" in new WithDbData{
         
            var numberFiles: Long = 0;
            var matche: Long = 0;
            
            numberFiles = albumDao.getNumberOfFilesInAlbum(1)
            
            numberFiles must beGreaterThan(matche)
       } 
       
       "getNumberOfFilesInAlbum whit exist album and no content must be = 0" in new WithDbData{
         
            var numberFiles: Long = 0;
            var matche: Long = 0;
            
            numberFiles = albumDao.getNumberOfFilesInAlbum(4)
            
            numberFiles mustEqual(matche)
       } 
       
       "getNumberOfFilesInAlbum whit not exist album must be = 0" in new WithDbData{
         
            var numberFiles: Long = 0;
            var matche: Long = 0;
            
            numberFiles = albumDao.getNumberOfFilesInAlbum(100)
            
            numberFiles mustEqual(matche)
       }
       
       "insertAlbum with correct values" in new WithDbData{
         
             var stringDate = "05/12/2013"
             var date: Date = new Date(stringDate)
             var album: Album = new Album(20,"new album",0,"imagen.jpg",date,null,1)
             
             try
             {
                   beRight(albumDao.insertAlbum(album))
             } 
             catch {
                 case e: DAOException =>
                 Logger.error("No se ha creado el album " + album.getNombre)
                 
             }
       }
       
       "insertAlbum with not exist user must throw an exception " in new WithDbData{
         
             var stringDate = "05/12/2013"
             var date: Date = new Date(stringDate)
             var album: Album = new Album()
             album.setNombre("new Album")
             album.setPrivacidad(0)
             album.setCaratula("foto.jpg")
             album.setFechaCreacion(date)
             album.setOwnerId(200)
             album.setDescripcion(Some("description"))
             
             try
             {
                   albumDao.insertAlbum(album) must throwA[DAOException]
             } 
             catch {
                 case e: DAOException =>
                 Logger.error("No se ha creado el album " + album.getNombre)
                
             }
       }
       
       "insertAlbum with wrong values must throw an exception " in new WithDbData{
         
             var stringDate = "05/12/2013"
             var date: Date = new Date(stringDate)
             var album: Album = new Album()
             album.setNombre("new Albummmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmmm")
             album.setPrivacidad(0234)
             album.setCaratula("fotooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo.jpg")
             album.setFechaCreacion(date)
             album.setOwnerId(200)
             album.setDescripcion(Some("description"))
             
             try
             {
                   albumDao.insertAlbum(album) must throwA[DAOException]
             } 
             catch {
                 case e: DAOException =>
                 Logger.error("No se ha creado el album " + album.getNombre)
                
             }
       }
       
       
       "deleteAlbum whit valid id must be right" in new WithDbData{
         
           try{
                beRight(albumDao.deleteAlbumById(1))
           }
           catch {
                 case e: DAOException =>
                 Logger.error("No se ha eliminado el album con id = " + 1)
                
             }
       }
       
       "getContenidoByAlbum with invalid album id" in new WithDbData{
         
           var albumContent: List[ContenidoMultimedia] = null
           
           albumContent = albumDao.getContenidoByAlbum(200)
           
           albumContent must be empty
       }
       
       "getContenidoByAlbum with valid album id" in new WithDbData{
         
           var albumContent: List[ContenidoMultimedia] = null
           
           albumContent = albumDao.getContenidoByAlbum(1)
           
           albumContent must not be empty
       }
       
       "getContenidoByAlbum of empty album" in new WithDbData{
         
           var albumContent: List[ContenidoMultimedia] = null
           
           albumContent = albumDao.getContenidoByAlbum(3)
           
           albumContent must be empty
       }
       
     "findAlbumById with not existing album must return null" in new WithDbData {     
         album = albumDao.findAlbumById(200).getOrElse(null) 
         album mustEqual(null)
     } 
    
     "findAlbumById with existing album must not return null" in new WithDbData {     
         album = albumDao.findAlbumById(1).getOrElse(null) 
         album mustNotEqual(null)
     }
     
  
     
     "updateAlbumName with invalid value must throw an exception" in new WithDbData {
      
          album = albumDao.findAlbumById(1).getOrElse(null)
          val newName: String = "nombreeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"
            
          album mustNotEqual(null) 
          
          try{
              albumDao.updateAlbumName(newName,album.getId) must throwA[DAOException]
          }
          catch {          
             case e: DAOException =>
             Logger.info("No es posible modificar el nombre del album " + 1)

          }
          
          
         
    }
    
    "updateAlbumNombre with valid value must be right" in new WithDbData {
      
          album = albumDao.findAlbumById(1).getOrElse(null)
          val newNombre: String = "nuvo album"
            
          album mustNotEqual(null) 
          
          try {
              beRight(albumDao.updateAlbumName(newNombre,album.getId))
          }
          catch {          
             case e: DAOException =>
             Logger.info("No es posible modificar el Nombre del album " + 1)

          }
          
          
         
    }
    
    "updateAlbumNombre with invalid album must throw an exception" in new WithDbData {
      
          album = albumDao.findAlbumById(200).getOrElse(null)
          val newNombre: String = "nuevo album"
            
          album mustEqual(null) 
          
          try {
              albumDao.updateAlbumName(newNombre,album.getId) must throwA[NullPointerException]
          }
          catch {          
             case e: NullPointerException =>
             Logger.info("NO existe el album")
          
          }
          
          
         
    }
    
    
    "updateAlbumNombre with no value must throw an exception" in new WithDbData {
      
          album = albumDao.findAlbumById(1).getOrElse(null)
          val newNombre: String = null
            
          album mustNotEqual(null) 
          
          try {
              albumDao.updateAlbumName(newNombre,album.getId) must throwA[DAOException]
          }
          catch {          
             case e: DAOException =>
             Logger.info("No es posible modificar el Nombre del album " + 1)
             

          }
          
          
         
    }
    
    "updateAlbumDescription with invalid value must throw an exception" in new WithDbData {
      
          album = albumDao.findAlbumById(1).getOrElse(null)
          val newDescription: String = "Descripcioneeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee"
            
          album mustNotEqual(null) 
          
          try{
              albumDao.updateAlbumDescription(newDescription,album.getId) must throwA[DAOException]
          }
          catch {          
             case e: DAOException =>
             Logger.info("No es posible modificar la Descripcion del album " + 1)

          }
          
          
         
    }
    
    "updateAlbumDescripcion with valid value must be right" in new WithDbData {
      
          album = albumDao.findAlbumById(1).getOrElse(null)
          val newDescripcion: String = "nuvo album"
            
          album mustNotEqual(null) 
          
          try {
              beRight(albumDao.updateAlbumDescription(newDescripcion,album.getId))
          }
          catch {          
             case e: DAOException =>
             Logger.info("No es posible modificar la Descripcion del album " + 1)

          }
          
          
         
    }
    
    "updateAlbumDescripcion with invalid album must throw an exception" in new WithDbData {
      
          album = albumDao.findAlbumById(200).getOrElse(null)
          val newDescripcion: String = "nuevo album"
            
          album mustEqual(null) 
          
          try {
              albumDao.updateAlbumDescription(newDescripcion,album.getId) must throwA[NullPointerException]
          }
          catch {          
             case e: NullPointerException =>
             Logger.info("NO existe el album")
          
          }
          
          
         
    }
    
    
    "updateAlbumDescripcion with no value must be right" in new WithDbData {
      
          album = albumDao.findAlbumById(1).getOrElse(null)
          val newDescripcion: String = null
            
          album mustNotEqual(null) 
          
          try {
              beRight(albumDao.updateAlbumDescription(newDescripcion,album.getId))
          }
          catch {          
             case e: DAOException =>
             Logger.info("No es posible modificar la Descripcion del album " + 1)

          }
          
          
         
    }
    
   
    
     "updateAlbumCaratula with invalid value must throw an exception" in new WithDbData {
      
          album = albumDao.findAlbumById(1).getOrElse(null)
          val newCaratula: String = """
                                    Caratulaeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
                                    eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
                                    eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
                                    """
            
          album mustNotEqual(null) 
          
          try{
              albumDao.updateAlbumCaratula(newCaratula,album.getId) must throwA[DAOException]
          }
          catch {          
             case e: DAOException =>
             Logger.info("No es posible modificar la Caratula del album " + 1)

          }
          
          
         
    }
    
    "updateAlbumCaratula with valid value must be right" in new WithDbData {
      
          album = albumDao.findAlbumById(1).getOrElse(null)
          val newCaratula: String = "nuvo album"
            
          album mustNotEqual(null) 
          
          try {
              beRight(albumDao.updateAlbumCaratula(newCaratula,album.getId))
          }
          catch {          
             case e: DAOException =>
             Logger.info("No es posible modificar la Caratula del album " + 1)

          }
          
          
         
    }
    
    "updateAlbumCaratula with invalid album must throw an exception" in new WithDbData {
      
          album = albumDao.findAlbumById(200).getOrElse(null)
          val newCaratula: String = "nuevo album"
            
          album mustEqual(null) 
          
          try {
              albumDao.updateAlbumCaratula(newCaratula,album.getId) must throwA[NullPointerException]
          }
          catch {          
             case e: NullPointerException =>
             Logger.info("NO existe el album")
          
          }
          
          
         
    }
    
    
    "updateAlbumCaratula with no value must throw an exception" in new WithDbData {
      
          album = albumDao.findAlbumById(1).getOrElse(null)
          val newCaratula: String = null
            
          album mustNotEqual(null) 
          
          try {
              albumDao.updateAlbumCaratula(newCaratula,album.getId) must throwA[DAOException]
          }
          catch {          
             case e: DAOException =>
             Logger.info("No es posible modificar la Caratula del album " + 1)
             

          }
                  
         
    }
    
  }
    

}