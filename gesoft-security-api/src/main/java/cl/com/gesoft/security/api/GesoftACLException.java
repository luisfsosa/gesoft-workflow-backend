/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * TEMPLATE-BACKEND-SPRING-BOOT
 */
package cl.com.gesoft.security.api;

/**
 * The type Gesoft acl exception.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
public class GesoftACLException extends RuntimeException {
    private String methodName;
    private GesoftAcl acl;
    private String authKey;

    /**
     * Instantiates a new Gesoft acl exception.
     *
     * @param message    the message
     * @param methodName the method name
     * @param acl        the acl
     * @param authKey    the auth key
     */
    public GesoftACLException(String message, String methodName, GesoftAcl acl, String authKey) {
        super(message);
        this.methodName = methodName;
        this.acl = acl;
        this.authKey = authKey;
    }

    /**
     * Gets method name.
     *
     * @return the method name
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * Gets acl.
     *
     * @return the acl
     */
    public GesoftAcl getAcl() {
        return acl;
    }

    /**
     * Gets auth key.
     *
     * @return the auth key
     */
    public String getAuthKey() {
        return authKey;
    }
}
