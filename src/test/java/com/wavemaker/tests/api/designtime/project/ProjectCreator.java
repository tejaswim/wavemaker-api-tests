package com.wavemaker.tests.api.designtime.project;


import com.wavemaker.tests.api.rest.models.project.ProjectDetails;
import com.wavemaker.tests.api.rest.models.project.ProjectModel;

/**
 * Created by Tejaswi Maryala on 11/21/2017.
 */
public interface ProjectCreator {

    ProjectDetails createProject(ProjectModel projectModel);

    boolean deleteProject(ProjectDetails projectDetails);
}
