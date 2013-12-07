package models

class BusinessException(msg: String) extends RuntimeException(msg)

object BusinessException {
  def create(msg: String) : BusinessException = new BusinessException(msg)

  def create(msg: String, cause: Throwable) = new BusinessException(msg).initCause(cause)
}