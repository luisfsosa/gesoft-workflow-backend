/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * GESOFT-WORKFLOW-BACKEND
 */
package cl.com.gesoft.workflow.dao;

import cl.com.gesoft.workflow.dao.dto.ProjectDTO;
import cl.com.gesoft.workflow.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Project repository.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {


    /**
     * Find project dto by client id list.
     *
     * @param clientId the client id
     * @return the list
     */
    @Query(
            value=  "SELECT gwp.id, gwp.name, gwp.project_type_id as projectTypeId , gwpt.name as projectTypeName, gwp.gwf_client_id as gwfclientId, gwp.template_id as templateId, gwp.business_name as gwfClientName, gwp.user_id as userId, u.email as userName " +
                    " FROM gwf_project gwp " +
                    " INNER JOIN gwf_project_type gwpt on gwp.project_type_id = gwpt.id " +
                    " INNER JOIN usuarios u on qwp.user_id = u.id " +
                    " INNER JOIN qwf_client gwc on gwp.workflow_client_id = gwc.id " +
                    " WHERE gwp.cliente_id = :clientId ",
            nativeQuery = true
    )
    List<ProjectDTO> findProjectDTOByClientId(Integer clientId);


    /**
     * Find by id and client id optional.
     *
     * @param id       the id
     * @param clientId the client id
     * @return the optional
     */
    Optional<Project> findByIdAndClientId(Long id, Integer clientId);


    /**
     * Find by project until is null and rfc active list.
     *
     * @return the list
     */
    @Query(
            value=  " SELECT * " +
                    " FROM gwf_project qwp " +
                    " INNER JOIN gwf_client qwc on gwp.gwf_client_id = gwc.id " +
                    " WHERE gwc.rfc IN (  " +
                    "            SELECT DISTINCT rfc FROM rfc_cliente " +
                    "            WHERE estatus = 'ACTIVO' \n" +
                    "           ) " +
                    " AND gwc.active = TRUE " +
                    " AND gwp.period_until IS NULL ",
            nativeQuery = true
    )
    List<Project> findByProjectUntilIsNullAndRFCActive();

}
