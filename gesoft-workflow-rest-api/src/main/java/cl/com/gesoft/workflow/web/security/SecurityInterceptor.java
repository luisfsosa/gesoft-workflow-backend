/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * TEMPLATE-BACKEND-SPRING-BOOT
 */
package cl.com.gesoft.workflow.web.security;

import cl.com.gesoft.workflow.web.RestServiceException;
import cl.com.gesoft.security.api.AuthenticationContext;
import cl.com.gesoft.security.api.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The type Security interceptor.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
public class SecurityInterceptor implements HandlerInterceptor {
    /**
     * The constant AUTH_KEY.
     */
    public static final String AUTH_KEY = "Auth-Key";

    /**
     * The Authentication context provider.
     */
    @Autowired
    AuthenticationContextProvider authenticationContextProvider;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        SecurityContextHolder.clearContext();
        if (HttpMethod.valueOf(httpServletRequest.getMethod()).equals(HttpMethod.OPTIONS)) {
            return true;
        } else {

            String authKey = httpServletRequest.getHeader(AUTH_KEY);
            System.out.println("ahi auth key " + authKey);

            try {

                AuthenticationContext authenticationContext = authenticationContextProvider.createAuthenticationContext(authKey, httpServletRequest);
                System.out.println("ahi voy ");
                if (authenticationContext != null) {
                    System.out.println("se logro");
                    return true;
                }
            } catch (AuthenticationException e) {
                System.out.println("la exepcion es " + e);
                throw new RestServiceException(HttpStatus.valueOf(e.getStatus()), e.getMessage());
            } catch (Throwable e) {
                System.out.println("la exepcion2 es " + e);
                throw new RestServiceException(HttpStatus.UNAUTHORIZED, "Recurso no Disponible: " + httpServletRequest.getRequestURI());
            }
            throw new RestServiceException(HttpStatus.UNAUTHORIZED, "Recurso no Disponible: " + httpServletRequest.getRequestURI());
        }

    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }



    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }
}
