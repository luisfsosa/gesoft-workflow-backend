/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * TEMPLATE-BACKEND-SPRING-BOOT
 */
package cl.com.gesoft.workflow.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Objects;

/**
 * The Class User.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
@Entity
@Table(name = "gwf_user")
public class User implements Serializable, Cloneable, ProjectModelIdAndClientId {

    private static final long serialVersionUID = 5039354116929239575L;

    /**
     * Gets serial version uid.
     *
     * @return the serial version uid
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "name", nullable = false)
    @NotEmpty(message = "Name is required")
    private String name;

    @Column(name = "email" ,nullable = false)
    @NotEmpty(message = "Email is required")
    private String email;

    @Column(name = "password" ,nullable = false)
    @NotEmpty(message = "Password is required")
    private String password;

    @Column(name = "rol" ,nullable = false)
    @NotEmpty(message = "Rol is required")
    private String rol;

    @Column(name = "client_id" ,nullable = false)
    @NotEmpty(message = "Cliente ID is required")
    private Integer clientId;

    /**
     * The id.  @return the id
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets rol.
     *
     * @return the rol
     */
    public String getRol() {
        return rol;
    }

    /**
     * Sets rol.
     *
     * @param rol the rol
     */
    public void setRol(String rol) {
        this.rol = rol;
    }

    /**
     * Gets client id.
     *
     * @return the client id
     */
    public Integer getClientId() {
        return clientId;
    }

    /**
     * Sets client id.
     *
     * @param clientId the client id
     */
    public void setClientId(Integer clientId) {
        this.clientId = clientId;
    }

    /**
     * Equals.
     *
     * @param o the o
     * @return true, if successful
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        return getId() != null && Objects.equals(getId(), ((User) o).getId());
    }

    /**
     * Hash code.
     *
     * @return the int
     */
    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    /**
     * To Clone.
     *
     * @return the User
     */
    @Override
    public User clone() {
        User clon = null;
        try {
            clon = (User) super.clone();
        } catch (CloneNotSupportedException e) {
            System.out.println(" no se puede duplicar");
        }
        return clon;
    }

    /**
     * To string.
     *
     * @return the string
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", name='" + getName() + "'" +
                ", email='" + getEmail() + "'" +
                ", rol='" + getRol() + "'" +
                ", clientId='" + getClientId() + "'" +
                "}";
    }
}
