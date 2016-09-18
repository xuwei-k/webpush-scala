package webpush

import javax.inject.Inject
import play.api.http.DefaultHttpFilters

class WebpushFilters @Inject() (httpsFilter: HTTPSRedirectFilter) extends DefaultHttpFilters(httpsFilter)
