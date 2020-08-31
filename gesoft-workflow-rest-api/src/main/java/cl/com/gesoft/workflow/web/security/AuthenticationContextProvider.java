/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * TEMPLATE-BACKEND-SPRING-BOOT
 */
package cl.com.gesoft.workflow.web.security;

import cl.com.gesoft.security.api.AuthenticationContext;

/**
 * The interface Authentication context provider.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
public interface AuthenticationContextProvider {
    /**
     * Create authentication context authentication context.
     *
     * @param authKey the auth key
     * @param var2    the var 2
     * @return the authentication context
     * @throws Exception the exception
     */
    AuthenticationContext createAuthenticationContext(String authKey, Object var2) throws Exception;

    /**
     * Gets authentication context.
     *
     * @return the authentication context
     */
    AuthenticationContext getAuthenticationContext();

    /**
     * Destroy authentication context.
     *
     * @return the authentication context
     */
    AuthenticationContext destroy();
}

