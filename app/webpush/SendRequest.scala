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
      Base64Encoder.decode(userAuth),
      payload.getBytes(StandardCharsets.UTF_8),
      ttl
    )
}

object SendRequest{
  given OFormat[SendRequest] =
    CaseClassFormats(apply, (x: SendRequest) => Option(Tuple.fromProductTyped(x)))(
      "endpoint",
      "publicKey",
      "userAuth",
      "payload",
      "ttl"
    )
}
