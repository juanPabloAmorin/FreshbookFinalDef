package models

import java.util.{ Date }

import play.api._
import play.api.mvc._
import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._
import models._

trait LugarDAO {
  /*El metodo insertLugar permite insertar un nuevo objeto de la clase lugar en la  *
   * base de datos */

  def insertLugar(newLugar: Lugar)

  /*El metodo getLugarById permite obtener un objeto de la clase lugar dado su id  */

  def getLugarById(id: Long): Option[Lugar]

  /* el metodo getLugarByNameAndType permite obtener un objeto de la clase lugar
   * a partir de su nombre y el tipo de lugar(pais,estado o ciudad)                */

  def getLugarByNameAndType(name: String, typo: String): Option[Lugar]

  /* El metodo getNewLugarLastIdFromSequence permite obtener el id del ultimo lugar  *
   * insertado en la base de datos                                                   */

  def getNewLugarLastIdFromSequence(): Long

  /*el metodo getLugarByNameAndTypeAndZone permite obtener un objeto de la clase lugar
   * dado su nombre, el tipo (pais,estado,ciudad) y el lugar de nivel superior       */

  def getLugarByNameAndTypeAndZone(name: String, typo: String, zone: Long): Option[Lugar]
}