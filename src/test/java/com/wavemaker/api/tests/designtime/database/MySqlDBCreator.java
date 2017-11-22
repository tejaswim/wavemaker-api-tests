package com.wavemaker.api.tests.designtime.database;

import org.testng.Assert;

import com.wavemaker.api.client.DatabaseServiceControllerClient;
import com.wavemaker.api.rest.models.project.ProjectDetails;
import com.wavemaker.api.utils.RuntimeUtils;
import com.wavemaker.studio.core.data.constants.DBType;
import com.wavemaker.studio.core.data.model.DataModel;
import com.wavemaker.studio.core.props.DBConnectionProps;

/**
 * Created by Tejaswi Maryala on 11/21/2017.
 */
public class MySqlDBCreator implements DatabaseServiceCreator {

    private  DBConnectionProps dbConnectionProps;
    private DatabaseServiceControllerClient dbServiceClient = new DatabaseServiceControllerClient();

    public MySqlDBCreator(final DBConnectionProps dbConnectionProps) {
        this.dbConnectionProps = dbConnectionProps;
    }

    @Override
    public String createDBService(final ProjectDetails projectDetails) {
        String studioProjectId = projectDetails.getStudioProjectId();

        //Step 1
        dbServiceClient.verifyJar(studioProjectId, DBType.MYSQL.getUiTypeString());

        //Step 2
        DataModel result = dbServiceClient.importDatabase(studioProjectId, dbConnectionProps);
        Assert.assertNotNull(result, "Import DB is not Successful");

        //Step 3
        return RuntimeUtils.getRuntimeProjectId(studioProjectId);
    }
}