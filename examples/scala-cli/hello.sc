//> using scala "3.3.1"
//> using dep ba.sake::sharaf:0.0.18

import io.undertow.Undertow
import ba.sake.sharaf.*, routing.*

val routes = Routes:
  case GET() ->  Path("my-prefix", segments*) =>
    Response.withBody(s"Hello there $segments")

Undertow
  .builder
  .addHttpListener(8181, "localhost")
  .setHandler(SharafHandler(routes))
  .build
  .start()

println(s"Server started at http://localhost:8181")
