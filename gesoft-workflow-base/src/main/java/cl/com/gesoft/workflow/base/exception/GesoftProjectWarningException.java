/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * TEMPLATE-BACKEND-SPRING-BOOT
 */
package cl.com.gesoft.workflow.base.exception;


/**
 * The type Gesoft project warning exception.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
public class GesoftProjectWarningException extends GesoftProjectException {


    /**
     * Instantiates a new Quo work flow warning exception.
     *
     * @param summary the summary
     * @param detail  the detail
     */
    public GesoftProjectWarningException(String summary, String detail) {
        super(summary, detail);
        this.severity = "warning";
    }
}
