package com.wavemaker.api.utils;


import com.wavemaker.api.client.DeploymentControllerClient;

/**
 * Created by Prithvi Medavaram on 18/4/16.
 */
public class RuntimeUtils {

    private static final DeploymentControllerClient deploymentControllerClient = new DeploymentControllerClient();

    public static String getRuntimeProjectId(String studioProjectId) {

        String runTimeUrl;
        try {
            runTimeUrl = deploymentControllerClient.inplaceDeploy(studioProjectId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deploy application " + e);
        }
        return runTimeUrl
                .substring(runTimeUrl.indexOf("/", runTimeUrl.indexOf("/") + 2) + 1, runTimeUrl.lastIndexOf("/"));
    }
}
