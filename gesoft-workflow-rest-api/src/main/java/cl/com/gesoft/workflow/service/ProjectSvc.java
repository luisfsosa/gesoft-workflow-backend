/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * TEMPLATE-BACKEND-SPRING-BOOT
 */
package cl.com.gesoft.workflow.service;

import cl.com.gesoft.workflow.base.exception.GesoftProjectException;
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
    //@QuoAcl(ignore = true) //para cuando quieres que se pueda entrar al metodo sin estar logeado (osea sin token)
    //@GesoftAcl(expression = "#acl.isUser()")  // cuando quieres que se pueda entrar al metodo pero tiene que estar logeado
    @GesoftAcl(expression = "#acl.hasPermission('OBTENER_USUARIO')")
    //@GesoftAcl(expression = "#acl.hasPermission({'OBTENER_USUARIO', 'CREAR_USUARIO'})") // cuando quieres que solo si se cumple el permiso pueda entrar
    List<User> findAllUsersByRolAndClientId(List<String> rolNames, Integer clientId) throws GesoftProjectException;

}
