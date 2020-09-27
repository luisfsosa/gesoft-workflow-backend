/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * GESOFT-WORKFLOW-BACKEND
 */
package cl.com.gesoft.workflow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * The Class Template.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
@Entity
@Table(name = "gwf_template")
public class Template implements Serializable, Cloneable, WorflowModelIdAndClientId{


    private static final long serialVersionUID = -3850355248905943603L;

    /**
     * Gets serial version uid.
     *
     * @return the serial version uid
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotEmpty(message = "Name is required")
    @Size(max = 80)
    private String name;

    @Column(name = "description" ,nullable = false)
    @NotEmpty(message = "Description is required")
    @Size(max = 255)
    private String description;

    @Column(name = "cutting_day" ,nullable = false)
    @NotNull(message = "CuttingDay is required")
    @Positive
    @Max(31)
    private Integer cuttingDay;

    @Column(name = "project_type_id" ,nullable = false)
    @NotNull(message = "Project Type ID is required")
    private Long projectTypeId;

    @Column(name = "is_order", nullable = false)
    @NotNull(message = "Is Order is required")
    private boolean orderTemplate;

    @Column(name = "cliente_id" ,nullable = false)
    @NotNull(message = "Client ID is required")
    @JsonIgnore
    private Integer clientId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Transient
    private Set<Activity> activities;


    /**
     * Gets the id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Name.
     *
     * @param name the first name
     * @return the group
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets cutting day.
     *
     * @return the cutting day
     */
    public Integer getCuttingDay() {
        return cuttingDay;
    }

    /**
     * Sets cutting day.
     *
     * @param cuttingDay the cutting day
     */
    public void setCuttingDay(Integer cuttingDay) {
        this.cuttingDay = cuttingDay;
    }

    /**
     * Gets project type id.
     *
     * @return the project type id
     */
    public Long getProjectTypeId() {
        return projectTypeId;
    }

    /**
     * Sets project type id.
     *
     * @param projectTypeId the project type id
     */
    public void setProjectTypeId(Long projectTypeId) {
        this.projectTypeId = projectTypeId;
    }

    /**
     * Is order template boolean.
     *
     * @return the boolean
     */
    public boolean isOrderTemplate() {
        return orderTemplate;
    }

    /**
     * Sets order template.
     *
     * @param orderTemplate the order template
     */
    public void setOrderTemplate(boolean orderTemplate) {
        this.orderTemplate = orderTemplate;
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
     * Gets activities.
     *
     * @return the activities
     */
    public Set<Activity> getActivities() {
        return activities;
    }

    /**
     * Sets activities.
     *
     * @param activities the activities
     */
    public void setActivities(Set<Activity> activities) {
        this.activities = activities;
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
        if (!(o instanceof Template)) {
            return false;
        }
        return id != null && Objects.equals(getId(), ((Template) o).getId());
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
     * @return the Group
     */
    @Override
    public Template clone() {
        Template clon = null;
        try {
            clon = (Template) super.clone();
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
        return "Template{" +
                "id=" + getId() +
                ", name='" + getName() + "'" +
                ", description='" + getDescription() + "'" +
                ", cuttingDay='" + getCuttingDay() + "'" +
                ", projectTypeId='" + getProjectTypeId() + "'" +
                ", isOrder='" +isOrderTemplate() + "'" +
                ", clientId='" + getClientId() + "'" +
                "}";
    }
}
