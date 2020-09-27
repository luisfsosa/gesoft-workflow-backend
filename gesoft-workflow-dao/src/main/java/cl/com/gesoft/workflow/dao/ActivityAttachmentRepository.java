/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * GESOFT-WORKFLOW-BACKEND
 */
package cl.com.gesoft.workflow.dao;

import cl.com.gesoft.workflow.model.ActivityAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Activity Attached repository.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
@Repository
public interface ActivityAttachmentRepository extends JpaRepository<ActivityAttachment, Long> {

    /**
     * Find by id o order by attachment date list.
     *
     * @param activityId the activity id
     * @return the list
     */
    List<ActivityAttachment> findByClientIdAndActivityIdOrderByAttachmentDate(Integer clientId, String activityId);

}
