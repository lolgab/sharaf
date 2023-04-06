package ba.sake.sharaf.handlers

import scala.util.control.NonFatal
import io.undertow.server.HttpHandler
import io.undertow.server.HttpServerExchange

import ba.sake.tupson.*

private[sharaf] final class ErrorHandler(
    httpHandler: HttpHandler
) extends HttpHandler {

  override def handleRequest(exchange: HttpServerExchange): Unit = try {
    exchange.startBlocking()
    if (exchange.isInIoThread()) {
      exchange.dispatch(this)
    } else {
      httpHandler.handleRequest(exchange)
    }
  } catch {
    case NonFatal(e) =>
      if (exchange.isResponseChannelAvailable()) {
        // TODO if json ..
        e match {
          case pe: ParsingException => // TODO ErrorMessage klasa.. fino JSON
            exchange.getResponseSender().send(e.getMessage())
            exchange.setResponseCode(400)
          case te: TupsonException =>
            exchange.getResponseSender().send(e.getMessage())
            exchange.setResponseCode(400)
        }
      }
  }

}
