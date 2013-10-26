package models

import java.util.{Date}

import play.api._
import play.api.mvc._
import play.api.db._
import play.api.Play.current

import anorm._
import anorm.SqlParser._
import models._


trait AlbumDAO
{
      def findAlbumsByUser(userId: Long): List[Album];
      def getNumberOfFilesInAlbum(albumId: Long): Long;
      def insertAlbum(newAlbum: Album)
      def deleteAlbumById(albumId: Long)
      
}