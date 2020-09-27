/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * TEMPLATE-BACKEND-SPRING-BOOT
 */
package cl.com.gesoft.security.api;

import java.util.Map;

/**
 * The interface Gesoft acl service.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
public interface GesoftAclService {
    /**
     * Acl boolean.
     *
     * @param permission the permission
     * @param params     the params
     * @return the boolean
     */
    public boolean acl(String permission, Map<String, Object> params);

    /**
     * Gets username.
     *
     * @return the username
     */
    public String getUsername();


}
