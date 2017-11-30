package com.wavemaker.api.tests.designtime.database;

import org.testng.Assert;

import com.wavemaker.api.client.DatabaseServiceControllerClient;
import com.wavemaker.api.rest.models.project.ProjectDetails;
import com.wavemaker.api.utils.RuntimeUtils;
import com.wavemaker.studio.core.data.model.DataModel;

/**
 * Created by Tejaswi Maryala on 11/21/2017.
 */
public class SampleDBCreator implements DatabaseServiceCreator {

    private DatabaseServiceControllerClient dbServiceClient = new DatabaseServiceControllerClient();

    @Override
    public void createDBService(final ProjectDetails projectDetails) {
        DataModel result = dbServiceClient.importDatabase(projectDetails.getStudioProjectId(), dbServiceClient.getSampleDbConnectionProps(projectDetails.getStudioProjectId()));
        Assert.assertNotNull(result, "Import DB is not Successful");
    }
}
