/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * TEMPLATE-BACKEND-SPRING-BOOT
 */
package cl.com.gesoft.workflow.base;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

/**
 * The type Http helper.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
public class HttpHelper {
    /**
     * The constant APPLICATION_JSON_UTF_8.
     */
    public static final String APPLICATION_JSON_UTF_8 = "application/json";

    private RestTemplate restTemplate;
    private String urlBase;

    /**
     * Instantiates a new Http helper.
     *
     * @param restTemplate the rest template
     * @param urlBase      the url base
     */
    public HttpHelper(RestTemplate restTemplate, String urlBase) {
        this.restTemplate = restTemplate;
        this.urlBase = urlBase;
    }

    /**
     * Process t.
     *
     * @param <T>         the type parameter
     * @param t           the t
     * @param method      the method
     * @param page        the page
     * @param queryString the query string
     * @param body        the body
     * @return the t
     * @throws Exception the exception
     */
    protected <T> T process(Class<T> t, HttpMethod method, String page, final String queryString, Object body) throws Exception {
        final String url;

        if (StringUtils.isEmpty(queryString))
            url = urlBase + page;
        else
            url = urlBase + page + "?" + queryString;

        System.out.println("Query->" + url);

        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.put(CONTENT_TYPE, Arrays.asList(new String[]{APPLICATION_JSON_UTF_8}));
        HttpEntity request = new HttpEntity<>(body, headers);
        System.out.println("Process URL -> [" + url + "]");
        try {
            ResponseEntity<T> responseEntity = restTemplate.exchange(url, method, request, t);
            return responseEntity.getBody();
        } catch (HttpServerErrorException e) {
            System.out.println("Response Error Body [" + e.getResponseBodyAsString() + "]");
            throw e;
        } catch (HttpClientErrorException e) {
            System.out.println("Response Error Body [" + e.getResponseBodyAsString() + "]");
            throw e;
        } catch (Exception e) {
            System.out.println("Response Error Body [NO RESPONSE]" + e.getClass().getCanonicalName());
            throw e;
        }
    }
}