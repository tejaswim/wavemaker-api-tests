package com.wavemaker.api.rest.models.project;

public enum ProjectType {
    APPLICATION("application"),
    PREFAB("prefab"),
    TEMPLATEBUNDLE("templateBundle");

    private String artifactFolderName;

    ProjectType(String artifactFolderName) {
        this.artifactFolderName = artifactFolderName;
    }

    public String getArtifactFolderName() {
        return artifactFolderName;
    }
}