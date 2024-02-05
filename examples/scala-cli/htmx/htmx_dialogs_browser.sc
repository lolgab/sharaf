//> using scala "3.3.1"
//> using dep ba.sake::sharaf:0.0.22

import io.undertow.util.HttpString
import io.undertow.Undertow
import scalatags.Text.all.*
import ba.sake.hepek.html.HtmlPage
import ba.sake.hepek.htmx.*
import ba.sake.formson.FormDataRW
import ba.sake.sharaf.*, routing.*

object IndexView extends HtmlPage with HtmxDependencies:
  override def bodyContent = div(
    button(
      hx.post := "/submit",
      hx.prompt := "Enter a string",
      hx.confirm := "Are you sure?",
      hx.target := "#response"
    )("Prompt Submission"),
    div(id := "response")
  )

val routes = Routes:
  case GET() -> Path() =>
    Response.withBody(IndexView)
  case POST() -> Path("submit") =>
    val submittedData = Request.current.headers(HttpString("HX-Prompt")).head
    Response.withBody(
      div(
        p("You submitted data:"),
        submittedData
      )
    )

Undertow.builder
  .addHttpListener(8181, "localhost")
  .setHandler(SharafHandler(routes))
  .build
  .start()

println(s"Server started at http://localhost:8181")