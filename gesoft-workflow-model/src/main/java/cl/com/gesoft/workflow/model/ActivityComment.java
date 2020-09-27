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
 * The Class Activity Attached.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
@Entity
@Table(name = "gwf_activity_comment")
public class ActivityComment implements Serializable, Cloneable, WorflowModel{


    private static final long serialVersionUID = -5589933096742606658L;

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

    @Column(name = "comment", nullable = false)
    @NotEmpty(message = "Comment is required")
    @Size(max = 255)
    private String comment;

    @Column(name = "user_name" ,nullable = false)
    @NotEmpty(message = "User Name is required")
    @Size(max = 80)
    private String userName;

    @Column(name = "comment_date")
    private LocalDateTime commentDate;

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
     * Gets comment.
     *
     * @return the comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * Sets comment.
     *
     * @param comment the comment
     */
    public void setComment(String comment) {
        this.comment = comment;
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
     * Gets comment date.
     *
     * @return the comment date
     */
    public LocalDateTime getCommentDate() {
        return commentDate;
    }

    /**
     * Sets comment date.
     *
     * @param commentDate the comment date
     */
    public void setCommentDate(LocalDateTime commentDate) {
        this.commentDate = commentDate;
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
        if (!(o instanceof ActivityComment)) {
            return false;
        }
        return getId() != null && Objects.equals(getId(), ((ActivityComment) o).getId());
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
    public ActivityComment clone() {
        ActivityComment clon = null;
        try {
            clon = (ActivityComment) super.clone();
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
                ", comment='" + getComment() + "'" +
                ", user_name='" + getUserName() + "'" +
                ", commentDate='" + getCommentDate() + "'" +
                ", activityId='" + getActivityId() + "'" +
                ", clientId='" + getClientId() + "'" +
                "}";
    }
}
