/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * TEMPLATE-BACKEND-SPRING-BOOT
 */
package cl.com.gesoft.workflow.dao;

import cl.com.gesoft.workflow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * The interface User repository.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find by email ignore case and password user.
     *
     * @param email    the email
     * @param password the password
     * @return the user
     */
    User findByEmailIgnoreCaseAndPassword(String email, String password);

    /**
     * Find by rol in and client id list.
     *
     * @param rolNames the rol names
     * @param clientId the client id
     * @return the list
     */
    List<User> findByRolInAndClientId(Collection<String> rolNames, Integer clientId);
}
