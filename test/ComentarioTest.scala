import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import play.api.test._
import play.api.test.Helpers._
import models._
import play.api._
import java.util.Date

@RunWith(classOf[JUnitRunner])
class ComentarioTest extends Specification {
  
 
  //after-init
  var userDao: UsuarioDAO = DAOFabrica.getUsuarioDAO;
  var user: Usuario = new Usuario();
  var album: Album = new Album();
  var albumDao: AlbumDAO = DAOFabrica.getAlbumDAO;
  var comentario: Comentario = new Comentario();
  var comentDao: ComentarioDAO = DAOFabrica.getComentarioDAO()
  
  abstract class WithDbData extends WithApplication() {
      def setupData() {}
  }
  
    //test
  
   "Album class methods" should {
    
    "insertComentario with correct values must be right" in new WithDbData{
         
             var stringDate = "05/12/2013"
             var date: Date = new Date(stringDate)
             var comentario: Comentario = new Comentario(20,"contenido del comentario",date,null,Some(1),null,1)
             
             try
             {
                   beRight(comentDao.insertComentario(comentario))
             } 
             catch {
                 case e: DAOException =>
                 Logger.error("No se ha creado el album " + album.getNombre)
                 
             }
       }
    
    "insertComentario with very large content must throw an exception" in new WithDbData{
         
             var stringDate = "05/12/2013"
             var date: Date = new Date(stringDate)
             var comentario: Comentario = new Comentario()
             comentario.setContenido("""
                                      el contenidooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
                                      oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
                                      oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
                                      oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
                                      oooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooooo
                                     """)
             comentario.setFecha(date)
             comentario.setFkAlbum(Some(1))
             comentario.setFkUsuario(1)
             
             try
             {
                  comentDao.insertComentario(comentario) must throwA[DAOException]
             } 
             catch {
                 case e: DAOException =>
                 Logger.error("No se ha creado el album " + album.getNombre)
                 
             }
       }
    
    "insertComentario with no valid fkAlbum must throw an exception" in new WithDbData{
         
             var stringDate = "05/12/2013"
             var date: Date = new Date(stringDate)
             var comentario: Comentario = new Comentario()
             comentario.setContenido(" contenido del comentario ")
             comentario.setFecha(date)
             comentario.setFkAlbum(Some(200))
             comentario.setFkUsuario(1)
             
             try
             {
                  comentDao.insertComentario(comentario) must throwA[DAOException]
             } 
             catch {
                 case e: DAOException =>
                 Logger.error("No se ha creado el album " + album.getNombre)
                 
             }
       }
    
    "insertComentario with no valid fkComentario must throw an exception" in new WithDbData{
         
             var stringDate = "05/12/2013"
             var date: Date = new Date(stringDate)
             var comentario: Comentario = new Comentario()
             comentario.setContenido(" contenido del comentario ")
             comentario.setFecha(date)
             comentario.setFkComentario(Some(200))
             comentario.setFkUsuario(1)
             
             try
             {
                  comentDao.insertComentario(comentario) must throwA[DAOException]
             } 
             catch {
                 case e: DAOException =>
                 Logger.error("No se ha creado el album " + album.getNombre)
                 
             }
       }
    
    "insertComentario with no valid fkUsuario must throw an exception" in new WithDbData{
         
             var stringDate = "05/12/2013"
             var date: Date = new Date(stringDate)
             var comentario: Comentario = new Comentario()
             comentario.setContenido(" contenido del comentario ")
             comentario.setFecha(date)
             comentario.setFkComentario(Some(1))
             comentario.setFkUsuario(200)
             
             try
             {
                  comentDao.insertComentario(comentario) must throwA[DAOException]
             } 
             catch {
                 case e: DAOException =>
                 Logger.error("No se ha creado el album " + album.getNombre)
                 
             }
       }
    
    "getCommentsForAlbum with no valid album id: comentList must be empty" in new WithDbData{
      
             var comentList: List[Comentario] = null
             
             comentList = comentDao.getCommentsForAlbum(200)
             
             comentList must be empty
    }
    
    "getCommentsForAlbum with valid album id and comments: comentList must not be empty" in new WithDbData{
      
             var comentList: List[Comentario] = null
             
             comentList = comentDao.getCommentsForAlbum(1)
             
             comentList must not be empty
    }
    
    "getCommentsForAlbum with valid album id and no comment: comentList must be empty" in new WithDbData{
      
             var comentList: List[Comentario] = null
             
             comentList = comentDao.getCommentsForAlbum(3)
             
             comentList must be empty
    }
    
    
    "deleteComentario whit valid id must be right" in new WithDbData{
         
           try{
                beRight(comentDao.deleteCommentById(1))
           }
           catch {
                 case e: DAOException =>
                 Logger.error("No se ha eliminado el album con id = " + 1)
                
             }
       }
     
    
    "getResponsesForComment with no valid comment id: responseList must be empty" in new WithDbData{
      
             var responseList: List[Comentario] = null
             
             responseList = comentDao.getResponsesForComment(200)
             
             responseList must be empty
    }
    
    "getResponsesForComment with valid comment id and responses: responseList must not be empty" in new WithDbData{
      
             var responseList: List[Comentario] = null
             
             responseList = comentDao.getResponsesForComment(1)
             
             responseList must not be empty
    }
    
    "getResponsesForComment with valid comment id and no responses: responseList must be empty" in new WithDbData{
      
             var responseList: List[Comentario] = null
             
             responseList = comentDao.getResponsesForComment(3)
             
             responseList must be empty
    }
    
    
    "commentCountResponses whit exist comment and exists responses must be > 0" in new WithDbData{
         
            var numberResponses: Long = 0;
            var matche: Long = 0;
            
            numberResponses = comentDao.commentCountResponses(1)
            
            numberResponses must beGreaterThan(matche)
       } 
       
       "commentCountResponses whit exist comment and no responses must be = 0" in new WithDbData{
         
            var numberResponses: Long = 0;
            var matche: Long = 0;
            
            numberResponses = comentDao.commentCountResponses(4)
            
            numberResponses mustEqual(matche)
       } 
       
       "commentCountResponses whit not exist comment must be = 0" in new WithDbData{
         
            var numberResponses: Long = 0;
            var matche: Long = 0;
            
            numberResponses = comentDao.commentCountResponses(100)
            
            numberResponses mustEqual(matche)
       }
   }
    
}