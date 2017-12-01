package com.wavemaker.tests.api.rest.models.project;

public class ProjectDetails extends ProjectInfo {

    private User currentOwner;
    private String currentUserId;
    private String templateName;
    private String version;
    private String vcsUrl;
    private String vcsAccountId;
    private VcsAccountInfo vcsAccountInfo;
    private VisibilityType visibility;
    private String projectAppUrl;
    private boolean isActive;
    private boolean markedDelete;
    private String createdByUserId;
    private String lastModifiedByUserId;
    private Role role;
    private ProjectMembershipStatus mappingStatus;
    private String paymentHostingPageUrl;
    private String studioProjectId;
    private com.wavemaker.tests.api.rest.models.studio.ProjectDetails studioProjectDetails;
    private PermissionsObject permissions;

    public ProjectDetails() {
    }

    public ProjectDetails(ProjectInfo project) {
        this.setProjectId(project.getProjectId());
        this.setName(project.getName());
        this.setDescription(project.getDescription());
        this.setIcon(project.getIcon());
        this.setCreatedByUserId(project.getCreatedByUserId());
        this.setCreatedAt(project.getCreatedAt());
        this.setLastModifiedAt(project.getLastModifiedAt());
        this.setLastModifiedByUserId(project.getLastModifiedByUserId());
        this.setStatus(project.getStatus());
        this.setSubscriptionState(project.getSubscriptionState());
        this.setSubscriptionPlanCode(project.getSubscriptionPlanCode());
        this.setExpiresOn(project.getExpiresOn());
        this.setPlatformType(project.getPlatformType());
    }


    public boolean isActive() {
        return isActive;
    }

    public void setActive(final boolean isActive) {
        this.isActive = isActive;
    }

    public User getCurrentOwner() {
        return currentOwner;
    }

    public void setCurrentOwner(User currentOwner) {
        this.currentOwner = currentOwner;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(final String version) {
        this.version = version;
    }

    public String getVcsUrl() {
        return vcsUrl;
    }

    public void setVcsUrl(String vcsUrl) {
        this.vcsUrl = vcsUrl;
    }

    public VcsAccountInfo getVcsAccountInfo() {
        return vcsAccountInfo;
    }

    public void setVcsAccountInfo(VcsAccountInfo vcsAccountInfo) {
        this.vcsAccountInfo = vcsAccountInfo;
    }

    public VisibilityType getVisibility() {
        return visibility;
    }

    public void setVisibility(VisibilityType visibility) {
        this.visibility = visibility;
    }

    public String getProjectAppUrl() {
        return projectAppUrl;
    }

    public void setProjectAppUrl(final String projectAppUrl) {
        this.projectAppUrl = projectAppUrl;
    }

    public boolean isMarkedDelete() {
        return markedDelete;
    }

    public void setMarkedDelete(boolean markedDelete) {
        this.markedDelete = markedDelete;
    }

    public String getCurrentUserId() {
        return currentUserId;
    }

    public void setCurrentUserId(String currentUserId) {
        this.currentUserId = currentUserId;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public ProjectMembershipStatus getMappingStatus() {
        return mappingStatus;
    }

    public void setMappingStatus(ProjectMembershipStatus mappingStatus) {
        this.mappingStatus = mappingStatus;
    }

    public String getStudioProjectId() {
        return studioProjectId;
    }

    public void setStudioProjectId(String studioProjectId) {
        this.studioProjectId = studioProjectId;
    }

    public com.wavemaker.tests.api.rest.models.studio.ProjectDetails getStudioProjectDetails() {
        return studioProjectDetails;
    }

    public void setStudioProjectDetails(
            com.wavemaker.tests.api.rest.models.studio.ProjectDetails studioProjectDetails) {
        this.studioProjectDetails = studioProjectDetails;
    }

    public PermissionsObject getPermissions() {
        return permissions;
    }

    public void setPermissions(PermissionsObject permissions) {
        this.permissions = permissions;
    }

    public String getVcsAccountId() {
        return vcsAccountId;
    }

    public void setVcsAccountId(String vcsAccountId) {
        this.vcsAccountId = vcsAccountId;
    }

    public String getPaymentHostingPageUrl() {
        return paymentHostingPageUrl;
    }

    public void setPaymentHostingPageUrl(String paymentHostingPageUrl) {
        this.paymentHostingPageUrl = paymentHostingPageUrl;
    }

    @Override
    public String toString() {
        return super.toString() + "ProjectDetails{" +
                "currentOwner=" + currentOwner +
                ", templateName='" + templateName + '\'' +
                ", version='" + version + '\'' +
                ", vcsUrl='" + vcsUrl + '\'' +
                ", vcsAccountInfo=" + vcsAccountInfo +
                ", visibility=" + visibility +
                ", projectAppUrl='" + projectAppUrl + '\'' +
                ", isActive=" + isActive +
                ", markedDelete=" + markedDelete +
                "} ";
    }

}