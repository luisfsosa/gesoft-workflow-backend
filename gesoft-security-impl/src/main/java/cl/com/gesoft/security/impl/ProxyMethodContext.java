/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * TEMPLATE-BACKEND-SPRING-BOOT
 */
package cl.com.gesoft.security.impl;

import org.aopalliance.intercept.MethodInvocation;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * The type Proxy method context.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
public class ProxyMethodContext {
	private Map<String, Object> attrs = new LinkedHashMap<String, Object>();
	private MethodInvocation methodInvocation;

	/**
	 * Instantiates a new Proxy method context.
	 *
	 * @param methodInvocation the method invocation
	 */
	public ProxyMethodContext(MethodInvocation methodInvocation) {
		this.methodInvocation = methodInvocation;
	}

	/**
	 * Sets attribute.
	 *
	 * @param name  the name
	 * @param value the value
	 */
	public void setAttribute(String name, Object value) {
		attrs.put(name, value);
	}

	/**
	 * Gets attribute.
	 *
	 * @param name the name
	 * @return the attribute
	 */
	public Object getAttribute(String name) {
		return attrs.get(name);
	}

	/**
	 * Gets method invocation.
	 *
	 * @return the method invocation
	 */
	public MethodInvocation getMethodInvocation() {
		return methodInvocation;
	}
}
