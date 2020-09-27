/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * TEMPLATE-BACKEND-SPRING-BOOT
 */
package cl.com.gesoft.workflow.service;

import cl.com.gesoft.workflow.base.exception.GesoftProjectException;
import cl.com.gesoft.workflow.dao.dto.TemplateDTO;
import cl.com.gesoft.workflow.model.Client;
import cl.com.gesoft.workflow.model.ProjectType;
import cl.com.gesoft.workflow.model.Template;
import cl.com.gesoft.workflow.model.User;
import cl.com.gesoft.workflow.security.AuthResponse;
import cl.com.gesoft.security.api.GesoftAcl;

import java.util.List;

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

    @GesoftAcl(expression = "#acl.hasPermission('CREAR_PLANTILLA')")
    @Transactional(rollbackFor = GesoftProjectException.class)
    Template createTemplate(Integer clientId, Template template) throws GesoftProjectException;

    @GesoftAcl(expression = "#acl.hasPermission('MODIFICAR_PLANTILLA')")
    @Transactional(rollbackFor = GesoftProjectException.class)
    Template updateTemplate(Integer clientId, Long templateId, Template template) throws GesoftProjectException;

}
