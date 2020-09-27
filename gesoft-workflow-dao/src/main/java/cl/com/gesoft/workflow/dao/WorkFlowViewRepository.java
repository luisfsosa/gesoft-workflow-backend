/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * GESOFT-WORKFLOW-BACKEND
 */
package cl.com.gesoft.workflow.dao;

import cl.com.gesoft.workflow.model.WorkflowView;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface WorkFlow View repository.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
@Repository
public interface WorkFlowViewRepository extends PagingAndSortingRepository<WorkflowView, Long>, JpaSpecificationExecutor<WorkflowView> {


    /**
     * Find by project type id and template id and year list.
     *
     * @param projectTypeId the project type id
     * @param templateId    the template id
     * @param year          the year
     * @return the list
     */
    List<WorkflowView> findByProjectTypeIdAndTemplateIdAndYear(Integer projectTypeId, Integer templateId, Integer year);

    /**
     * Find by project type id and template id and year and period list.
     *
     * @param projectTypeId the project type id
     * @param templateId    the template id
     * @param year          the year
     * @param period        the period
     * @return the list
     */
    List<WorkflowView> findByProjectTypeIdAndTemplateIdAndYearAndPeriod(Integer projectTypeId, Integer templateId, Integer year, String period);

    /**
     * Find by project id list.
     *
     * @param projectId the project id
     * @return the list
     */
    List<WorkflowView> findByProjectId(Long projectId);

}
