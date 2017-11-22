package com.wavemaker.api.tests.core;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.wavemaker.api.config.StudioTestConfig;
import com.wavemaker.api.rest.models.project.PlatformType;
import com.wavemaker.api.rest.models.project.ProjectDetails;
import com.wavemaker.api.rest.models.project.ProjectModel;
import com.wavemaker.api.rest.models.project.ProjectType;
import com.wavemaker.api.tests.designtime.login.ApiAuthenticationManager;
import com.wavemaker.api.tests.designtime.project.ProjectHandler;

/**
 * Created by ArjunSahasranam on 4/2/16.
 */
public abstract class BaseTest implements ApiTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseTest.class);
    private ApiAuthenticationManager authenticationManager = new ApiAuthenticationManager(
            StudioTestConfig.getInstance().getUsersCSV(),StudioTestConfig.getInstance().getNoOfUsers());
    private ProjectHandler projectHandler = new ProjectHandler();
    private ProjectDetails projectDetails;

    public ProjectDetails getProjectDetails() {
        return projectDetails;
    }

    public void setProjectDetails(final ProjectDetails projectDetails) {
        this.projectDetails = projectDetails;
    }

    @BeforeClass
    public void loginAndCreateProject(){
        authenticationManager.login();
        projectDetails = projectHandler.createProject(getProjectModel());
    }

    @AfterClass
    public void deleteProject(){
        projectHandler.deleteProject(projectDetails);
    }

    protected ProjectModel getProjectModel() {
        ProjectModel projectModel = new ProjectModel();
        projectModel.setName("dbproj" + RandomStringUtils.randomNumeric(6));
        projectModel.setDescription("");
        projectModel.setProjectType(ProjectType.APPLICATION);
        projectModel.setPlatformType(PlatformType.WEB);
        projectModel.setProjectShellId("");
        projectModel.setIcon("default.png");
        return projectModel;
    }
}