/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * GESOFT-WORKFLOW-BACKEND
 */
package cl.com.gesoft.workflow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * The Class Activity Comment.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
@Entity
@Table(name = "gwf_activity_attachment")
public class ActivityAttachment implements Serializable, Cloneable, WorflowModel{


    private static final long serialVersionUID = 1184409776160771332L;

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

    @Column(name = "file_name", nullable = false)
    @NotEmpty(message = "File Name is required")
    private String fileName;

    @Column(name = "attachment", nullable = false)
    @NotEmpty(message = "Attached is required")
    private String attachment;

    @Column(name = "user_name" ,nullable = false)
    @NotEmpty(message = "User Name is required")
    @Size(max = 80)
    private String userName;

    @Column(name = "attachment_date")
    private LocalDateTime attachmentDate;

    @Column(name = "activity_id")
    @JsonIgnore
    private String activityId;

    @Column(name = "cliente_id" ,nullable = false)
    @JsonIgnore
    private Integer clientId;


    /**
     * Gets id.
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
     * Gets attachment.
     *
     * @return the attachment
     */
    public String getAttachment() {
        return attachment;
    }

    /**
     * Sets attachment.
     *
     * @param attachment the attachment
     */
    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }

    /**
     * Gets file name.
     *
     * @return the file name
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets file name.
     *
     * @param fileName the file name
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * Gets user name.
     *
     * @return the user name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets user name.
     *
     * @param userName the user name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets attachment date.
     *
     * @return the attachment date
     */
    public LocalDateTime getAttachmentDate() {
        return attachmentDate;
    }

    /**
     * Sets attachment date.
     *
     * @param attachmentDate the attachment date
     */
    public void setAttachmentDate(LocalDateTime attachmentDate) {
        this.attachmentDate = attachmentDate;
    }

    /**
     * Gets activity id.
     *
     * @return the activity id
     */
    public String getActivityId() {
        return activityId;
    }

    /**
     * Sets activity id.
     *
     * @param activityId the activity id
     */
    public void setActivityId(String activityId) {
        this.activityId = activityId;
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
        if (!(o instanceof ActivityAttachment)) {
            return false;
        }
        return getId() != null && Objects.equals(getId(), ((ActivityAttachment) o).getId());
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
    public ActivityAttachment clone() {
        ActivityAttachment clon = null;
        try {
            clon = (ActivityAttachment) super.clone();
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
                ", attached='" + getAttachment() + "'" +
                ", user_name='" + getUserName() + "'" +
                ", attachedDate='" + getAttachmentDate() + "'" +
                ", activityId='" + getActivityId() + "'" +
                ", clientId='" + getClientId() + "'" +
                "}";
    }
}
