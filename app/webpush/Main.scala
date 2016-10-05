package webpush

import javax.inject.Inject

import play.api.mvc._
import nl.martijndwars.webpush._
import org.asynchttpclient.DefaultAsyncHttpClient
import org.bouncycastle.jce.provider.BouncyCastleProvider
import play.api.libs.json.{JsError, JsSuccess}

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.io.Source

class Main @Inject()(webJarAssets: controllers.WebJarAssets) extends Controller {

  java.security.Security.addProvider(new BouncyCastleProvider())

  private[this] val privateKey =
    Utils.loadPrivateKey("ANFu2lYRyNvpg+OvcYqF6N0O8gHir1JVkFbOhBdJsHeU")

  private[this] val publicKeyEncoded =
    "BFQ92i5QxtMLdL+Q5ptO/IwRz3Ptdub+ASQ23FfeN1VkDi1qdVjkEKwRbGa7/WqKX0QhFkNtXgqwu3u1AsfPhdE="

  private[this] val publicKeyHexStrings =
    java.util.Base64.getDecoder.decode(publicKeyEncoded).map(
      "0x%02x" format _
    ).mkString(",")

  private[this] val publicKey =
    Utils.loadPublicKey(publicKeyEncoded)

  val view = Action{
    Ok(views.html.main(publicKeyHexStrings, webJarAssets))
  }

  val send = Action(BodyParsers.parse.json) { request =>
    request.body.validate[SendRequest] match {
      case JsSuccess(value, _) =>
        val notification = value.toNotification
        val c = new DefaultAsyncHttpClient()

        try {
          val client = WebpushService.create(c)
          val response = Await.result(client.send(
            notification = notification,
            publicKey = publicKey,
            privateKey = privateKey,
            subject = None
          ), 10.seconds)
          val status = response.getStatusCode
          val body = response.getResponseBody
          println("status = " + status)
          println("body = " + body)
          val s = new Status(status)
          s(body)
        }finally{
          c.close()
        }
      case e: JsError =>
        println(e)
        println(request.body)
        BadRequest(e.toString)
    }
  }

}
