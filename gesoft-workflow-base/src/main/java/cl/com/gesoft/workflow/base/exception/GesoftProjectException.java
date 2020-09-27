/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * TEMPLATE-BACKEND-SPRING-BOOT
 */
package cl.com.gesoft.workflow.base.exception;


/**
 * The type Gesoft project exception.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
public class GesoftProjectException extends Exception {
    /**
     * The Severity.
     */
    protected String severity = "error";
    private String summary;
    private String detail;

    /**
     * Instantiates a new Gesoft work flow exception.
     *
     * @param summary the summary
     * @param detail  the detail
     */
    public GesoftProjectException(String summary, String detail) {
        super(detail);
        this.summary = summary;
        this.detail = detail;
    }

    /**
     * Instantiates a new GESOFT work flow exception.
     *
     * @param summary the summary
     * @param detail  the detail
     * @param cause   the cause
     */
    public GesoftProjectException(String summary, String detail, Throwable cause) {
        super(detail, cause);
        this.summary = summary;
        this.detail = detail;
    }

    /**
     * Gets summary.
     *
     * @return the summary
     */
    public String getSummary() {
        return summary;
    }

    /**
     * Sets summary.
     *
     * @param summary the summary
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * Gets detail.
     *
     * @return the detail
     */
    public String getDetail() {
        return detail;
    }

    /**
     * Sets detail.
     *
     * @param detail the detail
     */
    public void setDetail(String detail) {
        this.detail = detail;
    }

    /**
     * Gets severity.
     *
     * @return the severity
     */
    public String getSeverity() {
        return severity;
    }

}
