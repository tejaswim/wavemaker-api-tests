package com.wavemaker.api.tests.designtime.project;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wavemaker.api.client.ProjectControllerClient;
import com.wavemaker.api.exception.ProjectCreationException;
import com.wavemaker.api.rest.models.project.ProjectDetails;
import com.wavemaker.api.rest.models.project.ProjectModel;

/**
 * Created by Tejaswi Maryala on 11/21/2017.
 */
public class ProjectHandler implements ProjectCreator {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectHandler.class);
    private ProjectControllerClient projectControllerClient = new ProjectControllerClient();
    private ProjectDetails projectDetails;

    @Override
    public ProjectDetails createProject(ProjectModel projectModel) {
        try {
            projectDetails = projectControllerClient.createProject(projectModel);
        } catch (Exception e) {
            String exceptionMessage = "Project creation error for designtime model " + projectModel.toString();
            throw new ProjectCreationException(exceptionMessage, e);
        }
        return projectDetails;
    }

    @Override
    public boolean deleteProject(ProjectDetails projectDetails) {
        if (StringUtils.isNotBlank(projectDetails.getProjectId())) {
            boolean deleteProject = projectControllerClient.deleteProject(projectDetails.getProjectId());
            if (deleteProject) {
                LOGGER.info("Project {} deletion is successful", projectDetails.getName());
                return true;
            }
        }
        return false;
    }
}
