package com.wavemaker.api.tests.designtime.project;


import com.wavemaker.api.rest.models.project.ProjectDetails;
import com.wavemaker.api.rest.models.project.ProjectModel;

/**
 * Created by Tejaswi Maryala on 11/21/2017.
 */
public interface ProjectCreator {

    ProjectDetails createProject(ProjectModel projectModel);

    boolean deleteProject(ProjectDetails projectDetails);
}
