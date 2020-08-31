/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * TEMPLATE-BACKEND-SPRING-BOOT
 */
package cl.com.gesoft.security.impl;

/**
 * The interface Proxy.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
public interface Proxy {
    /**
     * Prepare.
     *
     * @param context the context
     */
    public void prepare(ProxyMethodContext context);

    /**
     * Execute object.
     *
     * @param context the context
     * @return the object
     * @throws Throwable the throwable
     */
    public Object execute(ProxyMethodContext context) throws Throwable;

    /**
     * After execute.
     *
     * @param contsext the contsext
     * @param result   the result
     */
    public void afterExecute(ProxyMethodContext contsext, Object result);

}
