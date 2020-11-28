/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * GESOFT-WORKFLOW-BACKEND
 */
package cl.com.gesoft.workflow.dao;

import cl.com.gesoft.workflow.dao.dto.TemplateDTO;
import cl.com.gesoft.workflow.model.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Template repository.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {


    /**
     * Find template dto by client id list.
     *
     * @param clientId the client id
     * @return the list
     */
    @Query(
            value=  "SELECT gwt.id, gwt.name, gwt.description, gwt.cutting_day as cuttingDay, gwt.id as projectTypeId , gwt.name as projectTypeName, gwt.is_order as orderTemplate " +
                    "FROM gwf_template gwt " +
                    "INNER JOIN gwf_project_type gwpt on gwt.project_type_id = gwpt.id " +
                    "WHERE gwt.cliente_id = :clientId ",
            nativeQuery = true
    )
    List<TemplateDTO> findTemplateDTOByClientId(Integer clientId);

    /**
     * Find by client id list.
     *
     * @param clientId the client id
     * @return the list
     */
    List<Template> findByClientId(Integer clientId);


    /**
     * Find by client id and project type id list.
     *
     * @param clientId      the client id
     * @param projectTypeId the project type id
     * @return the list
     */
    List<Template> findByClientIdAndProjectTypeId(Integer clientId, Long projectTypeId);

    /**
     * Find by id and client id optional.
     *
     * @param id       the id
     * @param clientId the client id
     * @return the optional
     */
    Optional<Template> findByIdAndClientId(Long id, Integer clientId);
}
