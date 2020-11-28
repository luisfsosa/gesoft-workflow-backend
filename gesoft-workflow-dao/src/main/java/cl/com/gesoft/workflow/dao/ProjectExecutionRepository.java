/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * GESOFT-WORKFLOW-BACKEND
 */
package cl.com.gesoft.workflow.dao;

import cl.com.gesoft.workflow.dao.dto.ProjectExecutionDTO;
import cl.com.gesoft.workflow.model.ProjectExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Project Execution repository.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
@Repository
public interface ProjectExecutionRepository extends JpaRepository<ProjectExecution, Long> {


    /**
     * Find project dto by client id and project type id and year since list.
     *
     * @param clientId      the client id
     * @param projectTypeId the project type id
     * @param year          the year
     * @return the list
     */
    @Query(
            value=  " SELECT gwpe.id, gwpe.project_type_id as projectTypeId, gwpt.name as projectTypeName, gwpe.year as year, gwpe.period as period, gwpe.user_id as userId, u.email as userName, gwpe.name,  gwpe.gwf_client_id as gwfClientId, gwc.business_name as gwfClientName,gwpe.project_advance as projectAdvance,  gwpe.project_id as projectId,  gwpe.is_order as orderExecution  " +
                    " FROM gwf_project_execution gwpe " +
                    " INNER JOIN gwf_project_type gwpt on gwpe.project_type_id = gwpt.id " +
                    " INNER JOIN gwf_user u on gwpe.user_id = u.id " +
                    " INNER JOIN gwf_client gwc on gwpe.gwf_client_id = gwc.id " +
                    " WHERE gwpe.cliente_id = :clientId " +
                    " AND gwpe.project_type_id = :projectTypeId " +
                    " AND gwpe.year = :year " +
                    " AND CURRENT_DATE >=  gwpe.project_since ",
            nativeQuery = true
    )
    List<ProjectExecutionDTO> findProjectDTOByClientIdAndProjectTypeIdAndYearSince(Integer clientId, Integer projectTypeId, Integer year);


    /**
     * Find project dto by client id and project type id and year since and period since list.
     *
     * @param clientId      the client id
     * @param projectTypeId the project type id
     * @param year          the year
     * @param period        the period
     * @return the list
     */
    @Query(
            value=  " SELECT gwpe.id, gwpe.project_type_id as projectTypeId, gwpt.name as projectTypeName, gwpe.year as year, gwpe.period as period, gwpe.user_id as userId, u.email as userName, gwpe.name,  gwpe.gwf_client_id as gwfClientId, gwc.business_name as gwfClientName,gwpe.project_advance as projectAdvance,  gwpe.project_id as projectId,  gwpe.is_order as orderExecution  " +
                    " FROM gwf_project_execution gwpe " +
                    " INNER JOIN gwf_project_type gwpt on gwpe.project_type_id = gwpt.id " +
                    " INNER JOIN gwf_user u on gwpe.user_id = u.id " +
                    " INNER JOIN gwf_client gwc on gwpe.gwf_client_id = gwc.id " +
                    " WHERE gwpe.cliente_id = :clientId " +
                    " AND gwpe.project_type_id = :projectTypeId " +
                    " AND gwpe.year = :year " +
                    " AND gwpe.period = :period " +
                    " AND CURRENT_DATE >=  gwpe.project_since ",
            nativeQuery = true
    )
    List<ProjectExecutionDTO> findProjectDTOByClientIdAndProjectTypeIdAndYearSinceAndPeriodSince(Integer clientId, Integer projectTypeId, Integer year, String period);


    /**
     * Find by project id and client id list.
     *
     * @param projectId the project id
     * @param clientId  the client id
     * @return the list
     */
    List<ProjectExecution> findByProjectIdAndClientIdOrderByProjectSinceAsc(Long projectId, Integer clientId);


    /**
     * Find by max project until optional.
     *
     * @param projectId the project id
     * @return the optional
     */
    @Query(
            value=  " SELECT * FROM qwf_project_execution gpe " +
                    " WHERE gpe.project_until = ( " +
                    "   SELECT MAX(gpe.project_until) FROM qwf_project_execution gpe " +
                    "   WHERE qpe.project_id = :projectId " +
                    " )   " +
                    " AND gpe.project_id = :projectId ",
            nativeQuery = true
    )
    Optional<ProjectExecution> findByMaxProjectUntil(Long projectId);

    /**
     * Find by id and client id list.
     *
     * @param id       the id
     * @param clientId the client id
     * @return the list
     */
    Optional<ProjectExecution> findByIdAndClientId(Long id, Integer clientId);
}
