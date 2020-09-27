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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

/**
 * The Class Project.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
@Entity
@Table(name = "gwf_project_execution")
public class ProjectExecution implements Serializable, Cloneable, WorflowModelIdAndClientId{


    private static final long serialVersionUID = 1626581902285877358L;

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

    @Column(name = "project_since")
    @NotNull(message = "Project Since is required")
    private LocalDate projectSince;

    @Column(name = "project_until")
    private LocalDate projectUntil;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "period", nullable = false)
    @Size(max = 50)
    private String period;

    @Column(name = "gwf_client_id")
    @NotNull(message = "Workflow Client ID is required")
    private Long gwfClientId;

    @Column(name = "project_type_id" ,nullable = false)
    @NotNull(message = "Project Type ID is required")
    private Integer projectTypeId;

    @Column(name = "project_id" ,nullable = false)
    @NotNull(message = "Project ID is required")
    private Long projectId;

    @Column(name = "template_id" ,nullable = false)
    @NotNull(message = "Template ID is required")
    private Integer templateId;

    @Column(name = "user_id")
    @NotNull(message = "User id is required")
    private Integer userId;

    @Column(name = "is_order", nullable = false)
    @NotNull(message = "Is Order is required")
    private boolean orderTemplate;

    @Column(name = "project_advance", nullable = false)
    @NotNull(message = "Project advance is required")
    private Integer projectAdvance;

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
     * Gets project since.
     *
     * @return the project since
     */
    public LocalDate getProjectSince() {
        return projectSince;
    }

    /**
     * Sets project since.
     *
     * @param projectSince the project since
     */
    public void setProjectSince(LocalDate projectSince) {
        this.projectSince = projectSince;
    }

    /**
     * Gets project until.
     *
     * @return the project until
     */
    public LocalDate getProjectUntil() {
        return projectUntil;
    }

    /**
     * Sets project until.
     *
     * @param projectUntil the project until
     */
    public void setProjectUntil(LocalDate projectUntil) {
        this.projectUntil = projectUntil;
    }

    /**
     * Gets year.
     *
     * @return the year
     */
    public Integer getYear() {
        return year;
    }

    /**
     * Sets year.
     *
     * @param year the year
     */
    public void setYear(Integer year) {
        this.year = year;
    }

    /**
     * Gets period.
     *
     * @return the period
     */
    public String getPeriod() {
        return period;
    }

    /**
     * Sets period.
     *
     * @param period the period
     */
    public void setPeriod(String period) {
        this.period = period;
    }

    /**
     * Gets gwf client id.
     *
     * @return the gwf client id
     */
    public Long getGwfClientId() {
        return gwfClientId;
    }

    /**
     * Sets gwf client id.
     *
     * @param gwfClientId the gwf client id
     */
    public void setGwfClientId(Long gwfClientId) {
        this.gwfClientId = gwfClientId;
    }

    /**
     * Gets project type id.
     *
     * @return the project type id
     */
    public Integer getProjectTypeId() {
        return projectTypeId;
    }

    /**
     * Sets project type id.
     *
     * @param projectTypeId the project type id
     */
    public void setProjectTypeId(Integer projectTypeId) {
        this.projectTypeId = projectTypeId;
    }


    /**
     * Gets project id.
     *
     * @return the project id
     */
    public Long getProjectId() {
        return projectId;
    }

    /**
     * Sets project id.
     *
     * @param projectId the project id
     */
    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }


    /**
     * Gets template id.
     *
     * @return the template id
     */
    public Integer getTemplateId() {
        return templateId;
    }

    /**
     * Sets template id.
     *
     * @param templateId the template id
     */
    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }

    /**
     * Gets user id.
     *
     * @return the user id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * Sets user id.
     *
     * @param userId the user id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
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
     * Gets project advance.
     *
     * @return the project advance
     */
    public Integer getProjectAdvance() {
        return projectAdvance;
    }

    /**
     * Sets project advance.
     *
     * @param projectAdvance the project advance
     */
    public void setProjectAdvance(Integer projectAdvance) {
        this.projectAdvance = projectAdvance;
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
        if (!(o instanceof ProjectExecution)) {
            return false;
        }
        return id != null && Objects.equals(getId(), ((ProjectExecution) o).getId());
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
    public ProjectExecution clone() {
        ProjectExecution clon = null;
        try {
            clon = (ProjectExecution) super.clone();
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
        return "Project{" +
                "id=" + getId() +
                ", name='" + getName() + "'" +
                ", projectSince='" + getProjectSince() + "'" +
                ", projectUntil='" + getProjectUntil() + "'" +
                ", Year='" + getYear() + "'" +
                ", Period='" + getPeriod()+ "'" +
                ", gwfClientId='" + getGwfClientId() + "'" +
                ", projectTypeId='" + getProjectTypeId() + "'" +
                ", projectId='" + getProjectId()+ "'" +
                ", userId='" + getUserId() + "'" +
                ", isOrder='" +isOrderTemplate() + "'" +
                ", projectAdvance='" +getProjectAdvance() + "'" +
                ", templateId='" + getTemplateId() + "'" +
                ", clientId='" + getClientId() + "'" +
                "}";
    }
}
