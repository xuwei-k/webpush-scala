package webpush

import javax.inject.Inject

import play.api.mvc._
import nl.martijndwars.webpush._
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.webjars.play.WebJarsUtil
import play.api.libs.json.{JsError, JsSuccess}

import scala.io.Source

class Main @Inject()(webjar: WebJarsUtil, c: ControllerComponents) extends AbstractController(c) {

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
    Ok(views.html.main(publicKeyHexStrings, webjar))
  }

  val send = Action(c.parsers.json) { request =>
    request.body.validate[SendRequest] match {
      case JsSuccess(value, _) =>
        val notification = value.toNotification
        val client = new PushService()
        client.setPrivateKey(privateKey)
        client.setPublicKey(publicKey)
        val response = client.send(notification)
        val status = response.getStatusLine.getStatusCode
        val body = Source.fromInputStream(
          response.getEntity.getContent
        ).getLines().mkString("\n")
        println("status = " + status)
        println("body = " + body)
        val s = new Status(status)
        s(body)
      case e: JsError =>
        println(e)
        println(request.body)
        BadRequest(e.toString)
    }
  }

}
