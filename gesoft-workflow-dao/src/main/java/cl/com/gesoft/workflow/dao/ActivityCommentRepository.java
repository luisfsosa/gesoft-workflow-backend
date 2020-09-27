/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * GESOFT-WORKFLOW-BACKEND
 */
package cl.com.gesoft.workflow.dao;

import cl.com.gesoft.workflow.model.ActivityComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * The interface Activity Comment repository.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
@Repository
public interface ActivityCommentRepository extends JpaRepository<ActivityComment, Long> {

    /**
     * Find by id o order by comment date list.
     *
     * @param activityId the activity id
     * @return the list
     */
    List<ActivityComment> findByClientIdAndActivityIdOrderByCommentDate(Integer clientId, String activityId);

}
