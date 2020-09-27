/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * GESOFT-WORKFLOW-BACKEND
 */
package cl.com.gesoft.workflow.dao;

import cl.com.gesoft.workflow.dao.dto.ActivityDTO;
import cl.com.gesoft.workflow.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * The interface Activity repository.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
@Repository
public interface ActivityRepository extends JpaRepository<Activity, String> {


    /**
     * Find client dto by client id list.
     *
     * @param clientId   the client id
     * @param templateId the template id
     * @return the list
     */
    @Query(value= " SELECT gwa.id, gwa.name, gwa.description, gwa.activity_type as activityType, gwa.authorizer as authorizer, u.email as authorizerName, gwa.responsable as responsable, u2.email as responsableName,gwa.weighing, gwa.evidence_required as evidenceRequired, gwa.predecessor_activities as predecessorActivities, gwa.activity_order as activityOrder  " +
                  " FROM gwf_activity gwa " +
                  " LEFT OUTER JOIN usuarios u on gwa.authorizer = u.id " +
                  " LEFT OUTER JOIN usuarios u2 on gwa.responsable = u2.id " +
                  " WHERE gwa.template_id = :templateId " +
                  " AND gwa.cliente_id = :clientId " +
                  " ORDER BY gwa.activity_order ",
            nativeQuery = true
    )
    List<ActivityDTO> findActivityDTOByClientIdAndTemplateId(Integer clientId, Long templateId);


    /**
     * Find activity dto by client id and project id list.
     *
     * @param clientId  the client id
     * @param projectId the project id
     * @return the list
     */
    @Query(value= " SELECT gwa.id, gwa.name, gwa.description, gwa.activity_type as activityType, gwa.authorizer as authorizer, u.email as authorizerName, gwa.responsable as responsable, u2.email as responsableName,gwa.weighing, gwa.evidence_required as evidenceRequired,qwa.predecessor_activities as predecessorActivities, qwa.activity_order as activityOrder  " +
            " FROM gwf_activity gwa " +
            " LEFT OUTER JOIN usuarios u on gwa.authorizer = u.id " +
            " LEFT OUTER JOIN usuarios u2 on gwa.responsable = u2.id " +
            " WHERE gwa.project_id = :projectId " +
            " AND gwa.cliente_id = :clientId " +
            " ORDER BY gwa.activity_order ",
            nativeQuery = true
    )
    List<ActivityDTO> findActivityDTOByClientIdAndProjectId(Integer clientId, Long projectId);


    /**
     * Find activity dto by client id and project execution id list.
     *
     * @param clientId           the client id
     * @param projectExecutionId the project execution id
     * @return the list
     */
    @Query(value= " SELECT gwa.id, gwa.name, gwa.description, gwa.status, gwa.weighing , qwa.activity_type as activityType, qwa.responsable as responsable, u.name as responsableName, qwa.authorizer as authorizer, u2.name as authorizerName, qwa.comments, qwa.attached, qwa.activity_advance as activityAdvance, qwa.evidence_required as evidenceRequired, qwa.activity_order as activityOrder, qwa.predecessor_activities as predecessorActivities " +
            " FROM gwf_activity gwa " +
            " INNER JOIN usuarios u on gwa.responsable = u.id " +
            " LEFT OUTER  JOIN usuarios u2 on gwa.authorizer = u2.id " +
            " WHERE gwa.project_execution_id = :projectExecutionId " +
            " AND gwa.cliente_id = :clientId " +
            " ORDER BY gwa.activity_order ",
            nativeQuery = true
    )
    List<ActivityDTO> findActivityDTOByClientIdAndProjectExecutionId(Integer clientId, Long projectExecutionId);

    /**
     * Find activity dto by client id and activity id activity dto.
     *
     * @param clientId   the client id
     * @param activityId the activity id
     * @return the activity dto
     */
    @Query(value= " SELECT gwa.id, gwa.name, gwa.status, gwa.weighing , qwa.activity_type as activityType, qwa.responsable as responsable, u.name as responsableName, qwa.authorizer as authorizer, u2.nombre as authorizerName, qwa.comments, qwa.attached, qwa.activity_advance as activityAdvance, qwa.evidence_required as evidenceRequired, qwa.activity_order as activityOrder, qwa.predecessor_activities as predecessorActivities " +
            " FROM gwf_activity gwa " +
            " INNER JOIN usuarios u on gwa.responsable = u.id " +
            " LEFT OUTER  JOIN usuarios u2 on gwa.authorizer = u2.id " +
            " WHERE gwa.id = :activityId " +
            " AND gwa.cliente_id = :clientId " ,
            nativeQuery = true
    )
    ActivityDTO findActivityDTOByClientIdAndActivityId(Integer clientId, String activityId);


    /**
     * Find by client id and project id list.
     *
     * @param clientId  the client id
     * @param projectId the project id
     * @return the list
     */
    List<Activity> findByClientIdAndProjectIdOrderByActivityOrder(Integer clientId, Long projectId);


    /**
     * Delete by template id.
     *
     * @param templateId the template id
     */
    void deleteByTemplateId(Long templateId);

    /**
     * Delete by project id.
     *
     * @param projectId the project id
     */
    void deleteByProjectId(Long projectId);

    /**
     * Delete by project execution id.
     *
     * @param projectExecutionId the project execution id
     */
    void deleteByProjectExecutionId(Long projectExecutionId);

    /**
     * Find by id and client id optional.
     *
     * @param id       the id
     * @param clientId the client id
     * @return the optional
     */
    Optional<Activity> findByIdAndClientId(String id, Integer clientId);

}
