package com.wavemaker.api.factory;


import com.wavemaker.api.client.ProjectControllerClient;

/**
 * Created by Prithvi Medavaram on 27/6/16.
 */
public class ProjectControllerClientFactory {
    private static final ProjectControllerClient projectControllerClient = new ProjectControllerClient();

    public static ProjectControllerClient getInstance() {
        return projectControllerClient;
    }
}
