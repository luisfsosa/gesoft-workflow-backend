/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * TEMPLATE-BACKEND-SPRING-BOOT
 */
package cl.com.gesoft.security.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * The interface Gesoft acl param.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER) // can use in method only.
public @interface GesoftAclParam {
    /**
     * Value string.
     *
     * @return the string
     */
    public String value();
}
