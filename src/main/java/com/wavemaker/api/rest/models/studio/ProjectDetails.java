/**
 * Copyright (c) 2013 - 2014 WaveMaker Inc. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of WaveMaker Inc.
 * You shall not disclose such Confidential Information and shall use it only in accordance
 * with the terms of the source code license agreement you entered into with WaveMaker Inc.
 */
package com.wavemaker.api.rest.models.studio;

import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.SortedSet;

import com.wavemaker.api.rest.models.project.PermissionsObject;
import com.wavemaker.api.rest.models.project.PlatformType;

/**
 * @author Sunil Kumar
 */
public class ProjectDetails {

    private String id;

    private String name;

    private String description;

    private String icon;

    private String type;

    private List<BasicInfo> dataModels;

    private String version;

    private SortedSet<String> pages;

    private Properties properties;

    private boolean upgradeRequired;

    private Date createdDate;

    private Date modifiedDate;

    private Date lastAccessedDate;

    private String groupId;

    private PermissionsObject permissions;

    private String activeTheme;

    private PlatformType platformType;

    private ProjectDetails() {
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public boolean isUpgradeRequired() {
        return upgradeRequired;
    }

    public void setUpgradeRequired(boolean upgradeRequired) {
        this.upgradeRequired = upgradeRequired;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public SortedSet<String> getPages() {
        return pages;
    }

    public void setPages(SortedSet<String> pages) {
        this.pages = pages;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<BasicInfo> getDataModels() {
        return dataModels;
    }

    public void setDataModels(List<BasicInfo> dataModels) {
        this.dataModels = dataModels;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Date getLastAccessedDate() {
        return lastAccessedDate;
    }

    public void setLastAccessedDate(Date lastAccessedDate) {
        this.lastAccessedDate = lastAccessedDate;
    }

    public PermissionsObject getPermissions() {
        return permissions;
    }

    public void setPermissions(PermissionsObject permissions) {
        this.permissions = permissions;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGroupId() {
        return groupId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getActiveTheme() {
        return activeTheme;
    }

    public void setActiveTheme(String activeTheme) {
        this.activeTheme = activeTheme;
    }

    public PlatformType getPlatformType() {
        return platformType;
    }

    public void setPlatformType(PlatformType platformType) {
        this.platformType = platformType;
    }
}
