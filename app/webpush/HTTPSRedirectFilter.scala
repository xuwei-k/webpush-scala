package webpush

import akka.stream.Materializer
import javax.inject.Inject
import play.api.mvc._
import scala.concurrent.{ExecutionContext, Future}

class HTTPSRedirectFilter @Inject()(implicit
  val ec: ExecutionContext,
  override val mat: Materializer
) extends Filter {

  override def apply(nextFilter: RequestHeader => Future[Result])(requestHeader: RequestHeader) = {
    requestHeader.headers.get("x-forwarded-proto") match {
      case Some("https") =>
        nextFilter(requestHeader).map { result =>
          result.withHeaders(("Strict-Transport-Security", "max-age=31536000"))
        }
      case Some(header) =>
        Future.successful(Results.Redirect("https://" + requestHeader.host + requestHeader.uri, 301))
      case None => nextFilter(requestHeader)
    }
  }
}
