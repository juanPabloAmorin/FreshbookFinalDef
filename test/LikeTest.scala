import org.specs2.mutable._
import org.specs2.runner._
import org.junit.runner._
import play.api.test._
import play.api.test.Helpers._
import models._
import play.api._
import java.util.Date

@RunWith(classOf[JUnitRunner])
class LikeTest extends Specification {
  
 
  //before-init
  var likeDao: LikeDAO = DAOFabrica.getLikeDAO;
  var like: Like = new Like();
  
  abstract class WithDbData extends WithApplication() {
      def setupData() {}
  }
  
    //test
    
    "Like class methods" should {
      
          "insertLike with correct values" in new WithDbData{
         
             var stringDate = "05/12/2013"
             var date: Date = new Date(stringDate)
             like = new Like(20,"L",date,null,Some(1),null,1)
             
             try
             {
                   beRight(likeDao.insertLike(like))
             } 
             catch {
                 case e: DAOException =>
                 Logger.error("No se ha creado el like ")
                 
             }
       }
          
          
       "insertLike with wrong tipo must throw an exception" in new WithDbData{
         
             var stringDate = "05/12/2013"
             var date: Date = new Date(stringDate)
             like = new Like()
             
             like.setTipo("LL")
             like.setFkAlbum(Some(1))
             like.setFkUsuario(1)
             
             try
             {
                  likeDao.insertLike(like) must throwA[DAOException]
             } 
             catch {
                 case e: DAOException =>
                 Logger.error("No se ha creado el like ")
                 
             }
       }
       
       
       "getLikesForAlbum whit exist album and exists like must be > 0" in new WithDbData{
         
            var numberLikes: Long = 0;
            var matche: Long = 0;
            
            numberLikes = likeDao.getLikesForAlbum(1)
            
            numberLikes must beGreaterThan(matche)
       } 
       
       "getLikesForAlbum whit exist album and no like must be = 0" in new WithDbData{
         
            var numberLikes: Long = 0;
            var matche: Long = 0;
            
            numberLikes = likeDao.getLikesForAlbum(4)
            
            numberLikes mustEqual(matche)
       } 
       
       "getLikesForAlbum whit not exist album must be = 0" in new WithDbData{
         
            var numberLikes: Long = 0;
            var matche: Long = 0;
            
            numberLikes = likeDao.getLikesForAlbum(100)
            
            numberLikes mustEqual(matche)
       }
       
       
       "getUnlikesForAlbum whit exist album and exists Unlike must be > 0" in new WithDbData{
         
            var numberUnlikes: Long = 0;
            var matche: Long = 0;
            
            numberUnlikes = likeDao.getUnlikesForAlbum(1)
            
            numberUnlikes must beGreaterThan(matche)
       } 
       
       "getUnlikesForAlbum whit exist album and no Unlike must be = 0" in new WithDbData{
         
            var numberUnlikes: Long = 0;
            var matche: Long = 0;
            
            numberUnlikes = likeDao.getUnlikesForAlbum(4)
            
            numberUnlikes mustEqual(matche)
       } 
       
       "getUnlikesForAlbum whit not exist album must be = 0" in new WithDbData{
         
            var numberUnlikes: Long = 0;
            var matche: Long = 0;
            
            numberUnlikes = likeDao.getUnlikesForAlbum(100)
            
            numberUnlikes mustEqual(matche)
       }
       
       "getUnlikesForComment whit exist Comment and exists Unlike must be > 0" in new WithDbData{
         
            var numberUnlikes: Long = 0;
            var matche: Long = 0;
            
            numberUnlikes = likeDao.getUnlikesForComment(1)
            
            numberUnlikes must beGreaterThan(matche)
       } 
       
       "getUnlikesForComment whit exist Comment and no Unlike must be = 0" in new WithDbData{
         
            var numberUnlikes: Long = 0;
            var matche: Long = 0;
            
            numberUnlikes = likeDao.getUnlikesForComment(4)
            
            numberUnlikes mustEqual(matche)
       } 
       
       "getUnlikesForComment whit not exist Comment must be = 0" in new WithDbData{
         
            var numberUnlikes: Long = 0;
            var matche: Long = 0;
            
            numberUnlikes = likeDao.getUnlikesForComment(100)
            
            numberUnlikes mustEqual(matche)
       }
       
       "getLikesForComment whit exist Comment and exists Like must be > 0" in new WithDbData{
         
            var numberLikes: Long = 0;
            var matche: Long = 0;
            
            numberLikes = likeDao.getLikesForComment(1)
            
            numberLikes must beGreaterThan(matche)
       } 
       
       "getLikesForComment whit exist Comment and no Like must be = 0" in new WithDbData{
         
            var numberLikes: Long = 0;
            var matche: Long = 0;
            
            numberLikes = likeDao.getLikesForComment(4)
            
            numberLikes mustEqual(matche)
       } 
       
       "getLikesForComment whit not exist Comment must be = 0" in new WithDbData{
         
            var numberLikes: Long = 0;
            var matche: Long = 0;
            
            numberLikes = likeDao.getLikesForComment(100)
            
            numberLikes mustEqual(matche)
       }
       
       "iLikeAlbum whit exist Album and exists Like must be > 0" in new WithDbData{
         
            var numberLikes: Long = 0;
            var matche: Long = 0;
            
            numberLikes = likeDao.iLikeAlbum(1,1)
            
            numberLikes must beGreaterThan(matche)
       } 
       
       "iLikeAlbum whit exist Album and no Like must be = 0" in new WithDbData{
         
            var numberLikes: Long = 0;
            var matche: Long = 0;
            
            numberLikes = likeDao.iLikeAlbum(4,1)
            
            numberLikes mustEqual(matche)
       } 
       
       "iLikeAlbum whit not exist Album must be = 0" in new WithDbData{
         
            var numberLikes: Long = 0;
            var matche: Long = 0;
            
            numberLikes = likeDao.iLikeAlbum(100,1)
            
            numberLikes mustEqual(matche)
       }
       
       "iLikeAlbum whit not exist Usuario must be = 0" in new WithDbData{
         
            var numberLikes: Long = 0;
            var matche: Long = 0;
            
            numberLikes = likeDao.iLikeAlbum(1,100)
            
            numberLikes mustEqual(matche)
       }
       
       
       "iDontLikeAlbum whit exist Album and exists Like must be > 0" in new WithDbData{
         
            var numberLikes: Long = 0;
            var matche: Long = 0;
            
            numberLikes = likeDao.iDontLikeAlbum(1,1)
            
            numberLikes must beGreaterThan(matche)
       } 
       
       "iDontLikeAlbum whit exist Album and no Like must be = 0" in new WithDbData{
         
            var numberLikes: Long = 0;
            var matche: Long = 0;
            
            numberLikes = likeDao.iDontLikeAlbum(4,1)
            
            numberLikes mustEqual(matche)
       } 
       
       "iDontLikeAlbum whit not exist Album must be = 0" in new WithDbData{
         
            var numberLikes: Long = 0;
            var matche: Long = 0;
            
            numberLikes = likeDao.iDontLikeAlbum(100,1)
            
            numberLikes mustEqual(matche)
       }
       
       "iDontLikeAlbum whit not exist Usuario must be = 0" in new WithDbData{
         
            var numberLikes: Long = 0;
            var matche: Long = 0;
            
            numberLikes = likeDao.iDontLikeAlbum(1,100)
            
            numberLikes mustEqual(matche)
       }
       
       
       "iLikeComment whit exist Comment and exists Like must be > 0" in new WithDbData{
         
            var numberLikes: Long = 0;
            var matche: Long = 0;
            
            numberLikes = likeDao.iLikeComment(1,1)
            
            numberLikes must beGreaterThan(matche)
       } 
       
       "iLikeComment whit exist Comment and no Like must be = 0" in new WithDbData{
         
            var numberLikes: Long = 0;
            var matche: Long = 0;
            
            numberLikes = likeDao.iLikeComment(4,1)
            
            numberLikes mustEqual(matche)
       } 
       
       "iLikeComment whit not exist Comment must be = 0" in new WithDbData{
         
            var numberLikes: Long = 0;
            var matche: Long = 0;
            
            numberLikes = likeDao.iLikeComment(100,1)
            
            numberLikes mustEqual(matche)
       }
       
       "iLikeComment whit not exist Usuario must be = 0" in new WithDbData{
         
            var numberLikes: Long = 0;
            var matche: Long = 0;
            
            numberLikes = likeDao.iLikeComment(1,100)
            
            numberLikes mustEqual(matche)
       }
       
       
       "iDontLikeComment whit exist Comment and exists Like must be > 0" in new WithDbData{
         
            var numberLikes: Long = 0;
            var matche: Long = 0;
            
            numberLikes = likeDao.iDontLikeComment(1,1)
            
            numberLikes must beGreaterThan(matche)
       } 
       
       "iDontLikeComment whit exist Comment and no Like must be = 0" in new WithDbData{
         
            var numberLikes: Long = 0;
            var matche: Long = 0;
            
            numberLikes = likeDao.iDontLikeComment(4,1)
            
            numberLikes mustEqual(matche)
       } 
       
       "iDontLikeComment whit not exist Comment must be = 0" in new WithDbData{
         
            var numberLikes: Long = 0;
            var matche: Long = 0;
            
            numberLikes = likeDao.iDontLikeComment(100,1)
            
            numberLikes mustEqual(matche)
       }
       
       "iDontLikeComment whit not exist Usuario must be = 0" in new WithDbData{
         
            var numberLikes: Long = 0;
            var matche: Long = 0;
            
            numberLikes = likeDao.iDontLikeComment(1,100)
            
            numberLikes mustEqual(matche)
       }
       
        "deleteAlbumLike whit valid id must be right" in new WithDbData{
         
           try{
                beRight(likeDao.deleteAlbumLike(1,1))
           }
           catch {
                 case e: DAOException =>
                 Logger.error("No se ha eliminado el like con id = " + 1)
                
             }
       }
        
        "deleteAlbumUnlike whit valid id must be right" in new WithDbData{
         
           try{
                beRight(likeDao.deleteAlbumUnlike(1,1))
           }
           catch {
                 case e: DAOException =>
                 Logger.error("No se ha eliminado el unlike con id = " + 1)
                
             }
       }
        
        "deleteCommentLike whit valid id must be right" in new WithDbData{
         
           try{
                beRight(likeDao.deleteCommentLike(1,1))
           }
           catch {
                 case e: DAOException =>
                 Logger.error("No se ha eliminado el like con id = " + 1)
                
             }
       }
        
        "deleteCommentUnlike whit valid id must be right" in new WithDbData{
         
           try{
                beRight(likeDao.deleteCommentUnlike(1,1))
           }
           catch {
                 case e: DAOException =>
                 Logger.error("No se ha eliminado el unlike con id = " + 1)
                
             }
       }
          
          
    }
    
}