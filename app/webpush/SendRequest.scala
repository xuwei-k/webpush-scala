package webpush

import java.nio.charset.StandardCharsets

import nl.martijndwars.webpush._
import play.api.libs.json._
import play.jsonext.CaseClassFormats

final case class SendRequest(
  endpoint: String,
  publicKey: String,
  userAuth: String,
  payload: String,
  ttl: Int
) {

  def toNotification: Notification =
    new Notification(
      endpoint,
      Utils.loadPublicKey(publicKey),
      Utils.base64Decode(userAuth),
      payload.getBytes(StandardCharsets.UTF_8),
      ttl
    )
}

object SendRequest{
  implicit val format: OFormat[SendRequest] =
    CaseClassFormats(apply _, unapply _)(
      "endpoint",
      "publicKey",
      "userAuth",
      "payload",
      "ttl"
    )
}
