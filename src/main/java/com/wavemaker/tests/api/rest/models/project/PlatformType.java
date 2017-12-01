package com.wavemaker.tests.api.rest.models.project;

/**
 * Created by ArjunSahasranam on 19/8/15.
 */
public enum PlatformType {
    WEB("WEB", "web", "wmapp", "default"), MOBILE("MOBILE", "mobile", "wmmobile", "mobile"), DEFAULT("DEFAULT",
            "default", "wmapp", "default");

    private String name;
    private String artifactFolderName;
    private String staticFileFolderName;
    private String defaultThemeName;

    private PlatformType(String name, String artifactFolderName, String staticFileFolderName, String defaultThemeName) {
        this.name = name;
        this.artifactFolderName = artifactFolderName;
        this.staticFileFolderName = staticFileFolderName;
        this.defaultThemeName = defaultThemeName;
    }

    public String getName() {
        return name;
    }

    public String getStaticFileFolderName() {
        return staticFileFolderName;
    }

    public String getArtifactFolderName() {
        return artifactFolderName;
    }

    public String getDefaultThemeName() {
        return defaultThemeName;
    }

    public static PlatformType getPlatformType(String name) {
        for (PlatformType platformType : values()) {
            if (platformType.getName().equals(name)) {
                return platformType;
            }
        }

        return null;
    }
}
