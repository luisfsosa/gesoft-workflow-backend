/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * TEMPLATE-BACKEND-SPRING-BOOT
 */
package cl.com.gesoft.workflow.web;

import org.springframework.http.HttpStatus;

/**
 * The type Rest service exception.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
public class RestServiceException extends Exception {
    private HttpStatus status;

    /**
     * Instantiates a new Rest service exception.
     *
     * @param status the status
     * @param cause  the cause
     */
    public RestServiceException(HttpStatus status, Throwable cause) {
        super(cause);
        this.status = status;
    }

    /**
     * Instantiates a new Rest service exception.
     *
     * @param status  the status
     * @param message the message
     */
    public RestServiceException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    /**
     * Gets status.
     *
     * @return the status
     */
    public HttpStatus getStatus() {
        return status;
    }

}
