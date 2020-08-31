/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * TEMPLATE-BACKEND-SPRING-BOOT
 */
package cl.com.gesoft.workflow;

import net.kaczmarzyk.spring.data.jpa.web.SpecificationArgumentResolver;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.TimeZone;

/**
 * The type Project backend application.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
@SpringBootApplication
@EnableScheduling
public class ProjectBackendApplication implements WebMvcConfigurer {
    /**
     * The Log.
     */
    static final Log log = LogFactory.getLog(ProjectBackendApplication.class);

    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new SpecificationArgumentResolver());
        argumentResolvers.add(new PageableHandlerMethodArgumentResolver());
    }

    /**
     * Init.
     */
    @PostConstruct
    public void init(){
        // Setting Spring Boot SetTimeZone
        TimeZone.setDefault(TimeZone.getTimeZone("America/Monterrey"));
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        System.out.println("====================");
        System.out.println("PUT HERE GIT VERSION");
        System.out.println("====================");
        String env = System.getenv("env");
        if (StringUtils.isEmpty(env)) {
            log.info("Env is required");
        } else {
            log.info("Initializing environtment [" + env + "]");
        }
        for (String arg : args) {
            log.info("Iniciando con properties [" + arg + "]");
        }
        SpringApplication.run(ProjectBackendApplication.class, args);
    }
}
