//> using scala "3.3.1"
//> using dep ba.sake::sharaf:0.0.22

import io.undertow.Undertow
import ba.sake.sharaf.*, routing.*

val routes = Routes:
  case GET() -> Path("string", x) =>
    Response.withBody(s"string = ${x}")

  case GET() -> Path("int", param[Int](x)) =>
    Response.withBody(s"int = ${x}")

Undertow.builder
  .addHttpListener(8181, "localhost")
  .setHandler(SharafHandler(routes))
  .build
  .start()

println(s"Server started at http://localhost:8181")