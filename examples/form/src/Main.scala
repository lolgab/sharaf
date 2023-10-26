package demo

import io.undertow.Undertow
import ba.sake.validson.*
import ba.sake.sharaf.*, routing.*
import demo.views.*

@main def main: Unit =
  val module = FormModule(8181)
  module.server.start()
  println(s"Started HTTP server at ${module.baseUrl}")

class FormModule(port: Int) {

  val baseUrl = s"http://localhost:${port}"

  private val routes: Routes = {
    case GET() -> Path() =>
      Response.withBody(FormPage())

    case POST() -> Path("form-submit") =>
      val req = Request.current.bodyForm[CreateCustomerForm]
      req.validate match
        case Seq()  => Response.withBody(SucessPage(req))
        case errors => Response.withBody(FormPage(Some(req), errors)).withStatus(400)
  }

  val server = Undertow
    .builder()
    .addHttpListener(port, "localhost")
    .setHandler(SharafHandler(routes))
    .build()
}
