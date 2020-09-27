/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * GESOFT-WORKFLOW-BACKEND
 */
package cl.com.gesoft.workflow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.vladmihalcea.hibernate.type.array.StringArrayType;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;

/**
 * The Class Activity.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
@Entity
@Table(name = "gwf_activity")
@TypeDefs({
        @TypeDef(
                name = "string-array",
                typeClass = StringArrayType.class
        )
})
public class Activity implements Serializable, Cloneable, WorflowModel{


    private static final long serialVersionUID = -7922501173917888549L;

    /**
     * Gets serial version uid.
     *
     * @return the serial version uid
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Id
    private String id;

    @Column(name = "name", nullable = false)
    @NotEmpty(message = "Name is required")
    @Size(max = 80)
    private String name;

    @Column(name = "description" ,nullable = false)
    @NotEmpty(message = "Description is required")
    @Size(max = 255)
    private String description;

    @Column(name = "weighing" ,nullable = false)
    @NotNull(message = "Weighing is required")
    @Max(100)
    private Integer weighing;

    @Column(name = "activity_type" ,nullable = false)
    @NotEmpty(message = "Activity Type is required")
    private String activityType;

    @Column(name = "authorizer")
    private Integer authorizer;

    @Column(name = "responsable")
    private Integer responsable;

    @Column(name = "evidence_required", nullable = false)
    @NotNull(message = "Evidence Required is required")
    private boolean evidenceRequired;

    @Column(name = "activity_order" ,nullable = false)
    @NotNull(message = "Activity order is required")
    private Integer activityOrder;

    @Column(name = "template_id")
    @JsonIgnore
    private Long templateId;

    @Column(name = "project_id")
    @JsonIgnore
    private Long projectId;

    @Column(name = "status")
    private String status;

    @Column(name = "activity_advance")
    private Integer activityAdvance;

    @Column(name = "comments")
    private boolean comment;

    @Column(name = "attached")
    private boolean attached;

    @Column(name = "project_execution_id")
    @JsonIgnore
    private Long projectExecutionId;

    @Column(name = "cliente_id" ,nullable = false)
    @JsonIgnore
    private Integer clientId;

    @Column(columnDefinition = "text[]", name = "predecessor_activities")
    @Type(type = "string-array")
    private String[] predecessorActivities;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Transient
    private Set<ActivityComment> activityComments;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Transient
    private Set<ActivityAttachment> activityAttachments;

    /**
     * Gets activity comments.
     *
     * @return the activity comments
     */
    public Set<ActivityComment> getActivityComments() {
        return activityComments;
    }

    /**
     * Sets activity comments.
     *
     * @param activityComments the activity comments
     */
    public void setActivityComments(Set<ActivityComment> activityComments) {
        this.activityComments = activityComments;
    }

    /**
     * Gets activity attachments.
     *
     * @return the activity attachments
     */
    public Set<ActivityAttachment> getActivityAttachments() {
        return activityAttachments;
    }

    /**
     * Sets activity attachments.
     *
     * @param activityAttachments the activity attachments
     */
    public void setActivityAttachments(Set<ActivityAttachment> activityAttachments) {
        this.activityAttachments = activityAttachments;
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(String id) {
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
     * Gets weighing.
     *
     * @return the weighing
     */
    public Integer getWeighing() {
        return weighing;
    }

    /**
     * Sets weighing.
     *
     * @param weighing the weighing
     */
    public void setWeighing(Integer weighing) {
        this.weighing = weighing;
    }

    /**
     * Gets activity type.
     *
     * @return the activity type
     */
    public String getActivityType() {
        return activityType;
    }

    /**
     * Sets activity type.
     *
     * @param activityType the activity type
     */
    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    /**
     * Gets authorizer.
     *
     * @return the authorizer
     */
    public Integer getAuthorizer() {
        return authorizer;
    }

    /**
     * Sets authorizer.
     *
     * @param authorizer the authorizer
     */
    public void setAuthorizer(Integer authorizer) {
        this.authorizer = authorizer;
    }

    /**
     * Gets responsable.
     *
     * @return the responsable
     */
    public Integer getResponsable() {
        return responsable;
    }

    /**
     * Sets responsable.
     *
     * @param responsable the responsable
     */
    public void setResponsable(Integer responsable) {
        this.responsable = responsable;
    }

    /**
     * Is evidence required boolean.
     *
     * @return the boolean
     */
    public boolean isEvidenceRequired() {
        return evidenceRequired;
    }

    /**
     * Sets evidence required.
     *
     * @param evidenceRequired the evidence required
     */
    public void setEvidenceRequired(boolean evidenceRequired) {
        this.evidenceRequired = evidenceRequired;
    }

    /**
     * Gets activity order.
     *
     * @return the activity order
     */
    public Integer getActivityOrder() {
        return activityOrder;
    }

    /**
     * Sets activity order.
     *
     * @param activityOrder the activity order
     */
    public void setActivityOrder(Integer activityOrder) {
        this.activityOrder = activityOrder;
    }

    /**
     * Gets template id.
     *
     * @return the template id
     */
    public Long getTemplateId() {
        return templateId;
    }

    /**
     * Sets template id.
     *
     * @param templateId the template id
     */
    public void setTemplateId(Long templateId) {
        this.templateId = templateId;
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
     * Gets status.
     *
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Gets activity advance.
     *
     * @return the activity advance
     */
    public Integer getActivityAdvance() {
        return activityAdvance;
    }

    /**
     * Sets activity advance.
     *
     * @param activityAdvance the activity advance
     */
    public void setActivityAdvance(Integer activityAdvance) {
        this.activityAdvance = activityAdvance;
    }

    /**
     * Is comment boolean.
     *
     * @return the boolean
     */
    public boolean isComment() {
        return comment;
    }

    /**
     * Sets comment.
     *
     * @param comment the comment
     */
    public void setComment(boolean comment) {
        this.comment = comment;
    }

    /**
     * Is attached boolean.
     *
     * @return the boolean
     */
    public boolean isAttached() {
        return attached;
    }

    /**
     * Sets attached.
     *
     * @param attached the attached
     */
    public void setAttached(boolean attached) {
        this.attached = attached;
    }

    /**
     * Gets project execution id.
     *
     * @return the project execution id
     */
    public Long getProjectExecutionId() {
        return projectExecutionId;
    }

    /**
     * Sets project execution id.
     *
     * @param projectExecutionId the project execution id
     */
    public void setProjectExecutionId(Long projectExecutionId) {
        this.projectExecutionId = projectExecutionId;
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
     * Get predecessor activities string [ ].
     *
     * @return the string [ ]
     */
    public String[] getPredecessorActivities() {
        return predecessorActivities;
    }

    /**
     * Sets predecessor activities.
     *
     * @param predecessorActivities the predecessor activities
     */
    public void setPredecessorActivities(String[] predecessorActivities) {
        this.predecessorActivities = predecessorActivities;
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
        if (!(o instanceof Activity)) {
            return false;
        }
        return getId() != null && Objects.equals(getId(), ((Activity) o).getId());
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
    public Activity clone() {
        Activity clon = null;
        try {
            clon = (Activity) super.clone();
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
                ", weighing='" + getWeighing() + "'" +
                ", activityType='" + getActivityType() + "'" +
                ", activityType='" + getActivityType() + "'" +
                ", authorizer='" + getAuthorizer()+ "'" +
                ", responsable='" + getResponsable()+ "'" +
                ", evidenceRequired='" + isEvidenceRequired() + "'" +
                ", activityOrder='" + getActivityOrder()+ "'" +
                ", templateId='" + getTemplateId()+ "'" +
                ", projectId='" + getProjectId()+ "'" +
                ", clientId='" + getClientId() + "'" +
                "}";
    }
}
