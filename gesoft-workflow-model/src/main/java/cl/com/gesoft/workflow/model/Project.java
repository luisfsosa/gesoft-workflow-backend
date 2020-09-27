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
import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

/**
 * The Class Project.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
@Entity
@Table(name = "gwf_project")
public class Project implements Serializable, Cloneable, WorflowModelIdAndClientId{


    private static final long serialVersionUID = 4459705281492452607L;

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

    @Column(name = "comments" ,nullable = false)
    @NotEmpty(message = "Comments is required")
    @Size(max = 500)
    private String comments;

    @Column(name = "project_since")
    @NotNull(message = "Project Since is required")
    private LocalDate projectSince;

    @Column(name = "project_until")
    private LocalDate projectUntil;

    @Column(name = "year_since", nullable = false)
    private Integer yearSince;

    @Column(name = "year_until", nullable = false)
    private Integer yearUntil;

    @Column(name = "period_since", nullable = false)
    @Size(max = 20)
    private String periodSince;

    @Column(name = "period_until", nullable = false)
    @Size(max = 20)
    private String periodUntil;

    @Column(name = "gwf_client_id")
    @NotNull(message = "Workflow Client ID is required")
    private Long gwfClientId;

    @Column(name = "project_type_id" ,nullable = false)
    @NotNull(message = "Project Type ID is required")
    private Integer projectTypeId;

    @Column(name = "template_id" ,nullable = false)
    @NotNull(message = "Template ID is required")
    private Integer templateId;

    @Column(name = "user_id")
    @NotNull(message = "User id is required")
    private Integer userId;

    @Column(name = "cutting_day" ,nullable = false)
    @NotNull(message = "CuttingDay is required")
    @Positive
    @Max(31)
    private Integer cuttingDay;

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
     * Gets comments.
     *
     * @return the comments
     */
    public String getComments() {
        return comments;
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
     * Sets comments.
     *
     * @param comments the comments
     */
    public void setComments(String comments) {
        this.comments = comments;
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
     * Gets year since.
     *
     * @return the year since
     */
    public Integer getYearSince() {
        return yearSince;
    }

    /**
     * Sets year since.
     *
     * @param yearSince the year since
     */
    public void setYearSince(Integer yearSince) {
        this.yearSince = yearSince;
    }

    /**
     * Gets year until.
     *
     * @return the year until
     */
    public Integer getYearUntil() {
        return yearUntil;
    }

    /**
     * Sets year until.
     *
     * @param yearUntil the year until
     */
    public void setYearUntil(Integer yearUntil) {
        this.yearUntil = yearUntil;
    }

    /**
     * Gets period since.
     *
     * @return the period since
     */
    public String getPeriodSince() {
        return periodSince;
    }

    /**
     * Sets period since.
     *
     * @param periodSince the period since
     */
    public void setPeriodSince(String periodSince) {
        this.periodSince = periodSince;
    }

    /**
     * Gets period until.
     *
     * @return the period until
     */
    public String getPeriodUntil() {
        return periodUntil;
    }

    /**
     * Sets period until.
     *
     * @param periodUntil the period until
     */
    public void setPeriodUntil(String periodUntil) {
        this.periodUntil = periodUntil;
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
        if (!(o instanceof Project)) {
            return false;
        }
        return id != null && Objects.equals(getId(), ((Project) o).getId());
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
    public Project clone() {
        Project clon = null;
        try {
            clon = (Project) super.clone();
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
                ", comments='" + getComments() + "'" +
                ", projectSince='" + getProjectSince() + "'" +
                ", projectUntil='" + getProjectUntil() + "'" +
                ", yearSince='" + getYearSince() + "'" +
                ", yearUntil='" + getYearUntil() + "'" +
                ", periodSince='" + getPeriodSince() + "'" +
                ", periodUntil='" + getPeriodUntil() + "'" +
                ", gwfClientId='" + getGwfClientId() + "'" +
                ", projectTypeId='" + getProjectTypeId() + "'" +
                ", templateId='" + getTemplateId() + "'" +
                ", userId='" + getUserId() + "'" +
                ", cuttingDay='" + getCuttingDay() + "'" +
                ", isOrder='" +isOrderTemplate() + "'" +
                ", clientId='" + getClientId() + "'" +
                "}";
    }
}
