package com.wavemaker.api.client;


import com.wavemaker.api.rest.RestInputImpl;
import com.wavemaker.api.wrapper.StringWrapper;

/**
 * Created by naveen.
 * On  15-02-2016
 * At 17:32
 * For com.wavemaker.test.studio.framework.api.client for Studio UI Automation
 */
public class DeploymentControllerClient extends BaseClient {

    private static final String STUDIO_PROJECT_ID = "studioProjectId";

    private String inPlaceDeployUrl = "/studio/services/projects/:studioProjectId/deployment/inplaceDeploy";
    private String inPlaceUnDeployUrl = "/studio/services/projects/:studioProjectId/deployment/inplaceUnDeploy";
    private String deployUrl = "/studio/services/projects/:studioProjectId/deployment/deploy";

    public String inplaceDeploy(String studioProjectId) {
        String url = constructUrl(inPlaceDeployUrl, new String[][]{{STUDIO_PROJECT_ID, studioProjectId}});
        StringWrapper deployMessage = post(url, new RestInputImpl(), StringWrapper.class);
        return deployMessage.getResult();
    }

    public String inplaceUnDeploy(String studioProjectId) {
        String url = constructUrl(inPlaceUnDeployUrl, new String[][]{{STUDIO_PROJECT_ID, studioProjectId}});
        StringWrapper deployMessage = post(url, new RestInputImpl(),StringWrapper.class);
        return deployMessage.getResult();

    }
}
