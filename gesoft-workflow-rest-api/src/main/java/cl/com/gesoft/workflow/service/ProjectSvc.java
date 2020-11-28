/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * TEMPLATE-BACKEND-SPRING-BOOT
 */
package cl.com.gesoft.workflow.service;

import cl.com.gesoft.workflow.base.exception.GesoftProjectException;
import cl.com.gesoft.workflow.dao.dto.ActivityDTO;
import cl.com.gesoft.workflow.dao.dto.ProjectDTO;
import cl.com.gesoft.workflow.dao.dto.ProjectExecutionDTO;
import cl.com.gesoft.workflow.dao.dto.TemplateDTO;
import cl.com.gesoft.workflow.model.*;
import cl.com.gesoft.workflow.security.AuthResponse;
import cl.com.gesoft.security.api.GesoftAcl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

/**
 * The interface Project svc.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
public interface ProjectSvc {


    @GesoftAcl(ignore = true)
	@Transactional
	AuthResponse login(String username, String password) throws Exception;

    /**
     * Find all users by rol and client id list.
     *
     * @param rolNames the rol names
     * @param clientId the client id
     * @return the list
     * @throws GesoftProjectException the gesoft project exception
     */
    //@GesoftAcl(ignore = true) //para cuando quieres que se pueda entrar al metodo sin estar logeado (osea sin token)
    //@GesoftAcl(expression = "#acl.isUser()")  // cuando quieres que se pueda entrar al metodo pero tiene que estar logeado
    @GesoftAcl(expression = "#acl.hasPermission('OBTENER_USUARIO')")
    //@GesoftAcl(expression = "#acl.hasPermission({'OBTENER_USUARIO', 'CREAR_USUARIO'})") // cuando quieres que solo si se cumple el permiso pueda entrar
    List<User> findAllUsersByRolAndClientId(List<String> rolNames, Integer clientId) throws GesoftProjectException;

    @GesoftAcl(expression = "#acl.hasPermission('OBTENER_CLIENTE')")
    List<Client> findAllClientsByClientId(Integer clientId) throws GesoftProjectException;

    @GesoftAcl(expression = "#acl.hasPermission('OBTENER_CLIENTE')")
    List<Client> findAllActiveClientsByClientId(Integer clientId) throws GesoftProjectException;

    @GesoftAcl(expression = "#acl.hasPermission('OBTENER_CLIENTE')")
    Client findClientById(Integer clientId, Long workFlowClientId) throws GesoftProjectException;

    @GesoftAcl(expression = "#acl.hasPermission('CREAR_CLIENTE')")
    @Transactional(rollbackFor = GesoftProjectException.class)
    Client createClient(Integer clientId, Client client) throws GesoftProjectException;

    @GesoftAcl(expression = "#acl.hasPermission('MODIFICAR_CLIENTE')")
    @Transactional(rollbackFor = GesoftProjectException.class)
    Client updateClient(Integer clientId, Long workFlowClientId, Client client) throws GesoftProjectException;

    @GesoftAcl(expression = "#acl.hasPermission('MODIFICAR_CLIENTE')")
    @Transactional(rollbackFor = GesoftProjectException.class)
    Client updateClientState(Integer clientId, Long workFlowClientId, Client client) throws GesoftProjectException;

    @GesoftAcl(expression = "#acl.hasPermission('OBTENER_TIPO_PROYECTO')")
    List<ProjectType> findAllProjectTypes() throws GesoftProjectException;

    @GesoftAcl(expression = "#acl.hasPermission('OBTENER_TIPO_PROYECTO')")
    ProjectType findProjectTypeById(Integer projectTypeId) throws GesoftProjectException;

    @GesoftAcl(expression = "#acl.hasPermission('OBTENER_PANTILLA')")
    List<TemplateDTO> findAllTemplatesByClientId(Integer clientId) throws GesoftProjectException;

    @GesoftAcl(expression = "#acl.hasPermission('OBTENER_PANTILLA')")
    List<Template> findTemplateByClientIdAndProjectTypeId(Integer clientId, Long projectTypeId) throws GesoftProjectException;

    @GesoftAcl(expression = "#acl.hasPermission('OBTENER_PANTILLA')")
    List<ActivityDTO> findActivityDTOByClientIdAndTemplateId(Integer clientId, Long templateId) throws GesoftProjectException;

    @GesoftAcl(expression = "#acl.hasPermission('CREAR_PLANTILLA')")
    @Transactional(rollbackFor = GesoftProjectException.class)
    Template createTemplate(Integer clientId, Template template) throws GesoftProjectException;

    @GesoftAcl(expression = "#acl.hasPermission('MODIFICAR_PLANTILLA')")
    @Transactional(rollbackFor = GesoftProjectException.class)
    Template updateTemplate(Integer clientId, Long templateId, Template template) throws GesoftProjectException;

    @GesoftAcl(expression = "#acl.hasPermission('OBTENER_PROYECTO')")
    List<ProjectDTO> findAllProjectsByClientId(Integer clientId) throws GesoftProjectException;

    @GesoftAcl(expression = "#acl.hasPermission('OBTENER_PROYECTO')")
    Project findProjectById(Integer clientId, Long projectId) throws GesoftProjectException;

    @GesoftAcl(expression = "#acl.hasPermission('OBTENER_PROYECTO')")
    List<ActivityDTO> findActivityDTOByClientIdAndProjectId(Integer clientId, Long projectId) throws GesoftProjectException;

    @GesoftAcl(expression = "#acl.hasPermission('OBTENER_PROYECTO')")
    List<ProjectExecution> findProjectExecutionsByProjectId(Integer clientId, Long projectId) throws GesoftProjectException;

    @GesoftAcl(expression = "#acl.hasPermission('CREAR_PROYECTO')")
    @Transactional(rollbackFor = GesoftProjectException.class)
    Project createProject(Integer clientId, Project project) throws GesoftProjectException;

    @GesoftAcl(expression = "#acl.hasPermission('MODIFICAR_PROYECTO')")
    @Transactional(rollbackFor = GesoftProjectException.class)
    Project updateProject(Integer clientId, Long projectId, Project project) throws GesoftProjectException;

    @GesoftAcl(expression = "#acl.hasPermission('ELIMINAR_PROYECTO')")
    @Transactional(rollbackFor = GesoftProjectException.class)
    ResponseEntity<?> deleteProject(Integer clientId, Long projectId) throws GesoftProjectException;

    @GesoftAcl(expression = "#acl.hasPermission('OBTENER_EJECUCION_PROYECTO')")
    List<ProjectExecutionDTO> findProjectDTOByClientIdAndProjectTypeIdAndYearSinceAndPeriodSince(Integer clientId, Integer projectTypeId, Integer yearSince, String periodSince) throws GesoftProjectException;

    @GesoftAcl(expression = "#acl.hasPermission('OBTENER_EJECUCION_PROYECTO')")
    Page<WorkflowView> findAllWorkflowViews(Integer clientId, Specification<WorkflowView> workFlowViewSpec, Pageable pageable) throws GesoftProjectException;

    /*@GesoftAcl(expression = "#acl.hasPermission('OBTENER_EJECUCION_PROYECTO')")
    WorkflowFilterNumbers findAllWorkflowFilterNumbers(ProjectExecution projectExecution) throws GesoftProjectException;*/

    @GesoftAcl(expression = "#acl.hasPermission('OBTENER_EJECUCION_PROYECTO')")
    List<ActivityDTO> findActivityDTOByClientIdAndProjectExecutionId(Integer clientId, Long projectExecutionId) throws GesoftProjectException;

    @GesoftAcl(expression = "#acl.hasPermission('MODIFICAR_PROYECTO')")
    @Transactional(rollbackFor = GesoftProjectException.class)
    ProjectExecution updateProjectExecution(Integer clientId, Long projectExecutionId, ProjectExecution projectExecution) throws GesoftProjectException;

    @GesoftAcl(expression = "#acl.hasPermission('MODIFICAR_PROYECTO')")
    @Transactional(rollbackFor = GesoftProjectException.class)
    ResponseEntity<?> deleteProjectExecution(Integer clientId, Long projectExecutionId) throws GesoftProjectException;

    @GesoftAcl(expression = "#acl.hasPermission('OBTENER_EJECUCION_PROYECTO')")
    ActivityDTO findActivityDTOByClientIdAndActivityId(Integer clientId, String activityId) throws GesoftProjectException;

    @GesoftAcl(expression = "#acl.hasPermission('OBTENER_EJECUCION_PROYECTO')")
    List<ActivityComment> findActivityCommentByClientIdAndActivityId(Integer clientId, String activityId) throws GesoftProjectException;

    @GesoftAcl(expression = "#acl.hasPermission('OBTENER_EJECUCION_PROYECTO')")
    List<ActivityAttachment> findActivityAttachmentByClientIdAndActivityId(Integer clientId, String activityId) throws GesoftProjectException;

    @GesoftAcl(expression = "#acl.hasPermission('MODIFICAR_EJECUCION_ACTIVIDAD')")
    Activity updateActivityAdvance(Integer clientId, User user, String activityId, Activity activity) throws GesoftProjectException;

    @GesoftAcl(expression = "#acl.hasPermission('APROBAR_EJECUCION_ACTIVIDAD')")
    Activity updateActivityApprove(Integer clientId, String activityId) throws GesoftProjectException;

    @GesoftAcl(expression = "#acl.hasPermission('RECHAZAR_EJECUCION_ACTIVIDAD')")
    Activity updateActivityReject(Integer clientId, String activityId) throws GesoftProjectException;

    @GesoftAcl(expression = "#acl.hasPermission('RECHAZAR_EJECUCION_ACTIVIDAD')")
    Activity updateActivityPending(Integer clientId, String activityId) throws GesoftProjectException;



}
