
# Sharaf :nut_and_bolt:

Simple, intuitive, batteries-included HTTP server library.

Still WIP  :construction: but very much usable. :construction_worker:

## Usage
Mill:
```scala
def ivyDeps = Agg(ivy"ba.sake::sharaf:0.0.11")

def scalacOptions = Seq("-Yretain-trees")
```

## Examples

A hello world example in scala-cli:
```scala
//> using dep ba.sake::sharaf:0.0.11

import io.undertow.Undertow
import ba.sake.sharaf.*, routing.*

val routes: Routes = 
  case GET() -> Path("hello", name) =>
    Response.withBody(s"Hello $name")

val server = Undertow
  .builder()
  .addHttpListener(8181, "localhost")
  .setHandler(SharafHandler(routes))
  .build()

server.start()

println(s"Server started at http://localhost:8181")
```

You can run it like this:
```sh
scala-cli examples/scala-cli/hello.sc
```
Then you can do a GET http://localhost:8181/hello/Bob  
to try it out.

---

Full blown standalone examples:
- handling [json](examples/json)
- rendering [html](examples/html) and serving static files
- handling [form data](examples/form) and [Bootstrap](https://getbootstrap.com/) usage
- [implementation](examples/todo) of [todobackend.com](http://todobackend.com/) spec, featuring CORS handling
- [OAuth2 login](examples/oauth2) with [Pac4J library](https://www.pac4j.org/)


## Why sharaf?

Simplicity and ease of use is the main focus of sharaf.  

It is built on top of [Undertow](https://undertow.io/).  
This means you can use awesome libraries built for Undertow, like [pac4j](https://github.com/pac4j/undertow-pac4j) for security and similar.  
Also, you can leverage Undertow's lower level API, e.g. for WebSockets.

Sharaf bundles a set of libraries:
- [querson](querson) for query parameters
- [tupson](https://github.com/sake92/tupson) for JSON
- [formson](formson) for forms
- [validson](validson) for validation
- [hepek-components](https://github.com/sake92/hepek) for HTML (with [scalatags](https://github.com/com-lihaoyi/scalatags))
- [requests](https://github.com/com-lihaoyi/requests-scala) for firing HTTP requests
- [typesafe-config](https://github.com/lightbend/config) for configuration


## Misc

Why name "sharaf"?  

Šaraf means a "screw" in Bosnian, which reminds me of scala spiral logo.

