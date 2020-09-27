/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 *  GESOFT-WORKFLOW-BACKEND
 */
package cl.com.gesoft.workflow.dao.dto;


/**
 * The interface Activity dto.
 */
public interface ActivityDTO {

    /**
     * Gets id.
     *
     * @return the id
     */
    String getId();

    /**
     * Gets rfc.
     *
     * @return the rfc
     */
    String getName();

    /**
     * Gets description.
     *
     * @return the description
     */
    String getDescription();

    /**
     * Gets weighing.
     *
     * @return the weighing
     */
    Integer getWeighing();

    /**
     * Gets activity type.
     *
     * @return the activity type
     */
    String getActivityType();

    /**
     * Is evidence required boolean.
     *
     * @return the boolean
     */
    boolean isEvidenceRequired();


    /**
     * Gets authorizer id.
     *
     * @return the authorizer id
     */
    Integer getAuthorizer();


    /**
     * Gets authorizer name.
     *
     * @return the authorizer name
     */
    String getAuthorizerName();

    /**
     * Gets responsable id.
     *
     * @return the responsable id
     */
    Integer getResponsable();

    /**
     * Gets responsable name.
     *
     * @return the responsable name
     */
    String getResponsableName();

    /**
     * Gets activity order.
     *
     * @return the activity order
     */
    Integer getActivityOrder();

    /**
     * Gets predecessor activities.
     *
     * @return the predecessor activities
     */
    String[] getPredecessorActivities();

    /**
     * Gets status.
     *
     * @return the status
     */
    String getStatus();

    /**
     * Gets comments.
     *
     * @return the comments
     */
    String getComments();

    /**
     * Gets attached.
     *
     * @return the attached
     */
    String getAttached();

    /**
     * Gets activity advance.
     *
     * @return the activity advance
     */
    Integer getActivityAdvance();
}
