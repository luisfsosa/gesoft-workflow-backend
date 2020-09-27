/*
 * Copyright (c) 2020, Luis Felipe Sosa Alvarez. All rights reserved.
 * Use is subject to license terms.
 *
 * GESOFT-WORKFLOW-BACKEND
 */
package cl.com.gesoft.workflow.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Immutable;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;

/**
 * The Class Project.
 *
 * @autor Luis Felipe Sosa Alvarez luisfsosa@gmail.com
 */
@Entity
@Immutable
@Table(name = "qwf_vw_workflow")
public class WorkflowView implements Serializable, Cloneable, WorflowModelIdAndClientId{


    private static final long serialVersionUID = -6439738517148846764L;

    /**
     * Gets serial version uid.
     *
     * @return the serial version uid
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Id
    private Long id;

    @Column(name = "project_type_id" ,nullable = false)
    private Integer projectTypeId;

    @Column(name = "project_type_name" ,nullable = false)
    private String projectTypeName;

    @Column(name = "project_since")
    private LocalDate projectSince;

    @Column(name = "project_until")
    private LocalDate projectUntil;

    @Column(name = "year", nullable = false)
    private Integer year;

    @Column(name = "period", nullable = false)
    private String period;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "gwf_client_id")
    private Long gwfFlowClientId;

    @Column(name = "gwf_client_name", nullable = false)
    private String gwfFlowclientName;

    @Column(name = "project_advance", nullable = false)
    private Integer projectAdvance;

    @Column(name = "project_id" ,nullable = false)
    private Long projectId;

    @Column(name = "template_id" ,nullable = false)
    private Integer templateId;

    @Column(name = "order_execution", nullable = false)
    private Boolean orderExecution;

    @Column(name = "comments_number", nullable = false)
    private Integer commentsNumber;

    @Column(name = "attached_number", nullable = false)
    private Integer attachedNumber;

    @Column(name = "pending_number", nullable = false)
    private Integer pendingNumber;

    @Column(name = "rejected_number", nullable = false)
    private Integer rejectedNumber;

    @Column(name = "activity1", nullable = false)
    private String activity1;

    @Column(name = "status1", nullable = false)
    private String status1;

    @Column(name = "comment1")
    private Boolean comment1;

    @Column(name = "attached1")
    private Boolean attached1;

    @Column(name = "activity2", nullable = false)
    private String activity2;

    @Column(name = "status2", nullable = false)
    private String status2;

    @Column(name = "comment2")
    private Boolean comment2;

    @Column(name = "attached2")
    private Boolean attached2;

    @Column(name = "activity3", nullable = false)
    private String activity3;

    @Column(name = "status3", nullable = false)
    private String status3;

    @Column(name = "comment3")
    private Boolean comment3;

    @Column(name = "attached3")
    private Boolean attached3;

    @Column(name = "activity4", nullable = false)
    private String activity4;

    @Column(name = "status4", nullable = false)
    private String status4;

    @Column(name = "comment4")
    private Boolean comment4;

    @Column(name = "attached4")
    private Boolean attached4;

    @Column(name = "activity5", nullable = false)
    private String activity5;

    @Column(name = "status5", nullable = false)
    private String status5;

    @Column(name = "comment5")
    private Boolean comment5;

    @Column(name = "attached5")
    private Boolean attached5;

    @Column(name = "activity6", nullable = false)
    private String activity6;

    @Column(name = "status6", nullable = false)
    private String status6;

    @Column(name = "comment6")
    private Boolean comment6;

    @Column(name = "attached6")
    private Boolean attached6;

    @Column(name = "activity7", nullable = false)
    private String activity7;

    @Column(name = "status7", nullable = false)
    private String status7;

    @Column(name = "comment7")
    private Boolean comment7;

    @Column(name = "attached7")
    private Boolean attached7;

    @Column(name = "activity8", nullable = false)
    private String activity8;

    @Column(name = "status8", nullable = false)
    private String status8;

    @Column(name = "comment8")
    private Boolean comment8;

    @Column(name = "attached8")
    private Boolean attached8;

    @Column(name = "activity9", nullable = false)
    private String activity9;

    @Column(name = "status9", nullable = false)
    private String status9;

    @Column(name = "comment9")
    private Boolean comment9;

    @Column(name = "attached9")
    private Boolean attached9;

    @Column(name = "activity10", nullable = false)
    private String activity10;

    @Column(name = "status10", nullable = false)
    private String status10;

    @Column(name = "comment10")
    private Boolean comment10;

    @Column(name = "attached10")
    private Boolean attached10;

    @Column(name = "activity11", nullable = false)
    private String activity11;

    @Column(name = "status11", nullable = false)
    private String status11;

    @Column(name = "comment11")
    private Boolean comment11;

    @Column(name = "attached11")
    private Boolean attached11;

    @Column(name = "activity12", nullable = false)
    private String activity12;

    @Column(name = "status12", nullable = false)
    private String status12;

    @Column(name = "comment12")
    private Boolean comment12;

    @Column(name = "attached12")
    private Boolean attached12;

    @Column(name = "activity13", nullable = false)
    private String activity13;

    @Column(name = "status13", nullable = false)
    private String status13;

    @Column(name = "comment13")
    private Boolean comment13;

    @Column(name = "attached13")
    private Boolean attached13;

    @Column(name = "activity14", nullable = false)
    private String activity14;

    @Column(name = "status14", nullable = false)
    private String status14;

    @Column(name = "comment14")
    private Boolean comment14;

    @Column(name = "attached14")
    private Boolean attached14;

    @Column(name = "activity15", nullable = false)
    private String activity15;

    @Column(name = "status15", nullable = false)
    private String status15;

    @Column(name = "comment15")
    private Boolean comment15;

    @Column(name = "attached15")
    private Boolean attached15;

    @Column(name = "cliente_id" ,nullable = false)
    @JsonIgnore
    private Integer clientId;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @Transient
    private Set<Activity> activities;


    /** The id. */
    @Override
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
     * Gets project type name.
     *
     * @return the project type name
     */
    public String getProjectTypeName() {
        return projectTypeName;
    }

    /**
     * Sets project type name.
     *
     * @param projectTypeName the project type name
     */
    public void setProjectTypeName(String projectTypeName) {
        this.projectTypeName = projectTypeName;
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
     * Gets gwf flow client id.
     *
     * @return the gwf flow client id
     */
    public Long getGwfFlowClientId() {
        return gwfFlowClientId;
    }

    /**
     * Sets gwf flow client id.
     *
     * @param gwfFlowClientId the gwf flow client id
     */
    public void setGwfFlowClientId(Long gwfFlowClientId) {
        this.gwfFlowClientId = gwfFlowClientId;
    }

    /**
     * Gets gwf flowclient name.
     *
     * @return the gwf flowclient name
     */
    public String getGwfFlowclientName() {
        return gwfFlowclientName;
    }

    /**
     * Sets gwf flowclient name.
     *
     * @param gwfFlowclientName the gwf flowclient name
     */
    public void setGwfFlowclientName(String gwfFlowclientName) {
        this.gwfFlowclientName = gwfFlowclientName;
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

    @Override
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
     * Is order execution Boolean.
     *
     * @return the Boolean
     */
    public Boolean isOrderExecution() {
        return orderExecution;
    }

    /**
     * Sets order execution.
     *
     * @param orderExecution the order execution
     */
    public void setOrderExecution(Boolean orderExecution) {
        this.orderExecution = orderExecution;
    }

    /**
     * Gets comments number.
     *
     * @return the comments number
     */
    public Integer getCommentsNumber() {
        return commentsNumber;
    }

    /**
     * Sets comments number.
     *
     * @param commentsNumber the comments number
     */
    public void setCommentsNumber(Integer commentsNumber) {
        this.commentsNumber = commentsNumber;
    }

    /**
     * Gets attached number.
     *
     * @return the attached number
     */
    public Integer getAttachedNumber() {
        return attachedNumber;
    }

    /**
     * Sets attached number.
     *
     * @param attachedNumber the attached number
     */
    public void setAttachedNumber(Integer attachedNumber) {
        this.attachedNumber = attachedNumber;
    }

    /**
     * Gets pending number.
     *
     * @return the pending number
     */
    public Integer getPendingNumber() {
        return pendingNumber;
    }

    /**
     * Sets pending number.
     *
     * @param pendingNumber the pending number
     */
    public void setPendingNumber(Integer pendingNumber) {
        this.pendingNumber = pendingNumber;
    }

    /**
     * Gets rejected number.
     *
     * @return the rejected number
     */
    public Integer getRejectedNumber() {
        return rejectedNumber;
    }

    /**
     * Sets rejected number.
     *
     * @param rejectedNumber the rejected number
     */
    public void setRejectedNumber(Integer rejectedNumber) {
        this.rejectedNumber = rejectedNumber;
    }

    /**
     * Gets activity 1.
     *
     * @return the activity 1
     */
    public String getActivity1() {
        return activity1;
    }

    /**
     * Sets activity 1.
     *
     * @param activity1 the activity 1
     */
    public void setActivity1(String activity1) {
        this.activity1 = activity1;
    }

    /**
     * Gets activity 2.
     *
     * @return the activity 2
     */
    public String getActivity2() {
        return activity2;
    }

    /**
     * Sets activity 2.
     *
     * @param activity2 the activity 2
     */
    public void setActivity2(String activity2) {
        this.activity2 = activity2;
    }

    /**
     * Gets activity 3.
     *
     * @return the activity 3
     */
    public String getActivity3() {
        return activity3;
    }

    /**
     * Sets activity 3.
     *
     * @param activity3 the activity 3
     */
    public void setActivity3(String activity3) {
        this.activity3 = activity3;
    }

    /**
     * Gets activity 4.
     *
     * @return the activity 4
     */
    public String getActivity4() {
        return activity4;
    }

    /**
     * Sets activity 4.
     *
     * @param activity4 the activity 4
     */
    public void setActivity4(String activity4) {
        this.activity4 = activity4;
    }

    /**
     * Gets activity 5.
     *
     * @return the activity 5
     */
    public String getActivity5() {
        return activity5;
    }

    /**
     * Sets activity 5.
     *
     * @param activity5 the activity 5
     */
    public void setActivity5(String activity5) {
        this.activity5 = activity5;
    }

    /**
     * Gets activity 6.
     *
     * @return the activity 6
     */
    public String getActivity6() {
        return activity6;
    }

    /**
     * Sets activity 6.
     *
     * @param activity6 the activity 6
     */
    public void setActivity6(String activity6) {
        this.activity6 = activity6;
    }

    /**
     * Gets activity 7.
     *
     * @return the activity 7
     */
    public String getActivity7() {
        return activity7;
    }

    /**
     * Sets activity 7.
     *
     * @param activity7 the activity 7
     */
    public void setActivity7(String activity7) {
        this.activity7 = activity7;
    }

    /**
     * Gets activity 8.
     *
     * @return the activity 8
     */
    public String getActivity8() {
        return activity8;
    }

    /**
     * Sets activity 8.
     *
     * @param activity8 the activity 8
     */
    public void setActivity8(String activity8) {
        this.activity8 = activity8;
    }

    /**
     * Gets activity 9.
     *
     * @return the activity 9
     */
    public String getActivity9() {
        return activity9;
    }

    /**
     * Sets activity 9.
     *
     * @param activity9 the activity 9
     */
    public void setActivity9(String activity9) {
        this.activity9 = activity9;
    }

    /**
     * Gets activity 10.
     *
     * @return the activity 10
     */
    public String getActivity10() {
        return activity10;
    }

    /**
     * Sets activity 10.
     *
     * @param activity10 the activity 10
     */
    public void setActivity10(String activity10) {
        this.activity10 = activity10;
    }

    /**
     * Gets activity 11.
     *
     * @return the activity 11
     */
    public String getActivity11() {
        return activity11;
    }

    /**
     * Sets activity 11.
     *
     * @param activity11 the activity 11
     */
    public void setActivity11(String activity11) {
        this.activity11 = activity11;
    }

    /**
     * Gets activity 12.
     *
     * @return the activity 12
     */
    public String getActivity12() {
        return activity12;
    }

    /**
     * Sets activity 12.
     *
     * @param activity12 the activity 12
     */
    public void setActivity12(String activity12) {
        this.activity12 = activity12;
    }

    /**
     * Gets activity 13.
     *
     * @return the activity 13
     */
    public String getActivity13() {
        return activity13;
    }

    /**
     * Sets activity 13.
     *
     * @param activity13 the activity 13
     */
    public void setActivity13(String activity13) {
        this.activity13 = activity13;
    }

    /**
     * Gets activity 14.
     *
     * @return the activity 14
     */
    public String getActivity14() {
        return activity14;
    }

    /**
     * Sets activity 14.
     *
     * @param activity14 the activity 14
     */
    public void setActivity14(String activity14) {
        this.activity14 = activity14;
    }

    /**
     * Gets activity 15.
     *
     * @return the activity 15
     */
    public String getActivity15() {
        return activity15;
    }

    /**
     * Sets activity 15.
     *
     * @param activity15 the activity 15
     */
    public void setActivity15(String activity15) {
        this.activity15 = activity15;
    }

    /**
     * Gets status 1.
     *
     * @return the status 1
     */
    public String getStatus1() {
        return status1;
    }

    /**
     * Sets status 1.
     *
     * @param status1 the status 1
     */
    public void setStatus1(String status1) {
        this.status1 = status1;
    }

    /**
     * Gets status 2.
     *
     * @return the status 2
     */
    public String getStatus2() {
        return status2;
    }

    /**
     * Sets status 2.
     *
     * @param status2 the status 2
     */
    public void setStatus2(String status2) {
        this.status2 = status2;
    }

    /**
     * Gets status 3.
     *
     * @return the status 3
     */
    public String getStatus3() {
        return status3;
    }

    /**
     * Sets status 3.
     *
     * @param status3 the status 3
     */
    public void setStatus3(String status3) {
        this.status3 = status3;
    }

    /**
     * Gets status 4.
     *
     * @return the status 4
     */
    public String getStatus4() {
        return status4;
    }

    /**
     * Sets status 4.
     *
     * @param status4 the status 4
     */
    public void setStatus4(String status4) {
        this.status4 = status4;
    }

    /**
     * Gets status 5.
     *
     * @return the status 5
     */
    public String getStatus5() {
        return status5;
    }

    /**
     * Sets status 5.
     *
     * @param status5 the status 5
     */
    public void setStatus5(String status5) {
        this.status5 = status5;
    }

    /**
     * Gets status 6.
     *
     * @return the status 6
     */
    public String getStatus6() {
        return status6;
    }

    /**
     * Sets status 6.
     *
     * @param status6 the status 6
     */
    public void setStatus6(String status6) {
        this.status6 = status6;
    }

    /**
     * Gets status 7.
     *
     * @return the status 7
     */
    public String getStatus7() {
        return status7;
    }

    /**
     * Sets status 7.
     *
     * @param status7 the status 7
     */
    public void setStatus7(String status7) {
        this.status7 = status7;
    }

    /**
     * Gets status 8.
     *
     * @return the status 8
     */
    public String getStatus8() {
        return status8;
    }

    /**
     * Sets status 8.
     *
     * @param status8 the status 8
     */
    public void setStatus8(String status8) {
        this.status8 = status8;
    }

    /**
     * Gets status 9.
     *
     * @return the status 9
     */
    public String getStatus9() {
        return status9;
    }

    /**
     * Sets status 9.
     *
     * @param status9 the status 9
     */
    public void setStatus9(String status9) {
        this.status9 = status9;
    }

    /**
     * Gets status 10.
     *
     * @return the status 10
     */
    public String getStatus10() {
        return status10;
    }

    /**
     * Sets status 10.
     *
     * @param status10 the status 10
     */
    public void setStatus10(String status10) {
        this.status10 = status10;
    }

    /**
     * Gets status 11.
     *
     * @return the status 11
     */
    public String getStatus11() {
        return status11;
    }

    /**
     * Sets status 11.
     *
     * @param status11 the status 11
     */
    public void setStatus11(String status11) {
        this.status11 = status11;
    }

    /**
     * Gets status 12.
     *
     * @return the status 12
     */
    public String getStatus12() {
        return status12;
    }

    /**
     * Sets status 12.
     *
     * @param status12 the status 12
     */
    public void setStatus12(String status12) {
        this.status12 = status12;
    }

    /**
     * Gets status 13.
     *
     * @return the status 13
     */
    public String getStatus13() {
        return status13;
    }

    /**
     * Sets status 13.
     *
     * @param status13 the status 13
     */
    public void setStatus13(String status13) {
        this.status13 = status13;
    }

    /**
     * Gets status 14.
     *
     * @return the status 14
     */
    public String getStatus14() {
        return status14;
    }

    /**
     * Sets status 14.
     *
     * @param status14 the status 14
     */
    public void setStatus14(String status14) {
        this.status14 = status14;
    }

    /**
     * Gets status 15.
     *
     * @return the status 15
     */
    public String getStatus15() {
        return status15;
    }

    /**
     * Sets status 15.
     *
     * @param status15 the status 15
     */
    public void setStatus15(String status15) {
        this.status15 = status15;
    }

    /**
     * Is comment 1 Boolean.
     *
     * @return the Boolean
     */
    public Boolean isComment1() {
        return comment1;
    }

    /**
     * Sets comment 1.
     *
     * @param comment1 the comment 1
     */
    public void setComment1(Boolean comment1) {
        this.comment1 = comment1;
    }

    /**
     * Is attached 1 Boolean.
     *
     * @return the Boolean
     */
    public Boolean isAttached1() {
        return attached1;
    }

    /**
     * Sets attached 1.
     *
     * @param attached1 the attached 1
     */
    public void setAttached1(Boolean attached1) {
        this.attached1 = attached1;
    }

    /**
     * Is comment 2 Boolean.
     *
     * @return the Boolean
     */
    public Boolean isComment2() {
        return comment2;
    }

    /**
     * Sets comment 2.
     *
     * @param comment2 the comment 2
     */
    public void setComment2(Boolean comment2) {
        this.comment2 = comment2;
    }

    /**
     * Is attached 2 Boolean.
     *
     * @return the Boolean
     */
    public Boolean isAttached2() {
        return attached2;
    }

    /**
     * Sets attached 2.
     *
     * @param attached2 the attached 2
     */
    public void setAttached2(Boolean attached2) {
        this.attached2 = attached2;
    }

    /**
     * Is comment 3 Boolean.
     *
     * @return the Boolean
     */
    public Boolean isComment3() {
        return comment3;
    }

    /**
     * Sets comment 3.
     *
     * @param comment3 the comment 3
     */
    public void setComment3(Boolean comment3) {
        this.comment3 = comment3;
    }

    /**
     * Is attached 3 Boolean.
     *
     * @return the Boolean
     */
    public Boolean isAttached3() {
        return attached3;
    }

    /**
     * Sets attached 3.
     *
     * @param attached3 the attached 3
     */
    public void setAttached3(Boolean attached3) {
        this.attached3 = attached3;
    }

    /**
     * Is comment 4 Boolean.
     *
     * @return the Boolean
     */
    public Boolean isComment4() {
        return comment4;
    }

    /**
     * Sets comment 4.
     *
     * @param comment4 the comment 4
     */
    public void setComment4(Boolean comment4) {
        this.comment4 = comment4;
    }

    /**
     * Is attached 4 Boolean.
     *
     * @return the Boolean
     */
    public Boolean isAttached4() {
        return attached4;
    }

    /**
     * Sets attached 4.
     *
     * @param attached4 the attached 4
     */
    public void setAttached4(Boolean attached4) {
        this.attached4 = attached4;
    }

    /**
     * Is comment 5 Boolean.
     *
     * @return the Boolean
     */
    public Boolean isComment5() {
        return comment5;
    }

    /**
     * Sets comment 5.
     *
     * @param comment5 the comment 5
     */
    public void setComment5(Boolean comment5) {
        this.comment5 = comment5;
    }

    /**
     * Is attached 5 Boolean.
     *
     * @return the Boolean
     */
    public Boolean isAttached5() {
        return attached5;
    }

    /**
     * Sets attached 5.
     *
     * @param attached5 the attached 5
     */
    public void setAttached5(Boolean attached5) {
        this.attached5 = attached5;
    }

    /**
     * Is comment 6 Boolean.
     *
     * @return the Boolean
     */
    public Boolean isComment6() {
        return comment6;
    }

    /**
     * Sets comment 6.
     *
     * @param comment6 the comment 6
     */
    public void setComment6(Boolean comment6) {
        this.comment6 = comment6;
    }

    /**
     * Is attached 6 Boolean.
     *
     * @return the Boolean
     */
    public Boolean isAttached6() {
        return attached6;
    }

    /**
     * Sets attached 6.
     *
     * @param attached6 the attached 6
     */
    public void setAttached6(Boolean attached6) {
        this.attached6 = attached6;
    }

    /**
     * Is comment 7 Boolean.
     *
     * @return the Boolean
     */
    public Boolean isComment7() {
        return comment7;
    }

    /**
     * Sets comment 7.
     *
     * @param comment7 the comment 7
     */
    public void setComment7(Boolean comment7) {
        this.comment7 = comment7;
    }

    /**
     * Is attached 7 Boolean.
     *
     * @return the Boolean
     */
    public Boolean isAttached7() {
        return attached7;
    }

    /**
     * Sets attached 7.
     *
     * @param attached7 the attached 7
     */
    public void setAttached7(Boolean attached7) {
        this.attached7 = attached7;
    }

    /**
     * Is comment 8 Boolean.
     *
     * @return the Boolean
     */
    public Boolean isComment8() {
        return comment8;
    }

    /**
     * Sets comment 8.
     *
     * @param comment8 the comment 8
     */
    public void setComment8(Boolean comment8) {
        this.comment8 = comment8;
    }

    /**
     * Is attached 8 Boolean.
     *
     * @return the Boolean
     */
    public Boolean isAttached8() {
        return attached8;
    }

    /**
     * Sets attached 8.
     *
     * @param attached8 the attached 8
     */
    public void setAttached8(Boolean attached8) {
        this.attached8 = attached8;
    }

    /**
     * Is comment 9 Boolean.
     *
     * @return the Boolean
     */
    public Boolean isComment9() {
        return comment9;
    }

    /**
     * Sets comment 9.
     *
     * @param comment9 the comment 9
     */
    public void setComment9(Boolean comment9) {
        this.comment9 = comment9;
    }

    /**
     * Is attached 9 Boolean.
     *
     * @return the Boolean
     */
    public Boolean isAttached9() {
        return attached9;
    }

    /**
     * Sets attached 9.
     *
     * @param attached9 the attached 9
     */
    public void setAttached9(Boolean attached9) {
        this.attached9 = attached9;
    }

    /**
     * Is comment 10 Boolean.
     *
     * @return the Boolean
     */
    public Boolean isComment10() {
        return comment10;
    }

    /**
     * Sets comment 10.
     *
     * @param comment10 the comment 10
     */
    public void setComment10(Boolean comment10) {
        this.comment10 = comment10;
    }

    /**
     * Is attached 10 Boolean.
     *
     * @return the Boolean
     */
    public Boolean isAttached10() {
        return attached10;
    }

    /**
     * Sets attached 10.
     *
     * @param attached10 the attached 10
     */
    public void setAttached10(Boolean attached10) {
        this.attached10 = attached10;
    }

    /**
     * Is comment 11 Boolean.
     *
     * @return the Boolean
     */
    public Boolean isComment11() {
        return comment11;
    }

    /**
     * Sets comment 11.
     *
     * @param comment11 the comment 11
     */
    public void setComment11(Boolean comment11) {
        this.comment11 = comment11;
    }

    /**
     * Is attached 11 Boolean.
     *
     * @return the Boolean
     */
    public Boolean isAttached11() {
        return attached11;
    }

    /**
     * Sets attached 11.
     *
     * @param attached11 the attached 11
     */
    public void setAttached11(Boolean attached11) {
        this.attached11 = attached11;
    }

    /**
     * Is comment 12 Boolean.
     *
     * @return the Boolean
     */
    public Boolean isComment12() {
        return comment12;
    }

    /**
     * Sets comment 12.
     *
     * @param comment12 the comment 12
     */
    public void setComment12(Boolean comment12) {
        this.comment12 = comment12;
    }

    /**
     * Is attached 12 Boolean.
     *
     * @return the Boolean
     */
    public Boolean isAttached12() {
        return attached12;
    }

    /**
     * Sets attached 12.
     *
     * @param attached12 the attached 12
     */
    public void setAttached12(Boolean attached12) {
        this.attached12 = attached12;
    }

    /**
     * Is comment 13 Boolean.
     *
     * @return the Boolean
     */
    public Boolean isComment13() {
        return comment13;
    }

    /**
     * Sets comment 13.
     *
     * @param comment13 the comment 13
     */
    public void setComment13(Boolean comment13) {
        this.comment13 = comment13;
    }

    /**
     * Is attached 13 Boolean.
     *
     * @return the Boolean
     */
    public Boolean isAttached13() {
        return attached13;
    }

    /**
     * Sets attached 13.
     *
     * @param attached13 the attached 13
     */
    public void setAttached13(Boolean attached13) {
        this.attached13 = attached13;
    }

    /**
     * Is comment 14 Boolean.
     *
     * @return the Boolean
     */
    public Boolean isComment14() {
        return comment14;
    }

    /**
     * Sets comment 14.
     *
     * @param comment14 the comment 14
     */
    public void setComment14(Boolean comment14) {
        this.comment14 = comment14;
    }

    /**
     * Is attached 14 Boolean.
     *
     * @return the Boolean
     */
    public Boolean isAttached14() {
        return attached14;
    }

    /**
     * Sets attached 14.
     *
     * @param attached14 the attached 14
     */
    public void setAttached14(Boolean attached14) {
        this.attached14 = attached14;
    }

    /**
     * Is comment 15 Boolean.
     *
     * @return the Boolean
     */
    public Boolean isComment15() {
        return comment15;
    }

    /**
     * Sets comment 15.
     *
     * @param comment15 the comment 15
     */
    public void setComment15(Boolean comment15) {
        this.comment15 = comment15;
    }

    /**
     * Is attached 15 Boolean.
     *
     * @return the Boolean
     */
    public Boolean isAttached15() {
        return attached15;
    }

    /**
     * Sets attached 15.
     *
     * @param attached15 the attached 15
     */
    public void setAttached15(Boolean attached15) {
        this.attached15 = attached15;
    }
}
