package models

import anorm.Macro
import play.api.libs.json.Json

/**
  * Created by Alexis on 25/01/2017.
  */
case class Quote(id: Option[Long], author: String, quote: String, category: String)

object Quote {
  implicit val format = Json.format[Quote]

  val parser = Macro.namedParser[Quote]
}