/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * TEMPLATE-BACKEND-SPRING-BOOT
 */
package cl.com.gesoft.workflow.web.log;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponseWrapper;
import javax.servlet.WriteListener;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;

/**
 * The type Apvas response wrapper.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
public class ApvasResponseWrapper extends ServletResponseWrapper implements HttpServletResponse {

	/**
	 * Constructs a response adaptor wrapping the given response.
	 *
	 * @throws IllegalArgumentException              if the response is null
	 */
	ApvasResponseWrapperOutputStream outputStreamWrapper = null;
	/**
	 * The Logger.
	 */
	ByteArrayOutputStream logger = new ByteArrayOutputStream();

	/**
	 * Instantiates a new Apvas response wrapper.
	 *
	 * @param response the response
	 * @throws IOException the io exception
	 */
	public ApvasResponseWrapper(HttpServletResponse response) throws IOException {
		super(response);
		outputStreamWrapper = new ApvasResponseWrapperOutputStream(response.getOutputStream(), logger);

	}

	private HttpServletResponse _getHttpServletResponse() {
		return (HttpServletResponse) super.getResponse();
	}

	/**
	 * The default behavior of this method is to call addCookie(Cookie cookie)
	 * on the wrapped response object.
	 */
	@Override
	public void addCookie(Cookie cookie) {
		this._getHttpServletResponse().addCookie(cookie);
	}

	/**
	 * The default behavior of this method is to call containsHeader(String
	 * name) on the wrapped response object.
	 */
	@Override
	public boolean containsHeader(String name) {
		return this._getHttpServletResponse().containsHeader(name);
	}

	/**
	 * The default behavior of this method is to call encodeURL(String url) on
	 * the wrapped response object.
	 */
	@Override
	public String encodeURL(String url) {
		return this._getHttpServletResponse().encodeURL(url);
	}

	/**
	 * The default behavior of this method is to return encodeRedirectURL(String
	 * url) on the wrapped response object.
	 */
	@Override
	public String encodeRedirectURL(String url) {
		return this._getHttpServletResponse().encodeRedirectURL(url);
	}

	/**
	 * The default behavior of this method is to call encodeUrl(String url) on
	 * the wrapped response object.
	 *
	 * @deprecated As of version 2.1, use {@link #encodeURL(String url)} instead
	 */
	@Override
	public String encodeUrl(String url) {
		return this._getHttpServletResponse().encodeUrl(url);
	}

	/**
	 * The default behavior of this method is to return encodeRedirectUrl(String
	 * url) on the wrapped response object.
	 *
	 * @deprecated As of version 2.1, use {@link #encodeRedirectURL(String url)}
	 *             instead
	 */
	@Override
	public String encodeRedirectUrl(String url) {
		return this._getHttpServletResponse().encodeRedirectUrl(url);
	}

	/**
	 * The default behavior of this method is to call sendError(int sc, String
	 * msg) on the wrapped response object.
	 */
	@Override
	public void sendError(int sc, String msg) throws IOException {
		this._getHttpServletResponse().sendError(sc, msg);
	}

	/**
	 * The default behavior of this method is to call sendError(int sc) on the
	 * wrapped response object.
	 */
	@Override
	public void sendError(int sc) throws IOException {
		this._getHttpServletResponse().sendError(sc);
	}

	/**
	 * The default behavior of this method is to return sendRedirect(String
	 * location) on the wrapped response object.
	 */
	@Override
	public void sendRedirect(String location) throws IOException {
		this._getHttpServletResponse().sendRedirect(location);
	}

	/**
	 * The default behavior of this method is to call setDateHeader(String name,
	 * long date) on the wrapped response object.
	 */
	@Override
	public void setDateHeader(String name, long date) {
		this._getHttpServletResponse().setDateHeader(name, date);
	}

	/**
	 * The default behavior of this method is to call addDateHeader(String name,
	 * long date) on the wrapped response object.
	 */
	@Override
	public void addDateHeader(String name, long date) {
		this._getHttpServletResponse().addDateHeader(name, date);
	}

	/**
	 * The default behavior of this method is to return setHeader(String name,
	 * String value) on the wrapped response object.
	 */
	@Override
	public void setHeader(String name, String value) {
		this._getHttpServletResponse().setHeader(name, value);
	}

	/**
	 * The default behavior of this method is to return addHeader(String name,
	 * String value) on the wrapped response object.
	 */
	@Override
	public void addHeader(String name, String value) {
		this._getHttpServletResponse().addHeader(name, value);
	}

	/**
	 * The default behavior of this method is to call setIntHeader(String name,
	 * int value) on the wrapped response object.
	 */
	@Override
	public void setIntHeader(String name, int value) {
		this._getHttpServletResponse().setIntHeader(name, value);
	}

	/**
	 * The default behavior of this method is to call addIntHeader(String name,
	 * int value) on the wrapped response object.
	 */
	@Override
	public void addIntHeader(String name, int value) {
		this._getHttpServletResponse().addIntHeader(name, value);
	}

	/**
	 * The default behavior of this method is to call setStatus(int sc) on the
	 * wrapped response object.
	 */
	@Override
	public void setStatus(int sc) {
		this._getHttpServletResponse().setStatus(sc);
	}

	/**
	 * The default behavior of this method is to call setStatus(int sc, String
	 * sm) on the wrapped response object.
	 *
	 * @deprecated As of version 2.1, due to ambiguous meaning of the message
	 *             parameter. To set a status code use {@link #setStatus(int)},
	 *             to send an error with a description use
	 *             {@link #sendError(int, String)}
	 */
	@Override
	public void setStatus(int sc, String sm) {
		this._getHttpServletResponse().setStatus(sc, sm);
	}

	/**
	 * The default behaviour of this method is to call
	 * {@link HttpServletResponse#getStatus} on the wrapped response object.
	 *
	 * @return the current status code of the wrapped response
	 */
	@Override
	public int getStatus() {
		return _getHttpServletResponse().getStatus();
	}

	/**
	 * The default behaviour of this method is to call
	 * {@link HttpServletResponse#getHeader} on the wrapped response object.
	 *
	 * @param name
	 *            the name of the response header whose value to return
	 *
	 * @return the value of the response header with the given name, or
	 *         <tt>null</tt> if no header with the given name has been set on
	 *         the wrapped response
	 *
	 * @since Servlet 3.0
	 */
	@Override
	public String getHeader(String name) {
		return _getHttpServletResponse().getHeader(name);
	}

	/**
	 * The default behaviour of this method is to call
	 * {@link HttpServletResponse#getHeaders} on the wrapped response object.
	 *
	 * <p>
	 * Any changes to the returned <code>Collection</code> must not affect this
	 * <code>HttpServletResponseWrapper</code>.
	 *
	 * @param name
	 *            the name of the response header whose values to return
	 *
	 * @return a (possibly empty) <code>Collection</code> of the values of the
	 *         response header with the given name
	 *
	 * @since Servlet 3.0
	 */
	@Override
	public Collection<String> getHeaders(String name) {
		return _getHttpServletResponse().getHeaders(name);
	}

	/**
	 * The default behaviour of this method is to call
	 * {@link HttpServletResponse#getHeaderNames} on the wrapped response
	 * object.
	 *
	 * <p>
	 * Any changes to the returned <code>Collection</code> must not affect this
	 * <code>HttpServletResponseWrapper</code>.
	 *
	 * @return a (possibly empty) <code>Collection</code> of the names of the
	 *         response headers
	 *
	 * @since Servlet 3.0
	 */
	@Override
	public Collection<String> getHeaderNames() {
		return _getHttpServletResponse().getHeaderNames();
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		return _getHttpServletResponse().getWriter();
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		// return _getHttpServletResponse().getOutputStream();

		return outputStreamWrapper;
	}

	/**
	 * Get response byte array byte [ ].
	 *
	 * @return the byte [ ]
	 */
	public byte[] getResponseByteArray() {
		return logger.toByteArray();
	}

	/**
	 * The type Apvas response wrapper output stream.
	 */
	class ApvasResponseWrapperOutputStream extends ServletOutputStream {
		/**
		 * The Target.
		 */
		ServletOutputStream target;
		/**
		 * The Logger.
		 */
		ByteArrayOutputStream logger = null;

		/**
		 * Instantiates a new Apvas response wrapper output stream.
		 *
		 * @param outputStream the output stream
		 * @param logger       the logger
		 */
		public ApvasResponseWrapperOutputStream(ServletOutputStream outputStream, ByteArrayOutputStream logger) {
			target = outputStream;
			this.logger = logger;
		}

		@Override
		public boolean isReady() {

			return target.isReady();
		}

		@Override
		public void setWriteListener(WriteListener writeListener) {
			target.setWriteListener(writeListener);

		}

		@Override
		public void write(int b) throws IOException {
			target.write(b);
		}

		@Override
		public void write(byte[] b) throws IOException {
			target.write(b);
		}

		@Override
		public void write(byte[] b, int off, int len) throws IOException {
			logger.write(b, off, len);
			target.write(b, off, len);
		}
	}

}
