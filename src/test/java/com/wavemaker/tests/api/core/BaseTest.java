package com.wavemaker.tests.api.core;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;

import com.wavemaker.tests.api.client.ProjectControllerClient;
import com.wavemaker.tests.api.config.StudioTestConfig;
import com.wavemaker.tests.api.designtime.project.ProjectHandler;
import com.wavemaker.tests.api.rest.models.project.PlatformType;
import com.wavemaker.tests.api.rest.models.project.ProjectDetails;
import com.wavemaker.tests.api.rest.models.project.ProjectModel;
import com.wavemaker.tests.api.rest.models.project.ProjectType;
import com.wavemaker.tests.api.login.ApiAuthenticationManager;
import com.wavemaker.tests.api.utils.RuntimeUtils;

/**
 * Created by ArjunSahasranam on 4/2/16.
 */
public abstract class BaseTest implements ApiTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseTest.class);
    private ApiAuthenticationManager authenticationManager = new ApiAuthenticationManager(
            StudioTestConfig.getInstance().getUsersCSV(),StudioTestConfig.getInstance().getNoOfUsers());
    private ProjectHandler projectHandler = new ProjectHandler();
    private ProjectDetails projectDetails;
    private ProjectControllerClient projectControllerClient = new ProjectControllerClient();

    protected ProjectDetails getProjectDetails() {
        return projectDetails;
    }

    public void setProjectDetails(final ProjectDetails projectDetails) {
        this.projectDetails = projectDetails;
    }

    protected void loginAndCreateProject(){
        authenticationManager.login();
        projectDetails = projectHandler.createProject(getProjectModel());
    }

    protected String runApp() {
        String runtimeId = RuntimeUtils.getRuntimeProjectId(getProjectDetails().getStudioProjectId());
        return getBaseUrl() + "/" + runtimeId + "/" + getProjectDetails().getName();
    }

    @AfterClass
    public void deleteProject(){
        projectHandler.deleteProject(projectDetails);
    }

    private ProjectModel getProjectModel() {
        ProjectModel projectModel = new ProjectModel();
        String appName = "dbproj" + RandomStringUtils.randomNumeric(6);
        projectModel.setName(appName);
        projectModel.setDescription(appName);
        projectModel.setProjectType(ProjectType.APPLICATION);
        projectModel.setPlatformType(PlatformType.WEB);
        projectModel.setProjectShellId("");
        projectModel.setIcon("default.png");
        return projectModel;
    }

    protected String getBaseUrl() {
        return StudioTestConfig.getInstance().getUrl();
    }
}
