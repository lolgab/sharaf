package demo

import ba.sake.validation.*
import ba.sake.formson.*
import ba.sake.sharaf.*
import ba.sake.sharaf.routing.*
import ba.sake.sharaf.handlers.*
import io.undertow.Undertow

@main def main: Unit = {

  val routes: Routes = { case (POST(), Path("form"), _) =>
    val req = Request.current.bodyForm[CreateCustomerForm]
    //  val req2 = Request.current.bodyForm[Int]
    Response(req.toString)
  }

  val server = Undertow
    .builder()
    .addHttpListener(8181, "localhost")
    .setHandler(
      ErrorHandler(
        RoutesHandler(routes)
      )
    )
    .build()

  server.start()

  val serverInfo = server.getListenerInfo().get(0)
  val url = s"${serverInfo.getProtcol}:/${serverInfo.getAddress}"
  println(s"Started HTTP server at $url")

}

case class CreateCustomerForm(
    name: String,
    photo: java.nio.file.Path,
    address: CreateAddressForm,
    hobbies: List[String]
) derives FromFormData {
  validate(
    check(name).is(!_.isBlank, "must not be blank")
  )
}

case class CreateAddressForm(
    street: String
) derives FromFormData
