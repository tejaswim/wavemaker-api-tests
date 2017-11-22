/**
 * Copyright (c) 2013 - 2014 WaveMaker Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker Inc.
 */
package com.wavemaker.api.rest.models.project;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author <a href="mailto:dilip.gundu@wavemaker.com">Dilip Kumar</a>
 * @since 15/10/14
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProjectInfo {
    private String projectId;
    private String name;
    private String description;
    private String icon;
    private String projectType;
    private String enterpriseId;
    private String createdByUserId;
    private String lastModifiedByUserId;
    private long createdAt;
    private long lastModifiedAt;
    private ProjectStatus status;
    private SubscriptionState subscriptionState;
    private String subscriptionPlanCode;
    private long expiresOn;
    private int expiresInDays;
    private int renewalGraceLeftInDays;
    private boolean accessibility;
    private PlatformType platformType;
    private Plan plan;
    private String projectShellId;
    private boolean isSampleApp = false;
    private Map<String, Object> vcsCustomProperties;
    private User subscribedUser;

    public ProjectInfo() {
    }

    public boolean isSampleApp() {
        return isSampleApp;
    }

    public void setSampleApp(boolean isSampleApp) {
        this.isSampleApp = isSampleApp;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(final String icon) {
        this.icon = icon;
    }

    public String getId() {
        return getProjectId();
    }

    public void setId(String id) {
        setProjectId(id);
    }

    public String getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(String enterpriseId) {
        this.enterpriseId = enterpriseId;
    }


    public String getCreatedByUserId() {
        return createdByUserId;
    }

    public void setCreatedByUserId(String createdByUserId) {
        this.createdByUserId = createdByUserId;
    }

    public String getLastModifiedByUserId() {
        return lastModifiedByUserId;
    }

    public void setLastModifiedByUserId(String lastModifiedByUserId) {
        this.lastModifiedByUserId = lastModifiedByUserId;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public long getLastModifiedAt() {
        return lastModifiedAt;
    }

    public void setLastModifiedAt(long lastModifiedAt) {
        this.lastModifiedAt = lastModifiedAt;
    }

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }

    public SubscriptionState getSubscriptionState() {
        return subscriptionState;
    }

    public void setSubscriptionState(SubscriptionState subscriptionState) {
        this.subscriptionState = subscriptionState;
    }

    public String getSubscriptionPlanCode() {
        return subscriptionPlanCode;
    }

    public void setSubscriptionPlanCode(String subscriptionPlanCode) {
        this.subscriptionPlanCode = subscriptionPlanCode;
    }

    public long getExpiresOn() {
        return expiresOn;
    }

    public void setExpiresOn(long expiresOn) {
        this.expiresOn = expiresOn;
    }

    public PlatformType getPlatformType() {
        return platformType;
    }

    public void setPlatformType(PlatformType platformType) {
        this.platformType = platformType;
    }

    public int getExpiresInDays() {
       return (int) ((getExpiresOn() - System.currentTimeMillis()) / TimeUnit.DAYS.toMillis(1));
    }

    public boolean isAccessibility() {
        return accessibility;
    }

    public void setAccessibility(boolean accessibility) {
        this.accessibility = accessibility;
    }

    public int getRenewalGraceLeftInDays() {
        return renewalGraceLeftInDays;
    }

    public void setRenewalGraceLeftInDays(int renewalGraceLeftInDays) {
        this.renewalGraceLeftInDays = renewalGraceLeftInDays;
    }

    public Plan getPlan() {
        return plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public String getProjectShellId() {
        return projectShellId;
    }

    public void setProjectShellId(String projectShellId) {
        this.projectShellId = projectShellId;
    }

    public Map<String, Object> getVcsCustomProperties() {
        return vcsCustomProperties;
    }

    public void setVcsCustomProperties(Object vcsCustomProperties) {
        this.vcsCustomProperties = (Map<String, Object>)vcsCustomProperties;
    }

    public User getSubscribedUser() {
        return subscribedUser;
    }

    public void setSubscribedUser(User subscribedUser) {
        this.subscribedUser = subscribedUser;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }

    @Override
    public String toString() {
        return "ProjectInfo{" +
                "projectId='" + projectId + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", icon='" + icon + '\'' +
                ", projectType='"+ projectType + '\''+
                ", enterpriseId='" + enterpriseId + '\'' +
                ", createdByUserId='" + createdByUserId + '\'' +
                ", lastModifiedByUserId='" + lastModifiedByUserId + '\'' +
                ", createdAt=" + createdAt +
                ", lastModifiedAt=" + lastModifiedAt +
                ", status=" + status +
                '}';
    }
}
