/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * GESOFT-WORKFLOW-BACKEND
 */
package cl.com.gesoft.workflow.model;

/**
 * The Interface WorflowModel.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
public interface WorflowModelIdAndClientId extends WorflowModel{

    /**
     * Gets id.
     *
     * @return the id
     */
    Long getId();

    /**
     * Gets client id.
     *
     * @return the client id
     */
    Integer getClientId();
}
