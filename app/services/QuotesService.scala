package services

import javax.inject.Inject

import com.google.inject.Singleton
import models.Quote
import play.api.db.{DB, Database, Databases}
import anorm._

import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by Alexis on 25/01/2017.
  */
@Singleton
class QuotesService @Inject() (db: Database)(implicit ec: ExecutionContext) {

  def addQuote(quote: Quote): Option[Long] = {
    db.withConnection { implicit c =>
      SQL(
        """
          INSERT INTO
            quotes(quote, author, category)
            VALUES({quote}, {author}, {category})
        """
      ).on(
        'quote -> quote.quote,
        'author -> quote.author,
        'category -> quote.category
      ).executeInsert()
    }
  }

  def listQuotes(): Future[Seq[Quote]] = {
    Future {
      db.withConnection { implicit c =>
        SQL(
          """
            SELECT *
            FROM
              quotes
          """
        ).as(Quote.parser.*)
      }
    }
  }

}
