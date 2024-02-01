//> using scala "3.3.1"
//> using dep ba.sake::sharaf:0.0.22

import io.undertow.Undertow
import ba.sake.tupson.JsonRW
import ba.sake.sharaf.*, routing.*

case class Car(brand: String, model: String, quantity: Int) derives JsonRW

var db: Seq[Car] = Seq()

val routes = Routes:  
  case GET() -> Path("cars") =>
    Response.withBody(db)

  case GET() -> Path("cars", brand) =>
    val res = db.filter(_.brand == brand)
    Response.withBody(res)

  case POST() -> Path("cars") =>
    val reqBody = Request.current.bodyJson[Car]
    db = db.appended(reqBody)
    Response.withBody(reqBody)

Undertow.builder
  .addHttpListener(8181, "localhost")
  .setHandler(
    SharafHandler(routes).withErrorMapper(ErrorMapper.json)
  )
  .build
  .start()

println(s"Server started at http://localhost:8181")