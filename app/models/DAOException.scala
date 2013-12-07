
package models

class DAOException(msg: String) extends RuntimeException(msg)

object DAOException {
  def create(msg: String) : DAOException = new DAOException(msg)

  def create(msg: String, cause: Throwable) = new DAOException(msg).initCause(cause)
}