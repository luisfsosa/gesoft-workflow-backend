/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * TEMPLATE-BACKEND-SPRING-BOOT
 */
package cl.com.gesoft.workflow.web;

import cl.com.gesoft.workflow.base.exception.GesoftProjectException;
import cl.com.gesoft.workflow.dao.WorkflowDAOSvc;
import cl.com.gesoft.workflow.dao.WorkflowDAOSvcImpl;
import cl.com.gesoft.workflow.dao.dto.TemplateDTO;
import cl.com.gesoft.workflow.model.Client;
import cl.com.gesoft.workflow.model.ProjectType;
import cl.com.gesoft.workflow.model.Template;
import cl.com.gesoft.workflow.model.User;
import cl.com.gesoft.workflow.security.WorkflowAuthManager;
import cl.com.gesoft.workflow.security.WorkflowAuthManagerImpl;
import cl.com.gesoft.workflow.service.ProjectSvc;
import cl.com.gesoft.workflow.service.ProjectSvcImpl;
import cl.com.gesoft.workflow.web.security.AuthenticationContextProvider;
import cl.com.gesoft.workflow.web.security.ProjectACLImpl;
import cl.com.gesoft.workflow.web.security.SSLFilter;
import cl.com.gesoft.workflow.web.security.SecurityInterceptor;
import cl.com.gesoft.security.api.AuthenticationContext;
import cl.com.gesoft.security.api.AuthenticationException;
import cl.com.gesoft.security.api.GesoftAclService;
import cl.com.gesoft.security.impl.SecurityProxyImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.apache.catalina.connector.Connector;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.sql.DataSource;
import java.util.*;

/**
 * The type Work flow rest controller.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
@Configuration
@RestController
@RequestMapping("/api/gesoft-workflow")
@CrossOrigin(origins = "*")
@EnableAutoConfiguration(exclude = { FlywayAutoConfiguration.class })
@EntityScan(basePackages = "cl.com.gesoft.workflow.model")
@EnableJpaRepositories(basePackages = "cl.com.gesoft.workflow.dao")
public class ProjectRestController {

    /**
     * The Log.
     */
    static final Log log = LogFactory.getLog(ProjectRestController.class);

    /**
     * The Workflow svc.
     */
    @Autowired
    private ProjectSvc projectSvc;


    /**
     * Ssl filter registration filter registration bean.
     *
     * @param sslFilter the ssl filter
     * @return the filter registration bean
     */
    @Bean
    public FilterRegistrationBean sslFilterRegistration(@Autowired SSLFilter sslFilter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(sslFilter);
        registration.setOrder(1);
        registration.addUrlPatterns("/*");
        return registration;
    }

    @Value("${server.additionalPorts}")
    private String additionalPorts;

    /**
     * Servlet container servlet web server factory.
     *
     * @return the servlet web server factory
     */
    @Bean // (it only works for springboot 2.x)
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        Connector[] additionalConnectors = this.additionalConnector();
        if (additionalConnectors != null && additionalConnectors.length > 0) {
            tomcat.addAdditionalTomcatConnectors(additionalConnectors);
        }
        return tomcat;
    }

    private Connector[] additionalConnector() {
        if (StringUtils.isBlank(this.additionalPorts)) {
            return null;
        }
        String[] ports = this.additionalPorts.split(",");
        List<Connector> result = new ArrayList<>();
        for (String port : ports) {
            Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
            connector.setScheme("http");
            connector.setPort(Integer.valueOf(port));
            result.add(connector);
        }
        return result.toArray(new Connector[] {});
    }

    /**
     * Create web mvc configuration support web mvc configurer.
     *
     * @return the web mvc configurer
     */
    @Bean
    WebMvcConfigurer createWebMvcConfigurationSupport() {
        WebMvcConfigurer webMvcConfigurer = new RestConfigurer() {
            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(createSecurityInterceptor()).addPathPatterns("/api/gesoft-workflow/**")
                        .excludePathPatterns("/public/*", "/api/gesoft-workflow/login").excludePathPatterns("/api/gesoft-workflow/public/**");
                super.addInterceptors(registry);
            }

            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
//                registry.addResourceHandler("/resourcess/**").setCachePeriod(0);
                registry.addResourceHandler("/static/**").setCacheControl(CacheControl.noCache());
                super.addResourceHandlers(registry);
            }
        };
        return webMvcConfigurer;
    }

    /**
     * Create security interceptor security interceptor.
     *
     * @return the security interceptor
     */
    @Bean
    public SecurityInterceptor createSecurityInterceptor() {
        return new SecurityInterceptor();
    }

    /**
     * Create authentication context provider authentication context provider.
     *
     * @param sessionSecret the session secret
     * @return the authentication context provider
     */
    @Bean
    AuthenticationContextProvider createAuthenticationContextProvider(
            @Value("${session.secret}") String sessionSecret) {
        return new AuthenticationContextProvider() {
            @Override
            public AuthenticationContext createAuthenticationContext(String authKey, Object request)
                    throws Exception {

                if (StringUtils.isEmpty(authKey)) {
                    throw new AuthenticationException("Auth-Key es requerido", null,
                            HttpStatus.UNPROCESSABLE_ENTITY.value());
                }

                return authenticateWithAuthkey(sessionSecret, authKey);
            }

            @Override
            public AuthenticationContext getAuthenticationContext() {
                return null;
            }

            @Override
            public AuthenticationContext destroy() {
                AuthenticationContext authenticationContext = new AuthenticationContext() {
                    @Override
                    public Object getPrincipal() {
                        return "xxx";
                    }
                };
                return authenticationContext;
            }
        };
    }

    private AuthenticationContext authenticateWithAuthkey(String sessionSecret, String authKey) throws Exception {

        System.out.println("voy a descifrar");
        System.out.println("el secret es" + sessionSecret);
        Jws<Claims> jws = Jwts.parser().setSigningKey(sessionSecret).parseClaimsJws(authKey);
        System.out.println("obtuve el jwt" + jws);

        Integer sessionId = (Integer) jws.getHeader().get("sessionId");
        String username = (String) jws.getHeader().get("username");
        String rol = (String) jws.getHeader().get("rol");
        Integer clientId = (Integer) jws.getHeader().get("clientId");
        String userId = jws.getBody().getSubject();

        Claims claims = jws.getBody();

        User user = new User();
        user.setId(Long.parseLong(userId));
        user.setName(username);
        user.setEmail(username);
        user.setRol(rol);
        user.setClientId(clientId);

        System.out.println("cree el usuario " + user);

        ProjectAuthentication projectAuthentication = new ProjectAuthentication(user, sessionId);
        projectAuthentication.setUser(user);

        SecurityContextHolder.createEmptyContext();
        SecurityContextHolder.getContext().setAuthentication(projectAuthentication);
        return projectAuthentication;
    }

    /**
     * The type Work flow authentication.
     */
    class ProjectAuthentication implements AuthenticationContext, Authentication {

        private static final long serialVersionUID = -3891201699981417334L;

        /**
         * The Principal.
         */
        Object principal;
        /**
         * The User.
         */
        User user;
        /**
         * The Session id.
         */
        Integer sessionId;

        /**
         * Instantiates a new Work flow authentication.
         *
         * @param principal the principal
         * @param sessionId the session id
         */
        public ProjectAuthentication(Object principal, Integer sessionId) {
            this.principal = principal;
            this.sessionId = sessionId;
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return null;
        }

        @Override
        public Object getCredentials() {
            return null;
        }

        @Override
        public Object getDetails() {
            return null;
        }

        @Override
        public Object getPrincipal() {
            return principal;
        }

        @Override
        public boolean isAuthenticated() {
            return true;
        }

        @Override
        public void setAuthenticated(boolean b) throws IllegalArgumentException {

        }

        @Override
        public String getName() {
            return user.getName();
        }

        /**
         * Gets session id.
         *
         * @return the session id
         */
        public Integer getSessionId() {
            return sessionId;
        }

        /**
         * Sets user.
         *
         * @param user the user
         */
        public void setUser(User user) {
            this.user = user;
        }

        /**
         * Gets user.
         *
         * @return the user
         */
        public User getUser() {
            return user;
        }
    }

    /**
     * Create security proxy security proxy.
     *
     * @return the security proxy
     */
    @Bean
    SecurityProxyImpl createSecurityProxyImpl() {
        return new SecurityProxyImpl();
    }

    /**
     * Create work flow acl work flow acl.
     *
     * @return the work flow acl
     */
    @Bean
    ProjectACLImpl createProjectACLImpl() {
        return new ProjectACLImpl();
    }


    /**
     * Create GESOFT acl service GESOFT acl service.
     *
     * @param ProjectACLImpl the project acl
     * @return the Gesoft acl service
     */
    @Bean
    GesoftAclService createGesoftAclService(@Autowired ProjectACLImpl ProjectACLImpl) {
        return new GesoftAclService() {
            @Override
            public boolean acl(String expression, Map<String, Object> params) {
                log.info("Validando ACL [" + expression + "]");
                System.out.println("si se esta llamando el ACL " + expression );
                ExpressionParser parser = new SpelExpressionParser();
                StandardEvaluationContext context = new StandardEvaluationContext();
                context.setVariable("acl", ProjectACLImpl);

                if (params != null) {
                    for (String key : params.keySet()) {
                        context.setVariable(key, params.get(key));
                    }
                }

                org.springframework.expression.Expression exp = parser.parseExpression(expression);
                return (Boolean) exp.getValue(context);

            }

            @Override
            public String getUsername() {
                return "example";
            }
        };
    }

    /**
     * Create workflow svc work flow svc.
     *
     * @param environment   the environment
     * @param securityProxy the security proxy
     * @param datasource    the datasource
     * @return the work flow svc
     * @throws Exception the exception
     */
    @Bean
    @Order(10)
    ProjectSvc createProjectSvc(Environment environment, @Autowired SecurityProxyImpl securityProxy, DataSource datasource) throws Exception {
        System.out.println("########################################################################");
        System.out.println("######################## Work Flow Svc Was Created ########################");
        System.out.println("########################################################################");
        return securityProxy.getInstance(ProjectSvc.class, new ProjectSvcImpl(datasource));
    }

    @Bean
	WorkflowDAOSvc createWorkflowDAOSvc() {
		return new WorkflowDAOSvcImpl();
	}

    @Bean
	WorkflowAuthManager createWorkflowAuthManager(Environment environment) {
		WorkflowAuthManager authManager = new WorkflowAuthManagerImpl(environment.getProperty("Session.secret"));
		return authManager;
	}

    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public @ResponseBody Object login(@RequestBody Map<String, String> requestBody) throws Exception {
        return projectSvc.login(requestBody.get("username"), requestBody.get("password"));
    }

    @PostMapping("/users")
    public List<User> findAllUsers(@RequestBody List<String> rolNames) throws Exception {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return projectSvc.findAllUsersByRolAndClientId(rolNames,user.getClientId());
        }
        return new ArrayList<>();
    }

    @GetMapping("/clients")
    public List<Client> findAllClients() throws Exception {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return projectSvc.findAllClientsByClientId(user.getClientId());
        }
        return new ArrayList<>();
    }

    @PostMapping("/clients")
    public Client createClient(@RequestBody Client client) throws Exception {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return projectSvc.createClient(user.getClientId(), client);
        } else {
            throw new GesoftProjectException("Clientes", "clientId es requerido");
        }
    }

    @PutMapping("/clients/{workFlowClientId}")
    public Client updateClient(@PathVariable Long workFlowClientId, @RequestBody Client client) throws Exception {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return projectSvc.updateClient(user.getClientId(), workFlowClientId, client);
        } else {
            throw new GesoftProjectException("Clientes", "clientId es requerido");
        }
    }

    @PutMapping("/clients/{workFlowClientId}/state")
    public Client updateClientState(@PathVariable Long workFlowClientId, @RequestBody Client client) throws Exception {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return projectSvc.updateClientState(user.getClientId(), workFlowClientId, client);
        } else {
            throw new GesoftProjectException("Clientes", "clientId es requerido");
        }
    }

    @GetMapping("/project-types")
    public List<ProjectType> findAllProjectTypes() throws Exception {
        return projectSvc.findAllProjectTypes();
    }


    @GetMapping("/project-types/{projectTypeId}")
    public ProjectType findProjectTypeById(@PathVariable Integer projectTypeId) throws Exception {
        return projectSvc.findProjectTypeById(projectTypeId);
    }

    @GetMapping("/templates")
    public List<TemplateDTO> findAllTemplates() throws Exception {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return projectSvc.findAllTemplatesByClientId(user.getClientId());
        }
        return new ArrayList<>();
    }

    @PostMapping("/templates")
    public Template createTemplate(@RequestBody Template template) throws Exception {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return projectSvc.createTemplate(user.getClientId(), template);
        } else {
            throw new GesoftProjectException("Plantillas", "clientId es requerido");
        }
    }

    @PutMapping("/templates/{templateId}")
    public Template updateTemplate(@PathVariable Long templateId, @RequestBody Template template) throws Exception {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return projectSvc.updateTemplate(user.getClientId(), templateId, template);
        } else {
            throw new GesoftProjectException("Plantillas", "clientId es requerido");
        }
    }
}
