//> using scala "3.3.1"
//> using dep ba.sake::sharaf:0.0.22

// https://htmx.org/examples/animations/

import io.undertow.Undertow
import scalatags.Text.all.*
import ba.sake.hepek.html.HtmlPage
import ba.sake.hepek.htmx.*
import ba.sake.sharaf.*, routing.*

object ColorThrobView extends HtmlPage with HtmxDependencies:
  override def bodyContent = snippet("red")

  def snippet(color: String) = div(
    id := "color-demo", // must stay same!
    hx.get := "/colors",
    hx.swap := "outerHTML",
    hx.trigger := "every 1s",
    cls := "smooth",
    style := s"color:${color}"
  )("Color Swap Demo")

  override def stylesInline = List("""
  .smooth {
    transition: all 1s ease-in;
  }
  """)

val routes = Routes:
  case GET() -> Path() =>
    Response.withBody(new HtmlPage {
      override def bodyContent = div(
        a(href := "color-throb")("Color throb")
      )
    })

  case GET() -> Path("color-throb") =>
    Response.withBody(ColorThrobView)
  case GET() -> Path("colors") =>
    // generate a random #aBc color
    // https://stackoverflow.com/a/19298151
    val x = scala.util.Random.nextInt(256)
    val randomColor = String.format("#%03X", x)
    Response.withBody(ColorThrobView.snippet(randomColor))

Undertow.builder
  .addHttpListener(8181, "localhost")
  .setHandler(SharafHandler(routes))
  .build
  .start()

println(s"Server started at http://localhost:8181")