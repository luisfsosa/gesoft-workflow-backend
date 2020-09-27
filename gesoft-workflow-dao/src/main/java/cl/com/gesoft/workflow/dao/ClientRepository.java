/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * GESOFT-WORKFLOW-BACKEND
 */
package cl.com.gesoft.workflow.dao;

import cl.com.gesoft.workflow.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * The interface Client repository.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    /**
     * Find by client id list.
     *
     * @param clientId the client id
     * @return the list
     */
    List<Client> findByClientId(Integer clientId);


    /**
     * Find by client id and active list.
     *
     * @param clientId the client id
     * @param active   the active
     * @return the list
     */
    List<Client> findByClientIdAndActive(Integer clientId, boolean active);

    /**
     * Find by id and client id optional.
     *
     * @param id       the id
     * @param clientId the client id
     * @return the optional
     */
    Optional<Client> findByIdAndClientId(Long id, Integer clientId);
}
