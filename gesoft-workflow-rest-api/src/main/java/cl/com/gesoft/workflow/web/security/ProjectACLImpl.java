/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * TEMPLATE-BACKEND-SPRING-BOOT
 */
package cl.com.gesoft.workflow.web.security;

import cl.com.gesoft.workflow.dao.RolPermissionRepository;
import cl.com.gesoft.workflow.model.RolPermission;
import cl.com.gesoft.workflow.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

/**
 * The type Project acl.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
public class ProjectACLImpl {
	/**
	 * The constant log.
	 */
	public static final Log log = LogFactory.getLog(ProjectACLImpl.class);

	/**
	 * The Rol permission repository.
	 */
	@Autowired
	RolPermissionRepository rolPermissionRepository;

	/**
	 * Has permission boolean.
	 *
	 * @param permissionName the permission name
	 * @return the boolean
	 */
	public boolean hasPermission(String permissionName) {

		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();

		if (authentication.getPrincipal() instanceof User) {
			User user = (User) authentication.getPrincipal();

			List<RolPermission> rolPermissions = rolPermissionRepository.findByRol(user.getRol());
			if (rolPermissions == null) {
				return false;
			}

			return rolPermissions.stream().anyMatch(rolPermission ->  StringUtils.equalsIgnoreCase(rolPermission.getPermission(), permissionName));

		}
		log.warn("No tiene permiso el permiso [" + permissionName + "] ");
		return false;
	}

	/**
	 * Has permission boolean.
	 *
	 * @param permissionNames the permission names
	 * @return the boolean
	 */
	public boolean hasPermission(List<String> permissionNames) {
		return permissionNames.stream().anyMatch(permissionName -> hasPermission(permissionName));
	}

	/**
	 * Is user boolean.
	 *
	 * @return the boolean
	 */
	public boolean isUser() {
		SecurityContext securityContext = SecurityContextHolder.getContext();
		Authentication authentication = securityContext.getAuthentication();

		if (authentication.getPrincipal() instanceof User) {
			User user = (User) authentication.getPrincipal();

			List<RolPermission> rolPermissions = rolPermissionRepository.findByRol(user.getRol());
			if (rolPermissions != null && rolPermissions.size() > 0) {
				return true;
			}
		}
		return false;
	}
}
