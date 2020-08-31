/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * TEMPLATE-BACKEND-SPRING-BOOT
 */
package cl.com.gesoft.workflow.web.log;


import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.MDC;
import org.springframework.http.MediaType;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * The type Log filter.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
public class LogFilter implements Filter {
    /**
     * The Log.
     */
    Log log = LogFactory.getLog(LogFilter.class);

    /**
     * The constant REQUEST_ID.
     */
    public final static String REQUEST_ID = "REQUEST_ID";
    /**
     * The Simple date format.
     */
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        String requestId = simpleDateFormat.format(new Date(System.currentTimeMillis())) + "-" + UUID.randomUUID();
        long t0 = System.currentTimeMillis();
        MDC.put(REQUEST_ID, requestId); //to set an attribute
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletRequest = new ApvasRequestWrapper((HttpServletRequest) httpServletRequest);
        httpServletResponse = new ApvasResponseWrapper((HttpServletResponse) response);
        request.setAttribute(REQUEST_ID, requestId);
        chain.doFilter(httpServletRequest, httpServletResponse);
        log.info("RequestId:[" + requestId + "]");
        log.info("Method Controller:[" + httpServletRequest.getMethod() + "]");
        log.info("Method Request:[" + httpServletRequest.getRequestURI() + "]");
        log.info("Method Request QueryString:[" + httpServletRequest.getQueryString() + "]");
        log.info("Request Body:[" + IOUtils.toString(httpServletRequest.getInputStream(), "UTF-8") + "]");
        log.info("Response Status:[" + httpServletResponse.getStatus() + "]");
        log.info("Response Content Type:[" + httpServletResponse.getContentType() + "]");
        if (MediaType.APPLICATION_PDF.toString().equalsIgnoreCase(httpServletResponse.getContentType())) {
            log.info("Response Body:[PDF FILE]");
        } else if ("image/png".equalsIgnoreCase(httpServletResponse.getContentType())) {
            log.info("Response Body:[IMAGE PNG]");
        } else if ("application/excel".equalsIgnoreCase(httpServletResponse.getContentType())) {
            log.info("Response Body:[EXCEL FILE]");
        } else {
            log.info("Response Body:[" + new String(((ApvasResponseWrapper) httpServletResponse).getResponseByteArray()) + "]");
        }
        long t1 = System.currentTimeMillis();
        log.info("Response Time:[" + (t1 - t0) + "ms]");
        MDC.remove(REQUEST_ID);

    }

    @Override
    public void destroy() {

    }

}
