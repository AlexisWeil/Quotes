package controllers

import javax.inject._

import models.Quote
import play.api.libs.json._
import play.api.libs.ws.WSClient
import play.api.mvc._
import services.QuotesService

import scala.concurrent.{ExecutionContext, Future}

/**
 * This controller creates an `Action` to handle HTTP requests to the
 * application's home page.
 */
@Singleton
class HomeController @Inject()(wsClient: WSClient, quotesService: QuotesService)(implicit executionContext: ExecutionContext) extends Controller {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def index = Action { implicit request =>
    Ok(views.html.index("toto"))
  }

  def quote = Action.async { implicit req =>

    wsClient.url("https://andruxnet-random-famous-quotes.p.mashape.com/")
      .withHeaders("X-Mashape-Key" -> "rsAAfpz3jdmsheqkswNVwKHbYhRLp1bN1JwjsnICBaiDOWqwRA")
      .post(
        Json.obj( // { "cat": "toto", "nb": 123 }
          "cat" -> "toto",
          "nb" -> 123
        )
      )
      .map { res =>
        res.json.asOpt[Quote] match {
          case Some(quote) =>
            quotesService.addQuote(quote)
            Ok(views.html.quote(quote))
          case None =>
            BadRequest
        }
      }
  }

  def listQuotes = Action.async { implicit req =>
    quotesService.listQuotes().map { quotes =>
      Ok(views.html.quotes(quotes))
    }
  }

}
