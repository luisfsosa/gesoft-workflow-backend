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
import cl.com.gesoft.workflow.dao.dto.ActivityDTO;
import cl.com.gesoft.workflow.dao.dto.ProjectDTO;
import cl.com.gesoft.workflow.dao.dto.ProjectExecutionDTO;
import cl.com.gesoft.workflow.dao.dto.TemplateDTO;
import cl.com.gesoft.workflow.model.*;
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
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.GreaterThan;
import net.kaczmarzyk.spring.data.jpa.domain.In;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/clients/active")
    public List<Client> findAllActiveClients() throws Exception {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return projectSvc.findAllActiveClientsByClientId(user.getClientId());
        }
        return new ArrayList<>();
    }

    @GetMapping("/clients/{workFlowClientId}")
    public Client findClientById(@PathVariable Long workFlowClientId) throws Exception {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return projectSvc.findClientById(user.getClientId(), workFlowClientId);
        }
        return new Client();
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

    @GetMapping("/project-types/{projectTypeId}/templates")
    public List<Template> findTemplateByClientIdAndProjectTypeId(@PathVariable Long projectTypeId) throws Exception {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return projectSvc.findTemplateByClientIdAndProjectTypeId(user.getClientId(), projectTypeId);
        }
        return new ArrayList<>();
    }

    @GetMapping("/templates/{templateId}/activities")
    public List<ActivityDTO> findActivityDTOByClientIdAndTemplateId(@PathVariable Long templateId) throws Exception {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return projectSvc.findActivityDTOByClientIdAndTemplateId(user.getClientId(), templateId);
        }
        return new ArrayList<>();
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

    @GetMapping("/projects")
    public List<ProjectDTO> findAllProjects() throws Exception {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return projectSvc.findAllProjectsByClientId(user.getClientId());
        }
        return new ArrayList<>();
    }

    @GetMapping("/projects/{projectId}")
    public Project findProjectById(@PathVariable Long projectId) throws Exception {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return projectSvc.findProjectById(user.getClientId(), projectId);
        }
        return new Project();
    }


    @GetMapping("/projects/{projectId}/activities")
    public List<ActivityDTO> findActivityDTOByClientIdAndProjectId(@PathVariable Long projectId) throws Exception {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return projectSvc.findActivityDTOByClientIdAndProjectId(user.getClientId(), projectId);
        }
        return new ArrayList<>();
    }

    @GetMapping("/projects/{projectId}/project-executions")
    public List<ProjectExecution> findProjectExecutionsByProjectId(@PathVariable Long projectId) throws Exception {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return projectSvc.findProjectExecutionsByProjectId(user.getClientId(), projectId);
        }
        return new ArrayList<>();
    }

    @PostMapping("/projects")
    public Project createProject(@RequestBody Project project) throws Exception {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return projectSvc.createProject(user.getClientId(), project);
        } else {
            throw new GesoftProjectException("Proyectos", "clientId es requerido");
        }
    }

    @PutMapping("/projects/{projectId}")
    public Project updateProject(@PathVariable Long projectId, @RequestBody Project project) throws Exception {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return projectSvc.updateProject(user.getClientId(), projectId, project);
        } else {
            throw new GesoftProjectException("Proyectos", "clientId es requerido");
        }
    }

    @DeleteMapping("/projects/{projectId}")
    public ResponseEntity<?> deleteProject(@PathVariable Long projectId) throws Exception {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return projectSvc.deleteProject(user.getClientId(), projectId);
        } else {
            throw new GesoftProjectException("Proyectos", "clientId es requerido");
        }
    }

    @GetMapping("/project-executions")
    public List<ProjectExecutionDTO> findAllProjectsExecutions(
            @RequestParam  Integer projectTypeId,
            @RequestParam  Integer yearSince,
            @RequestParam(required = false)  String periodSince) throws Exception {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return projectSvc.findProjectDTOByClientIdAndProjectTypeIdAndYearSinceAndPeriodSince(user.getClientId(), projectTypeId,yearSince, periodSince );
        }
        return new ArrayList<>();
    }

    /**
     * Find all workflow views page.
     *
     * @param workFlowViewSpec the work flow view spec
     * @param pageable         the pageable
     * @return the page
     * @throws Exception the exception
     */
    @GetMapping("/worflow-views")
    public Page<WorkflowView> findAllWorkflowViews(@And({
            @Spec(path = "id", spec = Equal.class),
            @Spec(path = "year", spec = Equal.class),
            @Spec(path = "period", spec = Equal.class),
            @Spec(path = "templateId", spec = Equal.class),
            @Spec(path = "projectTypeId", spec = Equal.class),
            @Spec(path = "clientId", spec = Equal.class),
            @Spec(path = "commentsNumber", spec = GreaterThan.class),
            @Spec(path = "attachedNumber", spec = GreaterThan.class),
            @Spec(path = "pendingNumber", spec = GreaterThan.class),
            @Spec(path = "rejectedNumber", spec = GreaterThan.class),
            @Spec(path = "activity1", spec = In.class),
            @Spec(path = "activity2", spec = In.class),
            @Spec(path = "activity3", spec = In.class),
            @Spec(path = "activity4", spec = In.class),
            @Spec(path = "activity5", spec = In.class),
            @Spec(path = "activity6", spec = In.class),
            @Spec(path = "activity7", spec = In.class),
            @Spec(path = "activity8", spec = In.class),
            @Spec(path = "activity9", spec = In.class),
            @Spec(path = "activity10", spec = In.class),
            @Spec(path = "activity11", spec = In.class),
            @Spec(path = "activity12", spec = In.class),
            @Spec(path = "activity13", spec = In.class),
            @Spec(path = "activity14", spec = In.class),
            @Spec(path = "activity15", spec = In.class),
            @Spec(path = "status1", spec = In.class),
            @Spec(path = "status2", spec = In.class),
            @Spec(path = "status3", spec = In.class),
            @Spec(path = "status4", spec = In.class),
            @Spec(path = "status5", spec = In.class),
            @Spec(path = "status6", spec = In.class),
            @Spec(path = "status7", spec = In.class),
            @Spec(path = "status8", spec = In.class),
            @Spec(path = "status9", spec = In.class),
            @Spec(path = "status10", spec = In.class),
            @Spec(path = "status11", spec = In.class),
            @Spec(path = "status12", spec = In.class),
            @Spec(path = "status13", spec = In.class),
            @Spec(path = "status14", spec = In.class),
            @Spec(path = "status15", spec = In.class),
            @Spec(path = "comment1", spec = In.class),
            @Spec(path = "comment2", spec = In.class),
            @Spec(path = "comment3", spec = In.class),
            @Spec(path = "comment4", spec = In.class),
            @Spec(path = "comment5", spec = In.class),
            @Spec(path = "comment6", spec = In.class),
            @Spec(path = "comment7", spec = In.class),
            @Spec(path = "comment8", spec = In.class),
            @Spec(path = "comment9", spec = In.class),
            @Spec(path = "comment10", spec = In.class),
            @Spec(path = "comment11", spec = In.class),
            @Spec(path = "comment12", spec = In.class),
            @Spec(path = "comment13", spec = In.class),
            @Spec(path = "comment14", spec = In.class),
            @Spec(path = "comment15", spec = In.class),
            @Spec(path = "attached1", spec = In.class),
            @Spec(path = "attached2", spec = In.class),
            @Spec(path = "attached3", spec = In.class),
            @Spec(path = "attached4", spec = In.class),
            @Spec(path = "attached5", spec = In.class),
            @Spec(path = "attached6", spec = In.class),
            @Spec(path = "attached7", spec = In.class),
            @Spec(path = "attached8", spec = In.class),
            @Spec(path = "attached9", spec = In.class),
            @Spec(path = "attached10", spec = In.class),
            @Spec(path = "attached11", spec = In.class),
            @Spec(path = "attached12", spec = In.class),
            @Spec(path = "attached13", spec = In.class),
            @Spec(path = "attached14", spec = In.class),
            @Spec(path = "attached15", spec = In.class)
    }) Specification<WorkflowView> workFlowViewSpec,
                                                   Pageable pageable) throws Exception {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return projectSvc.findAllWorkflowViews(user.getClientId(), workFlowViewSpec, pageable);
        }
        return null;
    }

    /*@GetMapping("/worflow-views/filters")
    public WorkflowFilterNumbers findAllWorkflowFilterNumbers (
            @RequestParam Integer projectTypeId,
            @RequestParam Integer templateId,
            @RequestParam Integer year,
            @RequestParam (required = false) String period
    ) throws Exception {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();

            ProjectExecution projectExecution = new ProjectExecution();
            projectExecution.setProjectTypeId(projectTypeId);
            projectExecution.setTemplateId(templateId);
            projectExecution.setYear(year);
            projectExecution.setPeriod(period);
            projectExecution.setClientId(user.getClientId());

            return workflowSvc.findAllWorkflowFilterNumbers(projectExecution);
        } else {
            return new WorkflowFilterNumbers();
        }
    }*/

    @GetMapping("/project-executions/{projectExecutionId}/activities")
    public List<ActivityDTO> findActivityDTOByClientIdAndProjectExecutionId(@PathVariable Long projectExecutionId) throws Exception {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return projectSvc.findActivityDTOByClientIdAndProjectExecutionId(user.getClientId(), projectExecutionId);
        }
        return new ArrayList<>();
    }

    @PutMapping("/project-executions/{projectExecutionId}")
    public ProjectExecution updateProjectExecution(@PathVariable Long projectExecutionId, @RequestBody ProjectExecution projectExecution) throws Exception {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return projectSvc.updateProjectExecution(user.getClientId(), projectExecutionId, projectExecution);
        } else {
            throw new GesoftProjectException("Ejecuciones Proyecto", "clientId es requerido");
        }
    }


    @DeleteMapping("/project-executions/{projectExecutionId}")
    public ResponseEntity<?> deleteProjectExecution(@PathVariable Long projectExecutionId) throws Exception {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return projectSvc.deleteProjectExecution(user.getClientId(), projectExecutionId);
        } else {
            throw new GesoftProjectException("Ejecuciones Proyecto", "clientId es requerido");
        }
    }

    @GetMapping("/activities/{activityId}")
    public ActivityDTO findActivityDTOByClientIdAndActivityId(@PathVariable String activityId) throws Exception {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return projectSvc.findActivityDTOByClientIdAndActivityId(user.getClientId(), activityId);
        }else {
            throw new GesoftProjectException("Actividades", "clientId es requerido");
        }
    }

    @GetMapping("/activity/{activityId}/comments")
    public List<ActivityComment> findActivityCommentByClientIdAndActivityId(@PathVariable String activityId) throws Exception {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return projectSvc.findActivityCommentByClientIdAndActivityId(user.getClientId(), activityId);
        }
        return new ArrayList<>();
    }

    @GetMapping("/activity/{activityId}/attachments")
    public List<ActivityAttachment> findActivityAttachmentByClientIdAndActivityId(@PathVariable String activityId) throws Exception {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return projectSvc.findActivityAttachmentByClientIdAndActivityId(user.getClientId(), activityId);
        }
        return new ArrayList<>();
    }

    @PutMapping("/activity/{activityId}/advance")
    public Activity updateActivityAdvance(@PathVariable String activityId, @RequestBody Activity activity) throws Exception {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return projectSvc.updateActivityAdvance(user.getClientId(), user,  activityId, activity);
        } else {
            throw new GesoftProjectException("Actividades", "clientId es requerido");
        }
    }

    @PutMapping("/activity/{activityId}/approve")
    public Activity updateActivityApprove(@PathVariable String activityId) throws Exception {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return projectSvc.updateActivityApprove(user.getClientId(), activityId);
        } else {
            throw new GesoftProjectException("Actividades", "clientId es requerido");
        }
    }

    @PutMapping("/activity/{activityId}/reject")
    public Activity updateActivityReject(@PathVariable String activityId) throws Exception {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return projectSvc.updateActivityReject(user.getClientId(), activityId);
        } else {
            throw new GesoftProjectException("Actividades", "clientId es requerido");
        }
    }

    @PutMapping("/activity/{activityId}/pending")
    public Activity updateActivityPending(@PathVariable String activityId) throws Exception {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        if (authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            return projectSvc.updateActivityPending(user.getClientId(), activityId);
        } else {
            throw new GesoftProjectException("Actividades", "clientId es requerido");
        }
    }

}
