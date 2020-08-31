/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * TEMPLATE-BACKEND-SPRING-BOOT
 */
package cl.com.gesoft.workflow.web.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * The type Ssl filter.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
@Component
public class SSLFilter implements Filter {
    /**
     * The Location.
     */
    static String LOCATION = "Location";

    @Value("${settings.secureUrl}")
    private String redirectSecureUrl;

    @Value("${settings.securePort}")
    private int securePort;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest.getLocalPort() == securePort) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            ((HttpServletResponse) servletResponse).setHeader(LOCATION, redirectSecureUrl);
            ((HttpServletResponse) servletResponse).setStatus(HttpStatus.MOVED_PERMANENTLY.value());
        }
    }

    @Override
    public void destroy() {

    }
}
