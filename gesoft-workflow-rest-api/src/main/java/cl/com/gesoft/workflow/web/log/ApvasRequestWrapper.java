/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * TEMPLATE-BACKEND-SPRING-BOOT
 */
package cl.com.gesoft.workflow.web.log;

import org.apache.commons.io.IOUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

/**
 * The type Apvas request wrapper.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
public class ApvasRequestWrapper implements HttpServletRequest {

	/**
	 * The Target.
	 */
	HttpServletRequest target;

	/**
	 * Instantiates a new Apvas request wrapper.
	 *
	 * @param target the target
	 */
	public ApvasRequestWrapper(HttpServletRequest target) {
		super();
		this.target = target;
	}

	@Override
	public Object getAttribute(String name) {
		return target.getAttribute(name);
	}

	@Override
	public Enumeration<String> getAttributeNames() {
		// TODO Auto-generated method stub
		return target.getAttributeNames();
	}

	@Override
	public String getCharacterEncoding() {
		return target.getCharacterEncoding();
	}

	@Override
	public void setCharacterEncoding(String env) throws UnsupportedEncodingException {
		target.setCharacterEncoding(env);

	}

	@Override
	public int getContentLength() {
		return target.getContentLength();
	}

	@Override
	public long getContentLengthLong() {
		return target.getContentLengthLong();
	}

	@Override
	public String getContentType() {
		return target.getContentType();
	}

	/**
	 * The Out.
	 */
	ByteArrayOutputStream out = null;

	@Override
	public ServletInputStream getInputStream() throws IOException {
		if (out == null) {
			out = new ByteArrayOutputStream();
			IOUtils.copy(target.getInputStream(), out);
		}
		final ByteArrayInputStream inputStream = new ByteArrayInputStream(out.toByteArray());
		final ServletInputStream targetInputStream = target.getInputStream();
		return new ServletInputStream() {
			@Override
			public boolean isFinished() {
				return false;
			}

			@Override
			public boolean isReady() {
				return false;
			}

			@Override
			public void setReadListener(ReadListener readListener) {
				targetInputStream.setReadListener(readListener);
			}

			@Override
			public int read() throws IOException {
				return inputStream.read();
			}

			@Override
			public int read(byte[] b) throws IOException {
				return inputStream.read(b);
			}

			@Override
			public int read(byte[] b, int off, int len) throws IOException {
				return inputStream.read(b, off, len);
			}

			@Override
			public synchronized void reset() throws IOException {
				inputStream.reset();
			}
		};
	}

	@Override
	public String getParameter(String name) {

		return target.getParameter(name);
	}

	@Override
	public Enumeration<String> getParameterNames() {
		return target.getParameterNames();
	}

	@Override
	public String[] getParameterValues(String name) {
		return target.getParameterValues(name);
	}

	@Override
	public Map<String, String[]> getParameterMap() {
		return target.getParameterMap();
	}

	@Override
	public String getProtocol() {
		return target.getProtocol();
	}

	@Override
	public String getScheme() {
		return target.getScheme();
	}

	@Override
	public String getServerName() {
		return target.getServerName();
	}

	@Override
	public int getServerPort() {

		return target.getServerPort();
	}

	@Override
	public BufferedReader getReader() throws IOException {
		throw new RuntimeException("Method not yet implemented");
	}

	@Override
	public String getRemoteAddr() {
		return target.getRemoteAddr();
	}

	@Override
	public String getRemoteHost() {
		// TODO Auto-generated method stub
		return target.getRemoteHost();
	}

	@Override
	public void setAttribute(String name, Object o) {
		target.setAttribute(name, o);

	}

	@Override
	public void removeAttribute(String name) {
		target.removeAttribute(name);

	}

	@Override
	public Locale getLocale() {
		return target.getLocale();
	}

	@Override
	public Enumeration<Locale> getLocales() {
		return target.getLocales();
	}

	@Override
	public boolean isSecure() {
		return target.isSecure();
	}

	@Override
	public RequestDispatcher getRequestDispatcher(String path) {
		return target.getRequestDispatcher(path);
	}

	@Override
	public String getRealPath(String path) {
		return target.getRealPath(path);
	}

	@Override
	public int getRemotePort() {
		return target.getRemotePort();
	}

	@Override
	public String getLocalName() {
		return target.getLocalName();
	}

	@Override
	public String getLocalAddr() {
		return target.getLocalAddr();
	}

	@Override
	public int getLocalPort() {
		return target.getLocalPort();
	}

	@Override
	public ServletContext getServletContext() {
		return target.getServletContext();
	}

	@Override
	public AsyncContext startAsync() throws IllegalStateException {
		return target.startAsync();
	}

	@Override
	public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse)
			throws IllegalStateException {
		return target.startAsync();
	}

	@Override
	public boolean isAsyncStarted() {
		return target.isAsyncStarted();
	}

	@Override
	public boolean isAsyncSupported() {
		return target.isAsyncSupported();
	}

	@Override
	public AsyncContext getAsyncContext() {
		return target.getAsyncContext();
	}

	@Override
	public DispatcherType getDispatcherType() {
		return target.getDispatcherType();
	}

	@Override
	public String getAuthType() {
		return target.getAuthType();
	}

	@Override
	public Cookie[] getCookies() {
		return target.getCookies();
	}

	@Override
	public long getDateHeader(String name) {
		return target.getDateHeader(name);
	}

	@Override
	public String getHeader(String name) {
		return target.getHeader(name);
	}

	@Override
	public Enumeration<String> getHeaders(String name) {
		return target.getHeaders(name);
	}

	@Override
	public Enumeration<String> getHeaderNames() {
		return target.getHeaderNames();
	}

	@Override
	public int getIntHeader(String name) {
		return target.getIntHeader(name);
	}

	@Override
	public String getMethod() {
		return target.getMethod();
	}

	@Override
	public String getPathInfo() {
		return target.getPathInfo();
	}

	@Override
	public String getPathTranslated() {
		return target.getPathTranslated();
	}

	@Override
	public String getContextPath() {
		return target.getContextPath();
	}

	@Override
	public String getQueryString() {
		return target.getQueryString();
	}

	@Override
	public String getRemoteUser() {
		return target.getRemoteUser();
	}

	@Override
	public boolean isUserInRole(String role) {
		return target.isUserInRole(role);
	}

	@Override
	public Principal getUserPrincipal() {
		return target.getUserPrincipal();
	}

	@Override
	public String getRequestedSessionId() {
		return target.getRequestedSessionId();
	}

	@Override
	public String getRequestURI() {
		return target.getRequestURI();
	}

	@Override
	public StringBuffer getRequestURL() {
		return target.getRequestURL();
	}

	@Override
	public String getServletPath() {
		return target.getServletPath();
	}

	@Override
	public HttpSession getSession(boolean create) {
		return target.getSession(create);
	}

	@Override
	public HttpSession getSession() {
		return target.getSession();
	}

	@Override
	public String changeSessionId() {
		return target.changeSessionId();
	}

	@Override
	public boolean isRequestedSessionIdValid() {
		return target.isRequestedSessionIdValid();
	}

	@Override
	public boolean isRequestedSessionIdFromCookie() {
		return target.isRequestedSessionIdFromCookie();
	}

	@Override
	public boolean isRequestedSessionIdFromURL() {
		return target.isRequestedSessionIdFromURL();
	}

	@Override
	public boolean isRequestedSessionIdFromUrl() {
		return target.isRequestedSessionIdFromUrl();
	}

	@Override
	public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
		return target.authenticate(response);
	}

	@Override
	public void login(String username, String password) throws ServletException {
		target.login(username, password);
	}

	@Override
	public void logout() throws ServletException {
		target.logout();

	}

	@Override
	public Collection<Part> getParts() throws IOException, ServletException {
		return target.getParts();
	}

	@Override
	public Part getPart(String name) throws IOException, ServletException {
		return target.getPart(name);
	}

	@Override
	public <T extends HttpUpgradeHandler> T upgrade(Class<T> handlerClass) throws IOException, ServletException {
		return target.upgrade(handlerClass);
	}

}
