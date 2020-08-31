/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * TEMPLATE-BACKEND-SPRING-BOOT
 */
package cl.com.gesoft.workflow.model;

/**
 * The Interface ProjectModel.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
public interface ProjectModelIdAndClientId extends ProjectModel{

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
