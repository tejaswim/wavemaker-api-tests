package com.wavemaker.tests.api.designtime.database;

import com.wavemaker.tests.api.rest.models.project.ProjectDetails;

/**
 * Created by Tejaswi Maryala on 11/21/2017.
 */
public interface DatabaseServiceCreator {

    void createDBService(ProjectDetails projectDetails);
}
