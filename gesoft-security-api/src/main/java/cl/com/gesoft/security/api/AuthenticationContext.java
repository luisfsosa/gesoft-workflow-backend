/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * TEMPLATE-BACKEND-SPRING-BOOT
 */
package cl.com.gesoft.security.api;

/**
 * The interface Authentication context.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
public interface AuthenticationContext {
    /**
     * Gets principal.
     *
     * @return the principal
     */
    public Object getPrincipal();
}
