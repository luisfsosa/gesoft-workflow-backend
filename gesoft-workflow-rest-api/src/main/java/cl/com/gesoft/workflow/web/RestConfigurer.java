/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * TEMPLATE-BACKEND-SPRING-BOOT
 */
package cl.com.gesoft.workflow.web;

import cl.com.gesoft.workflow.base.exception.GesoftProjectException;
import cl.com.gesoft.workflow.base.exception.GesoftProjectWarningException;
import cl.com.gesoft.workflow.web.log.LogFilter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * The type Rest configurer.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
@ControllerAdvice
@EnableWebMvc
public class RestConfigurer extends WebMvcConfigurerAdapter {
    /**
     * The Log.
     */
    static final Log log = LogFactory.getLog(RestConfigurer.class);

    /**
     * The constant ID.
     */
    public static final String ID = "id";
    /**
     * The constant SEVERITY.
     */
    public static final String SEVERITY = "severity";
    /**
     * The constant SUMMARY.
     */
    public static final String SUMMARY = "summary";
    /**
     * The constant DETAIL.
     */
    public static final String DETAIL = "detail";
    /**
     * The constant TRACKING_NUMBER.
     */
    public static final String TRACKING_NUMBER = "errorId";
    /**
     * The constant DETAILS.
     */
    public static final String DETAILS = "details";
    /**
     * The constant TYPE.
     */
    public static final String TYPE = "type";
    /**
     * The constant TIMESTAMP_FORMATER.
     */
    public static final DateFormat TIMESTAMP_FORMATER = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * The Interceptors.
     */
    List<HandlerInterceptor> interceptors = new LinkedList<>();

    /**
     * Instantiates a new Rest configurer.
     */
    public RestConfigurer() {
    }

    /**
     * Instantiates a new Rest configurer.
     *
     * @param interceptors the interceptors
     */
    public RestConfigurer(List<HandlerInterceptor> interceptors) {
        this.interceptors = interceptors;
    }

    /**
     * Init.
     */
    @PostConstruct
    public void init() {

    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        for (HandlerInterceptor interceptor : interceptors) {
            registry.addInterceptor(interceptor);
        }
        super.addInterceptors(registry);
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.serializationInclusion(JsonInclude.Include.NON_NULL);
        builder.serializationInclusion(JsonInclude.Include.NON_NULL);
        builder.featuresToEnable(JsonParser.Feature.ALLOW_SINGLE_QUOTES);
        builder.featuresToEnable(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES);
//        builder.indentOutput(true).dateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
        
        ResourceHttpMessageConverter resource = new ResourceHttpMessageConverter();
        converters.add(resource);
        super.configureMessageConverters(converters);
    }

    /**
     * Process Gesoft work flow warning exception map.
     *
     * @param request  the request
     * @param response the response
     * @param e        the e
     * @return the map
     */
    @ExceptionHandler(GesoftProjectWarningException.class)
    public @ResponseBody
    Map<String, Object> processGesoftProjectWarningException(HttpServletRequest request,
                                                           HttpServletResponse response, GesoftProjectWarningException e) {
        Map<String, Object> errorObject = new LinkedHashMap<>();
        errorObject.put(ID,
                TIMESTAMP_FORMATER.format(new Date(System.currentTimeMillis())) + "-" + UUID.randomUUID());
        errorObject.put(SEVERITY, e.getSeverity());
        errorObject.put(SUMMARY, e.getSummary());
        errorObject.put(DETAIL, e.getDetail());
        errorObject.put(TYPE, e.getClass().getCanonicalName());
        response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        return errorObject;
    }

    /**
     * Process Gesoft work flow exception map.
     *
     * @param response the response
     * @param e        the e
     * @return the map
     */
    @ExceptionHandler(GesoftProjectException.class)
    public @ResponseBody
    Map<String, Object> processGesoftProjectException(HttpServletResponse response, GesoftProjectException e) {
        Map<String, Object> errorObject = new LinkedHashMap<>();
        errorObject.put(ID,
                TIMESTAMP_FORMATER.format(new Date(System.currentTimeMillis())) + "-" + UUID.randomUUID());
        errorObject.put(SEVERITY, e.getSeverity());
        errorObject.put(SUMMARY, e.getSummary());
        errorObject.put(DETAIL, e.getDetail());
        errorObject.put(TYPE, e.getClass().getCanonicalName());
        response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        return errorObject;
    }


    /**
     * Apvas platform exception map.
     *
     * @param response the response
     * @param e        the e
     * @return the map
     */
    @ExceptionHandler(RestServiceException.class)
    public
    @ResponseBody
    Map<String, Object> apvasPlatformException(HttpServletResponse response, RestServiceException e) {
        log.error(e.getMessage(), e);
        Map<String, Object> errorObject = new LinkedHashMap<>();
        errorObject.put(DETAIL, e.getMessage());
        errorObject.put(SEVERITY, 5001);
        errorObject.put(TRACKING_NUMBER, MDC.get(LogFilter.REQUEST_ID));
        response.setStatus(e.getStatus().value());
        return errorObject;
    }

    /**
     * Apvas platform exception map.
     *
     * @param response the response
     * @param e        the e
     * @return the map
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public
    @ResponseBody
    Map<String, Object> apvasPlatformException(HttpServletResponse response, HttpMessageNotReadableException e) {
        final Throwable cause = e.getCause();
        String mensaje = "No se pudo leer correctamente el Documento. Existen valores incorrectos";
        if (cause == null) {

        } else if (cause instanceof JsonParseException) {
            JsonParseException jsonParseException = (JsonParseException) cause;
        } else if (cause instanceof InvalidFormatException) {
            InvalidFormatException invalidFormatException = (InvalidFormatException) cause;
            for (JsonMappingException.Reference reference : invalidFormatException.getPath()){
                mensaje = "El campo ["+reference.getFieldName()+"] tiene valor inválido";
            }
            mensaje += "["+invalidFormatException.getValue()+"]";
        }
        else if (cause instanceof JsonMappingException) {
            JsonMappingException jsonMappingException = (JsonMappingException) cause;
            for (JsonMappingException.Reference reference : jsonMappingException.getPath()){
                mensaje = "El campo ["+reference.getFieldName()+"] tiene valor inválido";
            }
        }
        else {

        }
        Map<String, Object> errorObject = new LinkedHashMap<>();
        errorObject.put(DETAIL, mensaje);
        errorObject.put(SEVERITY, 5001);
        errorObject.put(TRACKING_NUMBER, MDC.get(LogFilter.REQUEST_ID));
        if (e.getMostSpecificCause() != null) {
            log.error(e.getMostSpecificCause().getMessage(), e.getMostSpecificCause());
            errorObject.put(DETAILS, e.getMostSpecificCause().getMessage());
        } else {
            log.error(e.getMessage(), e);
        }

        return errorObject;
    }


}
