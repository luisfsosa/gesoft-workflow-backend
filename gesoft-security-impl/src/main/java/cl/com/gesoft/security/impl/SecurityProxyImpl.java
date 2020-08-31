/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * TEMPLATE-BACKEND-SPRING-BOOT
 */
package cl.com.gesoft.security.impl;

import cl.com.gesoft.security.api.GesoftACLException;
import cl.com.gesoft.security.api.GesoftAcl;
import cl.com.gesoft.security.api.GesoftAclParam;
import cl.com.gesoft.security.api.GesoftAclService;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;


/**
 * The type Security proxy.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
public class SecurityProxyImpl {
    /**
     * The Log.
     */
    Log log = LogFactory.getLog(SecurityProxyImpl.class);

    /**
     * The Application context.
     */
    @Autowired
    ApplicationContext applicationContext;

    /**
     * The Bean factory.
     */
    @Autowired
    AutowireCapableBeanFactory beanFactory;

    /**
     * The Quo acl service.
     */
    @Autowired
    GesoftAclService gesoftAclService;

    /**
     * Gets instance.
     *
     * @param <T>    the type parameter
     * @param t      the t
     * @param target the target
     * @return the instance
     * @throws Exception the exception
     */
    public <T> T getInstance(final Class<T> t, final Object target)
            throws Exception {
        final String clientId;

        beanFactory.autowireBean(target);
        beanFactory.initializeBean(target, UUID.randomUUID().toString());

        return getInstance(t, target, new Proxy() {
            @Override
            public void prepare(ProxyMethodContext proxyMethodContext) {
                //Do Nothing
            }

            @Override
            public Object execute(ProxyMethodContext proxyMethodContext) throws Throwable {
                MethodInvocation invocation = proxyMethodContext.getMethodInvocation();

                final GesoftAcl gesoftAcl = AnnotationUtils.findAnnotation(invocation.getMethod(),
                        GesoftAcl.class);

//                proxyMethodContext.setAttribute(CAN_CALL_METHOD, Boolean.FALSE);

                boolean call = false;

                String logString = "Call " + target.getClass().getCanonicalName() + "."
                        + invocation.getMethod().getName();

                String prefix = "(";

                for (Object o : invocation.getArguments()) {
                    logString += prefix + o;
                    prefix = ",";
                }
                logString += ");";

                if (gesoftAcl != null) {
                    // If ignore, method can be called
                    if (gesoftAcl.ignore()) {
                        call = true;
                    } else {
                        Map<String, Object> params = new LinkedHashMap<>();
                        Annotation[][] annotations = invocation.getMethod().getParameterAnnotations();

                        for (int i = 0; i < annotations.length; i++) {
                            for (int j = 0; j < annotations[i].length; j++) {
                                if (annotations[i][j] instanceof GesoftAclParam) {
                                    GesoftAclParam gesoftAclParam = (GesoftAclParam) annotations[i][j];
                                    params.put(gesoftAclParam.value(), invocation.getArguments()[i]);
                                }
                            }
                        }
                        if (!StringUtils.isEmpty(gesoftAcl.expression()) && gesoftAclService.acl(gesoftAcl.expression(), params)) {
                            call = true;
                        }
                    }
                }
                if (call) {
                    try {
                        String returnType = null;
                        // Object returnValue = audit(invocation);
                        Object returnValue = invocation.proceed();
                        if (returnValue != null) {
                            returnType = returnValue.getClass().getCanonicalName();
                        }
                        return returnValue;
                    } catch (Throwable e) {
                        log.error("Error calling "+logString);
                        log.error("=======================================");
                        log.error(e.getMessage(), e);
                        log.error("=======================================");
                        throw e;
                    }
                }
                throw new GesoftACLException("No ACL for Method  ["+invocation.getMethod().getName()+"]", invocation.getMethod().getName(), gesoftAcl, gesoftAclService.getUsername());
            }

            @Override
            public void afterExecute(ProxyMethodContext proxyMethodContext, Object result) {
                // TODO Auto-generated method stub

            }
        });

    }


    /**
     * Gets instance.
     *
     * @param <T>    the type parameter
     * @param t      the t
     * @param target the target
     * @param proxy  the proxy
     * @return the instance
     * @throws Exception the exception
     */
    protected <T> T getInstance(final Class<T> t, final Object target,
                                final Proxy proxy) throws Exception {
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setInterfaces(t);
        proxyFactoryBean.setTarget(target);
        // return (T) apvasProxyFactoryBean.getObject();

        proxyFactoryBean.addAdvice(new MethodInterceptor() {
            @Override
            public Object invoke(MethodInvocation invocation) throws Throwable {
                final ProxyMethodContext proxyMethodContext = new ProxyMethodContext(invocation);
                try {
                    proxy.prepare(proxyMethodContext);
                } catch (Exception e) {
                    throw e;
                }

                Object result = null;

                try {
                    result = proxy.execute(proxyMethodContext);
                } catch (Throwable e) {
                    throw e;
                } finally {
                    //must not send
                    proxy.afterExecute(proxyMethodContext, result);
                }
                return result;
            }
        });

        return t.cast(proxyFactoryBean.getObject());

    }

}
