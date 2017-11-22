package com.wavemaker.api.tests.designtime.database;

import com.wavemaker.api.rest.models.project.ProjectDetails;

/**
 * Created by Tejaswi Maryala on 11/21/2017.
 */
public interface DatabaseServiceCreator {

    String createDBService(ProjectDetails projectDetails);
}
