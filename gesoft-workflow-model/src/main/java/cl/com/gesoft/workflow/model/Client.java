/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * GESOFT-WORKFLOW-BACKEND
 */
package cl.com.gesoft.workflow.model;

import cl.com.gesoft.workflow.model.hibernate.AddressDataUserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * The Class Client.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
@Entity
@Table(name = "gwf_client")
@TypeDefs({
        @TypeDef( name = "AddressDataUserType", typeClass = AddressDataUserType.class)
})
public class Client implements Serializable, Cloneable, WorflowModelIdAndClientId {

    private static final long serialVersionUID = 3526436713991501328L;

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

    @Column(name = "business_name", nullable = false)
    @NotEmpty(message = "Business Name is required")
    @Size(max = 120)
    private String businessName;

    @Column(name = "address")
    @Type(type = "AddressDataUserType")
    private Address Address;

    @Column(name = "active", nullable = false)
    @NotNull(message = "Active Id is required")
    private boolean active;


    @Column(name = "cliente_id" ,nullable = false)
    @NotNull(message = "Client ID is required")
    @JsonIgnore
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
     * Gets business name.
     *
     * @return the business name
     */
    public String getBusinessName() {
        return businessName;
    }

    /**
     * Sets business name.
     *
     * @param businessName the business name
     */
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }


    /**
     * Gets address.
     *
     * @return the address
     */
    public Address getAddress() {
        return Address;
    }

    /**
     * Sets address.
     *
     * @param address the address
     */
    public void setAddress(Address address) {
        Address = address;
    }

    /**
     * Is active boolean.
     *
     * @return the boolean
     */
    public boolean isActive() {
        return active;
    }

    /**
     * Sets active.
     *
     * @param active the active
     */
    public void setActive(boolean active) {
        this.active = active;
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
        if (!(o instanceof Client)) {
            return false;
        }
        return getId() != null && Objects.equals(getId(), ((Client) o).getId());
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
     * @return the Contact
     */
    @Override
    public Client clone() {
        Client clon = null;
        try {
            clon = (Client) super.clone();
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
        return "Client{" +
                "id=" + getId() +
                ", businessName='" + getBusinessName() + "'" +
                ", state='" + isActive() + "'" +
                ", clientId='" + getClientId() + "'" +
                "}";
    }
}
