/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * GESOFT-WORKFLOW-BACKEND
 */
package cl.com.gesoft.workflow.dao.dto;

/**
 * The interface Template dto.
 */
public interface ProjectExecutionDTO {

    /**
     * Gets id.
     *
     * @return the id
     */
    Long getId();

    /**
     * Gets project type id.
     *
     * @return the project type id
     */
    Integer getProjectTypeId();

    /**
     * Gets project type name.
     *
     * @return the project type name
     */
    String getProjectTypeName();

    /**
     * Gets year since.
     *
     * @return the year since
     */
    Integer getYearSince();

    /**
     * Gets period since.
     *
     * @return the period since
     */
    String getPeriodSince();


    /**
     * Gets year until.
     *
     * @return the year until
     */
    Integer getYearUntil();

    /**
     * Gets period until.
     *
     * @return the period until
     */
    String getPeriodUntil();

    /**
     * Gets user id.
     *
     * @return the user id
     */
    Integer getUserId();

    /**
     * Gets user name.
     *
     * @return the user name
     */
    String getUserName();

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
     * Gets work flowclient rfc.
     *
     * @return the work flowclient rfc
     */
    String getWorkFlowclientRfc();

    /**
     * Gets project advance.
     *
     * @return the project advance
     */
    Integer getProjectAdvance();

    /**
     * Gets project id.
     *
     * @return the project id
     */
    Integer getProjectId();


    /**
     * Is order execution boolean.
     *
     * @return the boolean
     */
    boolean isOrderExecution();

}
