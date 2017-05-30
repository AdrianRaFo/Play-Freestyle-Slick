package filters

import javax.inject.Inject
import play.api.http.DefaultHttpFilters
import play.filters.csrf.CSRFFilter
import play.filters.hosts.AllowedHostsFilter
import play.filters.headers.SecurityHeadersFilter

class SecurityFilters @Inject()(
    csrfFilter: CSRFFilter,
    allowedHostsFilter: AllowedHostsFilter,
    securityHeadersFilter: SecurityHeadersFilter
) extends DefaultHttpFilters(
      csrfFilter,
      allowedHostsFilter,
      securityHeadersFilter
    )
