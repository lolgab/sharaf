//> using dep ba.sake::sharaf:0.0.5

import io.undertow.Undertow

import ba.sake.sharaf.*
import ba.sake.sharaf.routing.*
import ba.sake.sharaf.handlers.*

val routes: Routes = {
  case GET() -> Path("hello", name) =>
    Response.withBody(s"Hello $name")
}

val server = Undertow
  .builder()
  .addHttpListener(8080, "localhost")
  .setHandler(ErrorHandler(RoutesHandler(routes)))
  .build()

server.start()

println(s"Server started at http://localhost:8080")
