/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * TEMPLATE-BACKEND-SPRING-BOOT
 */
package cl.com.gesoft.security.api;

/**
 * The type Authentication exception.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
public class AuthenticationException extends Exception {
    /**
     * The Status.
     */
    public int status;

    /**
     * Instantiates a new Authentication exception.
     *
     * @param message the message
     */
    public AuthenticationException(String message) {
        super(message);
    }

    /**
     * Instantiates a new Authentication exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public AuthenticationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Authentication exception.
     *
     * @param message the message
     * @param cause   the cause
     * @param status  the status
     */
    public AuthenticationException(String message, Throwable cause, int status) {
        super(message, cause);
        this.status = status;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public int getStatus() {
        return status;
    }
}
