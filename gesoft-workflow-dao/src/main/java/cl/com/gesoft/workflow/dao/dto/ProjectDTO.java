/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 *  GESOFT-WORKFLOW-BACKEND
 */
package cl.com.gesoft.workflow.dao.dto;

/**
 * The interface Template dto.
 */
public interface ProjectDTO {

    /**
     * Gets id.
     *
     * @return the id
     */
    Long getId();


    /**
     * Gets name.
     *
     * @return the name
     */
    String getName();


    /**
     * Gets gwfclient id.
     *
     * @return the gwfclient id
     */
    Integer getGwfclientId();


    /**
     * Gets gwfclient name.
     *
     * @return the gwfclient name
     */
    String getGwfclientName();


    /**
     * Gets project type id.
     *
     * @return the project type id
     */
    String getProjectTypeId();


    /**
     * Gets project type name.
     *
     * @return the project type name
     */
    String getProjectTypeName();

    /**
     * Gets user id.
     *
     * @return the user id
     */
    Integer getUserId();

    /**
     * Template id integer.
     *
     * @return the integer
     */
    Integer getTemplateId();

    /**
     * Gets user name.
     *
     * @return the user name
     */
    String getUserName();

    /**
     * Gets group name.
     *
     * @return the group name
     */
    String getGroupName();
}
