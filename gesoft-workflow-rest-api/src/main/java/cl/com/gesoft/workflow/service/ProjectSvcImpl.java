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
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * The type Project svc.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
public class ProjectSvcImpl implements ProjectSvc {

    private static Log log = LogFactory.getLog(ProjectSvcImpl.class);

    private static final String clientSummary = "Clientes";
    private static final String templateSummary = "Plantillas";
    private static final String activitySummary = "Actividades";
    private static final String projectSummary = "Proyectos";

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
        return null;
    }

    @Override
    public Client findClientById(Integer clientId, Long workFlowClientId) throws GesoftProjectException {
        return null;
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
        return null;
    }

    @Override
    public List<ActivityDTO> findActivityDTOByClientIdAndTemplateId(Integer clientId, Long templateId) throws GesoftProjectException {
        return null;
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
        return null;
    }

    @Override
    public Project findProjectById(Integer clientId, Long projectId) throws GesoftProjectException {
        return null;
    }

    @Override
    public List<ActivityDTO> findActivityDTOByClientIdAndProjectId(Integer clientId, Long projectId) throws GesoftProjectException {
        return null;
    }

    @Override
    public List<ProjectExecution> findProjectExecutionsByProjectId(Integer clientId, Long projectId) throws GesoftProjectException {
        return null;
    }

    @Override
    public Project createProject(Integer clientId, Project project) throws GesoftProjectException {
        return null;
    }

    @Override
    public Project updateProject(Integer clientId, Long projectId, Project project) throws GesoftProjectException {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteProject(Integer clientId, Long projectId) throws GesoftProjectException {
        return null;
    }

    @Override
    public List<ProjectExecutionDTO> findProjectDTOByClientIdAndProjectTypeIdAndYearSinceAndPeriodSince(Integer clientId, Integer projectTypeId, Integer yearSince, String periodSince) throws GesoftProjectException {
        return null;
    }

    @Override
    public Page<WorkflowView> findAllWorkflowViews(Integer clientId, Specification<WorkflowView> workFlowViewSpec, Pageable pageable) throws GesoftProjectException {
        return null;
    }

    @Override
    public List<ActivityDTO> findActivityDTOByClientIdAndProjectExecutionId(Integer clientId, Long projectExecutionId) throws GesoftProjectException {
        return null;
    }

    @Override
    public ProjectExecution updateProjectExecution(Integer clientId, Long projectExecutionId, ProjectExecution projectExecution) throws GesoftProjectException {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteProjectExecution(Integer clientId, Long projectExecutionId) throws GesoftProjectException {
        return null;
    }

    @Override
    public ActivityDTO findActivityDTOByClientIdAndActivityId(Integer clientId, String activityId) throws GesoftProjectException {
        return null;
    }

    @Override
    public List<ActivityComment> findActivityCommentByClientIdAndActivityId(Integer clientId, String activityId) throws GesoftProjectException {
        return null;
    }

    @Override
    public List<ActivityAttachment> findActivityAttachmentByClientIdAndActivityId(Integer clientId, String activityId) throws GesoftProjectException {
        return null;
    }

    @Override
    public Activity updateActivityAdvance(Integer clientId, User user, String activityId, Activity activity) throws GesoftProjectException {
        return null;
    }

    @Override
    public Activity updateActivityApprove(Integer clientId, String activityId) throws GesoftProjectException {
        return null;
    }

    @Override
    public Activity updateActivityReject(Integer clientId, String activityId) throws GesoftProjectException {
        return null;
    }

    @Override
    public Activity updateActivityPending(Integer clientId, String activityId) throws GesoftProjectException {
        return null;
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
