/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * TEMPLATE-BACKEND-SPRING-BOOT
 */
package cl.com.gesoft.workflow.dao;

import cl.com.gesoft.workflow.model.RolPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Rol permission repository.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
@Repository
public interface RolPermissionRepository extends JpaRepository<RolPermission, Long> {
    /**
     * Find by rol list.
     *
     * @param rol the rol
     * @return the list
     */
    List<RolPermission> findByRol(String rol);
}
