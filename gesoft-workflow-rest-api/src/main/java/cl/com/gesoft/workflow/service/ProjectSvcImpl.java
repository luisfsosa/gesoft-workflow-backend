/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * TEMPLATE-BACKEND-SPRING-BOOT
 */
package cl.com.gesoft.workflow.service;

import cl.com.gesoft.workflow.base.exception.GesoftProjectException;
import cl.com.gesoft.workflow.dao.UserRepository;
import cl.com.gesoft.workflow.dao.WorkflowDAOSvc;
import cl.com.gesoft.workflow.dao.events.CreateSesionCallback;
import cl.com.gesoft.workflow.dao.events.SaveSesionCallback;
import cl.com.gesoft.workflow.model.Session;
import cl.com.gesoft.workflow.model.User;
import cl.com.gesoft.workflow.security.AuthResponse;
import cl.com.gesoft.workflow.security.AuthenticationListener;
import cl.com.gesoft.workflow.security.WorkflowAuthManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;

/**
 * The type Project svc.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
public class ProjectSvcImpl implements ProjectSvc {

    private static Log log = LogFactory.getLog(ProjectSvcImpl.class);

    private DataSource dataSource;

    @Autowired
    private WorkflowDAOSvc workflowDAOSvc;

    @Autowired
	private WorkflowAuthManager authManager;

    /**
     * The User repository.
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Instantiates a new Work flow svc.
     *
     * @param dataSource the data source
     */
    public ProjectSvcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
        Flyway flyway = Flyway.configure().dataSource(dataSource).locations("fw").schemas("public").table("gp_project")
                .baselineVersion("00").ignoreFutureMigrations(false).ignoreMissingMigrations(false)
                .validateOnMigrate(false).load();

        // Start the migration
        flyway.baseline();
        flyway.migrate();
        log.info("Flyway config was started with successfull");
    }

    /**
     * Init.
     */
    @PostConstruct
    public void init() {
    }

    @Override
    public List<User> findAllUsersByRolAndClientId(List<String> rolNames, Integer clientId)
            throws GesoftProjectException {
        return userRepository.findByRolInAndClientId(rolNames, clientId);
    }

    @Override
    public AuthResponse login(String username, String password) throws Exception {
        return workflowDAOSvc.createSesion(new CreateSesionCallback<AuthResponse>() {
            @Override
            public AuthResponse beforeSave(SaveSesionCallback saveSesionCallback) throws Exception {
                Session session = new Session();
				AuthResponse authResponse = authManager.requestAuthKey(username, password,
						new AuthenticationListener() {
							@Override
							public void authResponseCreated(AuthResponse authResponse) throws Exception {
								Integer userId = Integer.parseInt(authResponse.getUserId());
								session.setCreateBy(userId);
								session.setUserId(userId);
								session.setValidFrom(authResponse.getCreationDate());
								session.setValidUntil(authResponse.getExpirationDate());
								session.setClientId(authResponse.getClientId());
								session.setLastAccess(null);
								session.setActive("S");
								saveSesionCallback.save(session);
								authResponse.setSessionId(session.getId());
							}
						});
				return authResponse;
            }
        });
    }
}
