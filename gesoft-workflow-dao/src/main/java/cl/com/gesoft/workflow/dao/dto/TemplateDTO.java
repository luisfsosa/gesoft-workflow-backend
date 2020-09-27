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
public interface TemplateDTO {

    /**
     * Gets id.
     *
     * @return the id
     */
    Long getId();

    /**
     * Gets rfc.
     *
     * @return the rfc
     */
    String getName();

    /**
     * Gets business name.
     *
     * @return the business name
     */
    String getDescription();

    /**
     * Gets tax regime id.
     *
     * @return the tax regime id
     */
    String getCuttingDay();


    /**
     * Gets project type name.
     *
     * @return the project type name
     */
    String getProjectTypeName();

    /**
     * Gets project type id.
     *
     * @return the project type id
     */
    Integer getProjectTypeId();


    /**
     * Is order template boolean.
     *
     * @return the boolean
     */
    boolean isOrderTemplate();
}
