package com.wavemaker.api.tests.designtime.database;

import java.io.File;

import org.testng.Assert;

import com.wavemaker.api.client.DatabaseServiceControllerClient;
import com.wavemaker.api.rest.models.project.ProjectDetails;
import com.wavemaker.api.utils.ApiUtils;
import com.wavemaker.api.utils.RuntimeUtils;
import com.wavemaker.studio.core.data.constants.DBType;
import com.wavemaker.studio.core.data.model.DataModel;
import com.wavemaker.studio.core.props.DBConnectionProps;

/**
 * Created by Tejaswi Maryala on 11/21/2017.
 */
public class OtherDBService implements DatabaseServiceCreator {

    private DBConnectionProps dbConnectionProps;
    private String jarFilePath;
    private DatabaseServiceControllerClient dbServiceClient = new DatabaseServiceControllerClient();

    public OtherDBService(final DBConnectionProps dbConnectionProps, final String jarFilePath) {
        this.dbConnectionProps = dbConnectionProps;
        this.jarFilePath = jarFilePath;
    }

    @Override
    public String createDBService(final ProjectDetails projectDetails) {
        String studioProjectId = projectDetails.getStudioProjectId();
        //Step 1
        dbServiceClient.verifyJar(studioProjectId, DBType.ORACLE.getUiTypeString());
        File file = ApiUtils.getFile(jarFilePath);
        dbServiceClient.uploadJar(studioProjectId, file);
        //Step 2
        DataModel result = dbServiceClient.importDatabase(studioProjectId, dbConnectionProps);
        Assert.assertNotNull(result, "Import DB is not Successful");
        //Step 3
        return RuntimeUtils.getRuntimeProjectId(studioProjectId);
    }
}
