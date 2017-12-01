package com.wavemaker.tests.api.rest.models.project;

public class ProjectModel {

    private String name;

    private String description;

    private ProjectType projectType;

    private PlatformType platformType;

    private String projectShellId;

    private String icon;

    private String subscriptionPlanCode;

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

    public String getProjectShellId() {
        return projectShellId;
    }

    public void setProjectShellId(String projectShellId) {
        this.projectShellId = projectShellId;
    }

    public ProjectType getProjectType() {
        return projectType;
    }

    public void setProjectType(ProjectType projectType) {
        this.projectType = projectType;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public PlatformType getPlatformType() {
        return platformType;
    }

    public void setPlatformType(PlatformType platformType) {
        this.platformType = platformType;
    }

    public String getSubscriptionPlanCode() {
        return subscriptionPlanCode;
    }

    public void setSubscriptionPlanCode(final String subscriptionPlanCode) {
        this.subscriptionPlanCode = subscriptionPlanCode;
    }

    @Override
    public String toString() {
        return "ProjectModel{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", projectShellId='" + projectShellId + '\'' +
                ", projectType=" + projectType +
                ", platformType=" + platformType +
                ", icon='" + icon + '\'' +
                ", subscriptionPlanCode='" + subscriptionPlanCode + '\'' +
                '}';
    }
}