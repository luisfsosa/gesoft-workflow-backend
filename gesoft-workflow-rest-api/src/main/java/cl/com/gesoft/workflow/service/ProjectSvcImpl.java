/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * TEMPLATE-BACKEND-SPRING-BOOT
 */
package cl.com.gesoft.workflow.service;

import cl.com.gesoft.workflow.base.exception.GesoftProjectException;
import cl.com.gesoft.workflow.dao.*;
import cl.com.gesoft.workflow.dao.dto.ActivityDTO;
import cl.com.gesoft.workflow.dao.dto.ProjectDTO;
import cl.com.gesoft.workflow.dao.dto.ProjectExecutionDTO;
import cl.com.gesoft.workflow.dao.dto.TemplateDTO;
import cl.com.gesoft.workflow.dao.events.CreateSesionCallback;
import cl.com.gesoft.workflow.dao.events.SaveSesionCallback;
import cl.com.gesoft.workflow.model.*;
import cl.com.gesoft.workflow.security.AuthResponse;
import cl.com.gesoft.workflow.security.AuthenticationListener;
import cl.com.gesoft.workflow.security.WorkflowAuthManager;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
 * The type Project svc.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
public class ProjectSvcImpl implements ProjectSvc {

    private static Log log = LogFactory.getLog(ProjectSvcImpl.class);
    private static final char[] hexArray = "0123456789ABCDEF".toCharArray();

    private static final String clientSummary = "Clientes";
    private static final String templateSummary = "Plantillas";
    private static final String activitySummary = "Actividades";
    private static final String projectSummary = "Proyectos";
    private static final String projectExecutionSummary = "Ejecuciones Proyecto";

    private static final String POR_INICIAR = "POR_INICIAR";
    private static final String EN_PROCESO = "EN_PROCESO";
    private static final String EN_ESPERA_AUTORIZACION = "EN_ESPERA_AUTORIZACION";
    private static final String AUTORIZADA = "AUTORIZADA";
    private static final String RECHAZADA = "RECHAZADA";
    private static final String TERMINADA = "TERMINADA";
    private static final String CON_AUTORIZACION = "CON AUTORIZACION";

    public enum ActivityTypeFather { TEMPLATE, PROJECT, PROJECT_EXECUTION };

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

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ProjectTypeRepository projectTypeRepository;

    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectExecutionRepository projectExecutionRepository;

    @Autowired
    private WorkFlowViewRepository workFlowViewRepository;

    @Autowired
    private ActivityCommentRepository activityCommentRepository;

    @Autowired
    private ActivityAttachmentRepository activityAttachmentRepository;

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

    @Override
    public List<User> findAllUsersByRolAndClientId(List<String> rolNames, Integer clientId)
            throws GesoftProjectException {
        return userRepository.findByRolInAndClientId(rolNames, clientId);
    }

    @Override
    public List<Client> findAllClientsByClientId(Integer clientId) throws GesoftProjectException {
        return clientRepository.findByClientId(clientId);
    }

    @Override
    public List<Client> findAllActiveClientsByClientId(Integer clientId) throws GesoftProjectException {
        return clientRepository.findByClientIdAndActive(clientId, true);
    }

    @Override
    public Client findClientById(Integer clientId, Long workFlowClientId) throws GesoftProjectException {
        Optional<Client> client = clientRepository.findByIdAndClientId(workFlowClientId, clientId);
        if(client.isPresent()) {
            return client.get();
        } else {
            throw new GesoftProjectException(clientSummary, "Cliente no encontrado");
        }
    }

    @Override
    public Client createClient(Integer clientId, Client client) throws GesoftProjectException {
        client.setId(null);
        client.setClientId(clientId);

        validateViolations(client, clientSummary, "el Cliente");

        try {
            log.info("Crear Cliente [" + client + "]");
            return clientRepository.save(client);
        } catch (DataIntegrityViolationException e) {
            if (e.getMessage().contains("business_name_unique")) {
                throw new GesoftProjectException(clientSummary, "El Nombre del Cliente debe ser único");
            } else {
                throw new GesoftProjectException(clientSummary, e.getMessage());
            }
        } catch (Exception e) {
            throw new GesoftProjectException(clientSummary, e.getMessage(), e);
        }
    }

    @Override
    public Client updateClient(Integer clientId, Long workFlowClientId, Client clientToSave) throws GesoftProjectException {
        if (workFlowClientId == null) {
            throw new GesoftProjectException(clientSummary, "workFlowClientId es Requerido");
        }

        Optional<Client> client = null;

        try {
            log.info("Actualizar Cliente [" + clientToSave + "]");

            client = clientRepository.findByIdAndClientId(workFlowClientId, clientId);

            if(client.isPresent()) {

                if(clientToSave.getAddress() != null) {
                    client.get().setAddress(clientToSave.getAddress());
                }

                return clientRepository.save(client.get());

            } else {
                throw new GesoftProjectException(clientSummary, "Cliente no encontrado");
            }
        }
        catch (GesoftProjectException e) {
            throw e;
        }
        catch (Exception e) {
            throw new GesoftProjectException(clientSummary, e.getMessage(), e);
        }
    }

    @Override
    public Client updateClientState(Integer clientId, Long workFlowClientId, Client clientToSave) throws GesoftProjectException {
        if (workFlowClientId == null) {
            throw new GesoftProjectException(clientSummary, "accountingClientId es Requerido");
        }
        Optional<Client> client = null;

        try {
            log.info("Actualizar Cliente [" + clientToSave + "]");

            client = clientRepository.findByIdAndClientId(workFlowClientId, clientId);

            if(client.isPresent()) {

                client.get().setActive(clientToSave.isActive());
                return clientRepository.save(client.get());

            } else {
                throw new GesoftProjectException(clientSummary, "Cliente no encontrado");
            }
        }
        catch (GesoftProjectException e) {
            throw e;
        }
        catch (Exception e) {
            throw new GesoftProjectException(clientSummary, e.getMessage(), e);
        }
    }

    @Override
    public List<ProjectType> findAllProjectTypes() throws GesoftProjectException {
        return projectTypeRepository.findAll();
    }

    @Override
    public ProjectType findProjectTypeById(Integer projectTypeId) throws GesoftProjectException {
        Optional<ProjectType> projectType = projectTypeRepository.findById(projectTypeId);
        if(projectType.isPresent()) {
            return projectType.get();
        } else {
            throw new GesoftProjectException(projectSummary, "Típo de Proyecto no encontrado");
        }
    }

    @Override
    public List<TemplateDTO> findAllTemplatesByClientId(Integer clientId) throws GesoftProjectException {
        return templateRepository.findTemplateDTOByClientId(clientId);
    }

    @Override
    public List<Template> findTemplateByClientIdAndProjectTypeId(Integer clientId, Long projectTypeId) throws GesoftProjectException {
        return templateRepository.findByClientIdAndProjectTypeId(clientId, projectTypeId);
    }

    @Override
    public List<ActivityDTO> findActivityDTOByClientIdAndTemplateId(Integer clientId, Long templateId) throws GesoftProjectException {
        return activityRepository.findActivityDTOByClientIdAndTemplateId(clientId, templateId);
    }

    @Override
    public Template createTemplate(Integer clientId, Template template) throws GesoftProjectException {
        template.setId(null);
        template.setClientId(clientId);

        validateViolations(template, templateSummary, "la Plantilla");

        try {
            log.info("Crear Plantilla [" + template + "]");
            template = templateRepository.save(template);
        } catch (Exception e) {
            throw new GesoftProjectException(templateSummary, e.getMessage(), e);
        }

        saveActivities(ActivityTypeFather.TEMPLATE, false, template, template.getActivities());
        return template;
    }

    @Override
    public Template updateTemplate(Integer clientId, Long templateId, Template templateToSave) throws GesoftProjectException {
        if (templateId == null) {
            throw new GesoftProjectException(templateSummary, "templateId es Requerido");
        }
        if (templateToSave.getName() == null) {
            throw new GesoftProjectException(templateSummary, "name es Requerido");
        }

        if (templateToSave.getDescription() == null) {
            throw new GesoftProjectException(templateSummary, "description es Requerido");
        }

        if (templateToSave.getCuttingDay() == null) {
            throw new GesoftProjectException(templateSummary, "cuttingDay es Requerido");
        }

        if (templateToSave.getProjectTypeId() == null) {
            throw new GesoftProjectException(templateSummary, "projectTypeId es Requerido");
        }

        Template templateUpdated = null;
        try {
            log.info("Actualizar Plantilla [" + templateToSave + "]");
            templateUpdated = templateRepository.findByIdAndClientId(templateId, clientId).map(template -> {
                template.setName(templateToSave.getName());
                template.setDescription(templateToSave.getDescription());
                template.setCuttingDay(templateToSave.getCuttingDay());
                template.setProjectTypeId(templateToSave.getProjectTypeId());
                template.setOrderTemplate(templateToSave.isOrderTemplate());
                return templateRepository.save(template);
            }).orElseThrow(() -> new GesoftProjectException(templateSummary, "Plantilla no encontrada"));
        } catch (Exception e) {
            throw new GesoftProjectException(templateSummary, e.getMessage(), e);
        }
        templateUpdated.setActivities(templateToSave.getActivities());
        saveActivities(ActivityTypeFather.TEMPLATE, true, templateUpdated, templateUpdated.getActivities());
        return templateUpdated;
    }

    @Override
    public List<ProjectDTO> findAllProjectsByClientId(Integer clientId) throws GesoftProjectException {
        return projectRepository.findProjectDTOByClientId(clientId);
    }

    @Override
    public Project findProjectById(Integer clientId, Long projectId) throws GesoftProjectException {
        Optional<Project> project = projectRepository.findByIdAndClientId(projectId, clientId);
        if(project.isPresent()) {
            return project.get();
        } else {
            throw new GesoftProjectException(projectSummary, "Proyecto no encontrado");
        }
    }

    @Override
    public List<ActivityDTO> findActivityDTOByClientIdAndProjectId(Integer clientId, Long projectId) throws GesoftProjectException {
        return activityRepository.findActivityDTOByClientIdAndProjectId(clientId, projectId);
    }

    @Override
    public List<ProjectExecution> findProjectExecutionsByProjectId(Integer clientId, Long projectId) throws GesoftProjectException {
        return projectExecutionRepository.findByProjectIdAndClientIdOrderByProjectSinceAsc(projectId, clientId);
    }

    @Override
    public Project createProject(Integer clientId, Project project) throws GesoftProjectException {
        ProjectExecution projectExecution;
        project.setId(null);
        project.setClientId(clientId);

        validateViolations(project, projectSummary, "el Proyecto");

        try {
            log.info("Crear Proyecto [" + project + "]");
            project = projectRepository.save(project);
        } catch (Exception e) {
            throw new GesoftProjectException(projectSummary, e.getMessage(), e);
        }

        try {
            saveActivities(ActivityTypeFather.PROJECT, false, project, project.getActivities());
        } catch (Exception e) {
            throw new GesoftProjectException(projectSummary, e.getMessage(), e);
        }

        LocalDate projectSince = project.getProjectSince();
        LocalDate projectUntil = project.getProjectUntil();

        if(projectUntil == null) {
            projectUntil = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        }

        do {
            try {
                projectExecution = createProjectExecution(project, projectSince);
            } catch (Exception e) {
                throw new GesoftProjectException(projectSummary, e.getMessage(), e);
            }

            try {
                saveProjectExecutionActivities(projectExecution, project.getId(), project.getClientId());
            } catch (Exception e) {
                throw new GesoftProjectException(projectSummary, e.getMessage(), e);
            }
            projectSince = projectExecution.getProjectUntil();
        }
        while (projectSince.isBefore(projectUntil));

        return project;
    }

    @Override
    public Project updateProject(Integer clientId, Long projectId, Project projectToSave) throws GesoftProjectException {
        Optional<ProjectExecution> lastProjectExecution;
        ProjectExecution projectExecution;

        if (projectId == null) {
            throw new GesoftProjectException(projectSummary, "projectId es Requerido");
        }
        if (projectToSave.getName() == null) {
            throw new GesoftProjectException(projectSummary, "name es Requerido");
        }

        if (projectToSave.getComments() == null) {
            throw new GesoftProjectException(projectSummary, "comments es Requerido");
        }

        if (projectToSave.getGwfClientId() == null) {
            throw new GesoftProjectException(projectSummary, "gwfClientId es Requerido");
        }

        if (projectToSave.getProjectTypeId() == null) {
            throw new GesoftProjectException(projectSummary, "projectTypeId es Requerido");
        }

        if (projectToSave.getTemplateId() == null) {
            throw new GesoftProjectException(projectSummary, "templateId es Requerido");
        }

        if (projectToSave.getUserId()== null) {
            throw new GesoftProjectException(projectSummary, "userId es Requerido");
        }

        if (projectToSave.getProjectSince()== null) {
            throw new GesoftProjectException(projectSummary, "projectSince es Requerido");
        }

        if (projectToSave.getCuttingDay() == null) {
            throw new GesoftProjectException(projectSummary, "cuttingDay es Requerido");
        }

        Project projectUpdated = null;
        try {
            log.info("Actualizar Proyecto [" + projectToSave + "]");
            projectUpdated = projectRepository.findByIdAndClientId(projectId, clientId).map(project -> {
                project.setName(projectToSave.getName());
                project.setComments(projectToSave.getComments());
                project.setGwfClientId(projectToSave.getGwfClientId());
                project.setProjectTypeId(projectToSave.getProjectTypeId());
                project.setTemplateId(projectToSave.getTemplateId());
                project.setUserId(projectToSave.getUserId());
                project.setProjectSince(projectToSave.getProjectSince());
                project.setProjectUntil(projectToSave.getProjectUntil());
                project.setYearSince(projectToSave.getYearSince());
                project.setYearUntil(projectToSave.getYearUntil());
                project.setPeriodSince(projectToSave.getPeriodSince());
                project.setPeriodUntil(projectToSave.getPeriodUntil());
                project.setCuttingDay(projectToSave.getCuttingDay());
                project.setOrderTemplate(projectToSave.isOrderTemplate());
                return projectRepository.save(project);
            }).orElseThrow(() -> new GesoftProjectException(projectSummary, "Proyecto no encontrado"));
        } catch (Exception e) {
            throw new GesoftProjectException(projectSummary, e.getMessage(), e);
        }
        projectUpdated.setActivities(projectToSave.getActivities());
        saveActivities(ActivityTypeFather.PROJECT, true, projectUpdated, projectUpdated.getActivities());

        lastProjectExecution = projectExecutionRepository.findByMaxProjectUntil(projectUpdated.getId());

        LocalDate projectSince;
        LocalDate projectUntil = projectUpdated.getProjectUntil();

        if(projectUntil == null) {
            projectUntil = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        }

        if (lastProjectExecution.isPresent() && lastProjectExecution.get().getProjectSince().isBefore(projectUntil)) {
            projectSince = lastProjectExecution.get().getProjectUntil();

            while (projectSince.isBefore(projectUntil)) {
                try {
                    projectExecution = createProjectExecution(projectUpdated, projectSince);
                } catch (Exception e) {
                    throw new GesoftProjectException(projectSummary, e.getMessage(), e);
                }

                try {
                    saveProjectExecutionActivities(projectExecution, projectUpdated.getId(), projectUpdated.getClientId());
                } catch (Exception e) {
                    throw new GesoftProjectException(projectSummary, e.getMessage(), e);
                }
                projectSince = projectExecution.getProjectUntil();
            }
        }

        return projectUpdated;
    }

    @Override
    public ResponseEntity<?> deleteProject(Integer clientId, Long projectId) throws GesoftProjectException {
        if (projectId == null) {
            throw new GesoftProjectException(projectSummary, "groupId es Requerido");
        }
        try {
            List<ProjectExecution> projectExecutions = projectExecutionRepository.findByProjectIdAndClientIdOrderByProjectSinceAsc(projectId, clientId);
            if(projectExecutions == null || projectExecutions.size() <= 0) {
                log.info("Eliminar Proyecto [" + projectId + "]");
                return projectRepository.findByIdAndClientId(projectId, clientId).map(group -> {
                    projectRepository.delete(group);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new GesoftProjectException(projectSummary, "Proyecto no encontrado"));
            } else {
                throw new GesoftProjectException(projectSummary, "No se puede eliminar el proyecto porque ya esta en ejecución en el tablero workflow");
            }
        } catch (Exception e) {
            throw new GesoftProjectException(projectSummary, e.getMessage(), e);
        }
    }

    @Override
    public List<ProjectExecutionDTO> findProjectDTOByClientIdAndProjectTypeIdAndYearSinceAndPeriodSince(Integer clientId, Integer projectTypeId, Integer yearSince, String periodSince) throws GesoftProjectException {
        if(periodSince == null) {
            return projectExecutionRepository.findProjectDTOByClientIdAndProjectTypeIdAndYearSince(clientId, projectTypeId, yearSince);
        } else {
            return projectExecutionRepository.findProjectDTOByClientIdAndProjectTypeIdAndYearSinceAndPeriodSince(clientId, projectTypeId, yearSince, periodSince);
        }
    }

    @Override
    public Page<WorkflowView> findAllWorkflowViews(Integer clientId, Specification<WorkflowView> workFlowViewSpec, Pageable pageable) throws GesoftProjectException {
        return workFlowViewRepository.findAll(workFlowViewSpec, pageable);
    }

    @Override
    public List<ActivityDTO> findActivityDTOByClientIdAndProjectExecutionId(Integer clientId, Long projectExecutionId) throws GesoftProjectException {
        return activityRepository.findActivityDTOByClientIdAndProjectExecutionId(clientId, projectExecutionId);
    }

    @Override
    public ProjectExecution updateProjectExecution(Integer clientId, Long projectExecutionId, ProjectExecution projectExecutionToSave) throws GesoftProjectException {
        if (projectExecutionId == null) {
            throw new GesoftProjectException(projectExecutionSummary, "projectExecutionId es Requerido");
        }

        if (projectExecutionToSave.getUserId()== null) {
            throw new GesoftProjectException(projectExecutionSummary, "userId es Requerido");
        }

        ProjectExecution projectExecutionUpdated = null;
        try {
            log.info("Actualizar Ejecución Proyecto [" + projectExecutionToSave + "]");
            projectExecutionUpdated = projectExecutionRepository.findByIdAndClientId(projectExecutionId, clientId).map(projectExecution -> {
                projectExecution.setUserId(projectExecutionToSave.getUserId());
                projectExecution.setOrderTemplate(projectExecutionToSave.isOrderTemplate());
                return projectExecutionRepository.save(projectExecution);
            }).orElseThrow(() -> new GesoftProjectException(projectExecutionSummary, "Ejecución Proyecto no encontrado"));
        } catch (Exception e) {
            throw new GesoftProjectException(projectExecutionSummary, e.getMessage(), e);
        }
        projectExecutionUpdated.setActivities(projectExecutionToSave.getActivities());
        saveActivities(ActivityTypeFather.PROJECT_EXECUTION, true, projectExecutionUpdated, projectExecutionUpdated.getActivities());
        return projectExecutionUpdated;
    }

    @Override
    public ResponseEntity<?> deleteProjectExecution(Integer clientId, Long projectExecutionId) throws GesoftProjectException {
        if (projectExecutionId == null) {
            throw new GesoftProjectException(projectExecutionSummary, "projectExecutionId es Requerido");
        }
        try {
            return projectExecutionRepository.findByIdAndClientId(projectExecutionId, clientId).map(group -> {
                projectExecutionRepository.delete(group);
                return ResponseEntity.ok().build();
            }).orElseThrow(() -> new GesoftProjectException(projectExecutionSummary, "Ejecución Proyecto no encontrado"));
        } catch (Exception e) {
            throw new GesoftProjectException(projectExecutionSummary, e.getMessage(), e);
        }
    }

    @Override
    public ActivityDTO findActivityDTOByClientIdAndActivityId(Integer clientId, String activityId) throws GesoftProjectException {
        return activityRepository.findActivityDTOByClientIdAndActivityId(clientId, activityId);
    }

    @Override
    public List<ActivityComment> findActivityCommentByClientIdAndActivityId(Integer clientId, String activityId) throws GesoftProjectException {
        return activityCommentRepository.findByClientIdAndActivityIdOrderByCommentDate(clientId, activityId);
    }

    @Override
    public List<ActivityAttachment> findActivityAttachmentByClientIdAndActivityId(Integer clientId, String activityId) throws GesoftProjectException {
        return activityAttachmentRepository.findByClientIdAndActivityIdOrderByAttachmentDate(clientId, activityId);
    }

    @Override
    public Activity updateActivityAdvance(Integer clientId, User user, String activityId, Activity activityToSave) throws GesoftProjectException {
        if (activityId == null) {
            throw new GesoftProjectException(activitySummary, "activityId es Requerido");
        }

        Activity activityUpdated = null;

        try {
            log.info("Actualizar Actividad [" + activityToSave + "]");
            activityUpdated= activityRepository.findByIdAndClientId(activityId, clientId).map(activity -> {
                activity.setComment(activityToSave.isComment());
                activity.setAttached(activityToSave.isAttached());
                activity.setEvidenceRequired(activityToSave.isEvidenceRequired());

                if(activityToSave.getActivityAdvance() !=null) {
                    activity.setActivityAdvance(activityToSave.getActivityAdvance());


                    if(activityToSave.getActivityAdvance() >= 100 && CON_AUTORIZACION.equals(activity.getActivityType()) && (activityToSave.getStatus() == null && POR_INICIAR.equals(activity.getStatus()) || EN_PROCESO.equals(activity.getStatus()) || EN_ESPERA_AUTORIZACION.equals(activity.getStatus()))) {
                        activity.setStatus(EN_ESPERA_AUTORIZACION);
                    } else if (activityToSave.getActivityAdvance() >= 100 && CON_AUTORIZACION.equals(activity.getActivityType()) && RECHAZADA.equals(activity.getStatus()) && activity.getResponsable() == user.getId().intValue()) {
                        activity.setStatus(EN_PROCESO);
                    }  else if (activityToSave.getActivityAdvance() >= 100 && CON_AUTORIZACION.equals(activity.getActivityType()) && RECHAZADA.equals(activity.getStatus())){
                        activity.setStatus(RECHAZADA);
                    } else if (activityToSave.getActivityAdvance() >= 100 && (activityToSave.getStatus() == null || POR_INICIAR.equals(activity.getStatus())|| EN_PROCESO.equals(activity.getStatus()))) {
                        activity.setStatus(TERMINADA);
                    } else {
                        activity.setStatus(EN_PROCESO);
                    }
                }

                if(activityToSave.getActivityComments() != null && activityToSave.getActivityComments().size() > 0) {
                    activity.setComment(true);
                }
                if(activity.isEvidenceRequired() && activityToSave.getActivityAttachments() !=null && activityToSave.getActivityAttachments().size() >= 0) {
                    activity.setAttached(true);
                }
                return activityRepository.save(activity);
            }).orElseThrow(() -> new GesoftProjectException(activitySummary, "Actividad no encontrada"));
        } catch (Exception e) {
            throw new GesoftProjectException(activitySummary, e.getMessage(), e);
        }

        if(activityToSave.getActivityComments() != null && activityToSave.getActivityComments().size() > 0) {
            saveConmment(activityToSave.getActivityComments(), activityUpdated.getId(), user.getName(), clientId);
        }

        if(activityUpdated.isEvidenceRequired() && activityToSave.getActivityAttachments() != null && activityToSave.getActivityAttachments().size() > 0) {
            saveAttachment(activityToSave.getActivityAttachments(), activityUpdated.getId(), user.getName(), clientId);
        }

        if (activityToSave.getActivityAdvance() !=null && activityToSave.getActivityAdvance() >= 100) {
            calculateProjectAdvanceActivity(activityUpdated.getProjectExecutionId(), clientId );
        }

        return activityUpdated;
    }

    @Override
    public Activity updateActivityApprove(Integer clientId, String activityId) throws GesoftProjectException {
        if (activityId == null) {
            throw new GesoftProjectException(activitySummary, "activityId es Requerido");
        }

        try {
            return activityRepository.findByIdAndClientId(activityId, clientId).map(activity -> {
                activity.setStatus(TERMINADA);
                return activityRepository.save(activity);
            }).orElseThrow(() -> new GesoftProjectException(activitySummary, "Actividad no encontrada"));
        } catch (Exception e) {
            throw new GesoftProjectException(activitySummary, e.getMessage(), e);
        }
    }

    @Override
    public Activity updateActivityReject(Integer clientId, String activityId) throws GesoftProjectException {
        if (activityId == null) {
            throw new GesoftProjectException(activitySummary, "activityId es Requerido");
        }

        Activity activityUpdated = null;

        try {
            activityUpdated= activityRepository.findByIdAndClientId(activityId, clientId).map(activity -> {
                activity.setStatus(RECHAZADA);
                return activityRepository.save(activity);
            }).orElseThrow(() -> new GesoftProjectException(activitySummary, "Actividad no encontrada"));
        } catch (Exception e) {
            throw new GesoftProjectException(activitySummary, e.getMessage(), e);
        }

        calculateProjectAdvanceActivity(activityUpdated.getProjectExecutionId(),clientId );
        return activityUpdated;
    }

    @Override
    public Activity updateActivityPending(Integer clientId, String activityId) throws GesoftProjectException {
        if (activityId == null) {
            throw new GesoftProjectException(activitySummary, "activityId es Requerido");
        }

        Activity activityUpdated = null;

        try {
            activityUpdated= activityRepository.findByIdAndClientId(activityId, clientId).map(activity -> {
                activity.setStatus(EN_PROCESO);
                activity.setActivityAdvance(0);
                return activityRepository.save(activity);
            }).orElseThrow(() -> new GesoftProjectException(activitySummary, "Actividad no encontrada"));
        } catch (Exception e) {
            throw new GesoftProjectException(activitySummary, e.getMessage(), e);
        }

        calculateProjectAdvanceActivity(activityUpdated.getProjectExecutionId(),clientId );
        return activityUpdated;
    }

    private ProjectExecution createProjectExecution (Project project, LocalDate projectSince) throws GesoftProjectException {
        Optional<ProjectType> projectTypeOptional = null;
        ProjectExecution projectExecution =new ProjectExecution();

        StringBuilder periodName = new StringBuilder();
        LocalDate projectSincePeriod = projectSince.withDayOfMonth(1);

        if(project !=null && project.getProjectTypeId() != null) {
            projectTypeOptional = projectTypeRepository.findById(project.getProjectTypeId());
            if(projectTypeOptional.isPresent()) {
                if(projectSince.lengthOfMonth() < project.getCuttingDay()) {
                    projectSince = projectSince.withDayOfMonth(projectSince.lengthOfMonth());
                } else {
                    projectSince = projectSince.withDayOfMonth(project.getCuttingDay());
                }

                if(projectTypeOptional.get().getDuration() < 12) {
                    periodName.append(projectSincePeriod.getMonth().getDisplayName(TextStyle.FULL,new Locale("es")).toUpperCase());
                }

                projectExecution.setProjectSince(projectSince);
                projectExecution.setYear(projectSince.getYear());
                projectSince = projectSince.plusMonths(projectTypeOptional.get().getDuration());
                projectSincePeriod = projectSincePeriod.plusMonths(projectTypeOptional.get().getDuration() - 1);
                if(projectSince.lengthOfMonth() < project.getCuttingDay()) {
                    projectSince = projectSince.withDayOfMonth(projectSince.lengthOfMonth());
                } else {
                    projectSince = projectSince.withDayOfMonth(project.getCuttingDay());
                }
                projectSince = projectSince.minusDays(1);
                projectExecution.setProjectUntil(projectSince);
                if(projectTypeOptional.get().getDuration() !=12 && projectTypeOptional.get().getDuration() != 1) {
                    periodName.append("-");
                    periodName.append(projectSincePeriod.getMonth().getDisplayName(TextStyle.FULL,new Locale("es")).toUpperCase());
                    projectExecution.setPeriod(periodName.toString());
                } else if (projectTypeOptional.get().getDuration() == 1)  {
                    projectExecution.setPeriod(periodName.toString());
                }
                return saveProjectExecution(projectExecution, project);
            }else {
                throw new GesoftProjectException(projectSummary, "No se encontro un típo de proyecto compatible");
            }
        } else {
            throw new GesoftProjectException(projectSummary, "El proyecto no tiene asociado un típo de proyecto");
        }
    }

    private ProjectExecution saveProjectExecution(ProjectExecution projectExecution, Project project) throws GesoftProjectException {
        projectExecution.setName(project.getName());
        projectExecution.setGwfClientId(project.getGwfClientId());
        projectExecution.setProjectTypeId(project.getProjectTypeId());
        projectExecution.setProjectId(project.getId());
        projectExecution.setTemplateId(project.getTemplateId());
        projectExecution.setUserId(project.getUserId());
        projectExecution.setOrderTemplate(project.isOrderTemplate());
        projectExecution.setProjectAdvance(0);
        projectExecution.setClientId(project.getClientId());

        try {
            log.info("Crear projectExecution [" + projectExecution + "]");
            return projectExecutionRepository.save(projectExecution);
        } catch (Exception e) {
            throw new GesoftProjectException(activitySummary, e.getMessage(), e);
        }
    }

    private List<Activity> saveProjectExecutionActivities(ProjectExecution projectExecution, Long projectId, Integer clientId) throws NoSuchAlgorithmException, UnsupportedEncodingException, GesoftProjectException {
        MessageDigest salt = MessageDigest.getInstance("SHA-256");
        Set<Activity> projectExecutionActivities = new HashSet<>();
        List<String> predecessorActivities;
        Activity projectExecutionActivity;
        Map<String, String> activityKeymap = new HashMap<>();
        String oldKey;

        List<Activity> activities = activityRepository.findByClientIdAndProjectIdOrderByActivityOrder(clientId, projectId);

        if(activities == null) {
            return new ArrayList<>();
        }

        for (Activity activity: activities) {
            projectExecutionActivity = new Activity();

            if (activity.getPredecessorActivities() !=null && activity.getPredecessorActivities().length > 0) {
                predecessorActivities = new LinkedList<>();

                for (int i = 0; i< activity.getPredecessorActivities().length; i++) {
                    predecessorActivities.add(activityKeymap.get(activity.getPredecessorActivities()[i]));
                }
                projectExecutionActivity.setPredecessorActivities(predecessorActivities.toArray(new String[predecessorActivities.size()]));
            }
            oldKey = activity.getId();
            salt.update(UUID.randomUUID().toString().getBytes("UTF-8"));
            projectExecutionActivity.setId(bytesToHex(salt.digest()));
            activityKeymap.put(oldKey, projectExecutionActivity.getId());

            projectExecutionActivity.setName(activity.getName());
            projectExecutionActivity.setDescription(activity.getDescription());
            projectExecutionActivity.setWeighing(activity.getWeighing());
            projectExecutionActivity.setActivityType(activity.getActivityType());
            projectExecutionActivity.setEvidenceRequired(activity.isEvidenceRequired());
            //projectExecutionActivity.setSendFiles(activity.isSendFiles());
            projectExecutionActivity.setAuthorizer(activity.getAuthorizer());
            projectExecutionActivity.setResponsable(activity.getResponsable());
            projectExecutionActivity.setActivityOrder(activity.getActivityOrder());
            projectExecutionActivity.setActivityAdvance(0);
            projectExecutionActivity.setClientId(activity.getClientId());

            projectExecutionActivity.setProjectId(null);
            projectExecutionActivity.setProjectExecutionId(projectExecution.getId());
            projectExecutionActivity.setComment(false);
            projectExecutionActivity.setAttached(false);
            projectExecutionActivity.setStatus(POR_INICIAR);

            projectExecutionActivities.add(projectExecutionActivity);
        }

        try {
            log.info("Crear Actividades de project execution [" + activities + "]");
            return activityRepository.saveAll(projectExecutionActivities);
        } catch (Exception e) {
            throw new GesoftProjectException(activitySummary, e.getMessage(), e);
        }
    }

    private List<Activity> saveActivities (ActivityTypeFather activityTypeFather, boolean update, WorflowModelIdAndClientId worflowModelClientId, Set<Activity> activities) throws GesoftProjectException {
        if(activities == null) {
            return new ArrayList<>();
        }

        for (Activity activity : activities) {
            activity.setClientId(worflowModelClientId.getClientId());

            validateViolations(activity, activitySummary, "la Actividad");

            switch (activityTypeFather) {
                case TEMPLATE:
                    activity.setTemplateId(worflowModelClientId.getId());
                    break;
                case PROJECT:
                    activity.setProjectId(worflowModelClientId.getId());
                    break;
                case PROJECT_EXECUTION:
                    activity.setProjectExecutionId(worflowModelClientId.getId());
                    break;
                default:
                    activity.setTemplateId(worflowModelClientId.getId());
            }
        }

        try {
            log.info("Crear Actividades [" + activities + "]");
            if (update) {
                switch (activityTypeFather) {
                    case TEMPLATE:
                        activityRepository.deleteByTemplateId(worflowModelClientId.getId());
                        break;
                    case PROJECT:
                        activityRepository.deleteByProjectId(worflowModelClientId.getId());
                        break;
                    case PROJECT_EXECUTION:
                        activityRepository.deleteByProjectExecutionId(worflowModelClientId.getId());
                        break;
                    default:
                        activityRepository.deleteByTemplateId(worflowModelClientId.getId());
                }
            }

            return activityRepository.saveAll(activities);
        } catch (Exception e) {
            throw new GesoftProjectException(activitySummary, e.getMessage(), e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }


    private List<ActivityComment> saveConmment(Set<ActivityComment> activityComments, String activityId, String userName, Integer clientId) throws GesoftProjectException {
        activityComments.forEach( activityComment -> {
            activityComment.setActivityId(activityId);
            activityComment.setCommentDate(convertToLocalDateTime(Calendar.getInstance().getTime()));
            activityComment.setUserName(userName);
            activityComment.setClientId(clientId);
        });

        try {
            log.info("Crear comentarios actividad [" + activityComments + "]");
            return activityCommentRepository.saveAll(activityComments);
        } catch (Exception e) {
            throw new GesoftProjectException(activitySummary, e.getMessage(), e);
        }
    }

    private List<ActivityAttachment> saveAttachment(Set<ActivityAttachment> activityAttachments, String activityId, String userName, Integer clientId) throws GesoftProjectException {
        activityAttachments.forEach( activityAttachment -> {
            activityAttachment.setActivityId(activityId);
            activityAttachment.setAttachmentDate(convertToLocalDateTime(Calendar.getInstance().getTime()));
            activityAttachment.setUserName(userName);
            activityAttachment.setClientId(clientId);
        });

        try {
            log.info("Crear comentarios actividad [" + activityAttachments + "]");
            return activityAttachmentRepository.saveAll(activityAttachments);
        } catch (Exception e) {
            throw new GesoftProjectException(activitySummary, e.getMessage(), e);
        }
    }


    private ProjectExecution calculateProjectAdvanceActivity (Long projectExecutionId,Integer clientId) throws GesoftProjectException {

        Integer projectAdvance = 0;
        List<ActivityDTO>  activityDTOS;
        Optional<ProjectExecution> projectExecution;


        try {
            activityDTOS =  activityRepository.findActivityDTOByClientIdAndProjectExecutionId(clientId, projectExecutionId);
            for (ActivityDTO activityDTO: activityDTOS) {
                if(activityDTO.getStatus().equals(TERMINADA) || activityDTO.getStatus().equals(EN_ESPERA_AUTORIZACION)) {
                    projectAdvance += activityDTO.getWeighing();
                }
            }
        } catch (Exception e) {
            throw new GesoftProjectException(projectSummary, e.getMessage(), e);
        }

        try {

            projectExecution = projectExecutionRepository.findByIdAndClientId(projectExecutionId, clientId);

            if(projectAdvance > 100) {
                projectAdvance= 100;
            }

            if(projectAdvance < 0) {
                projectAdvance= 0;
            }

            if(projectExecution.isPresent()) {
                projectExecution.get().setProjectAdvance(projectAdvance);
                return projectExecutionRepository.save(projectExecution.get());
            } else {
                throw new GesoftProjectException(projectSummary, "Ejecución del proyecto no encontrada");
            }

        } catch (Exception e) {
            throw new GesoftProjectException(projectSummary, e.getMessage(), e);
        }

    }

    private LocalDateTime convertToLocalDateTime (Date datetoConvert) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(datetoConvert.getTime()), ZoneId.systemDefault());
    }

    private void validateViolations(WorflowModel worflowModel, String summary, String worflowModelName) throws GesoftProjectException {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(" No se puede Crear ");
        stringBuffer.append(worflowModelName);
        stringBuffer.append(" por lo siguiente: ");
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<WorflowModel>> violations = validator.validate(worflowModel);

        if (violations.size() > 0) {
            for (ConstraintViolation<WorflowModel> violation : violations) {
                stringBuffer.append(violation.getMessage());
                stringBuffer.append("  ");
            }
            throw new GesoftProjectException(summary, stringBuffer.toString());
        }
    }

}
