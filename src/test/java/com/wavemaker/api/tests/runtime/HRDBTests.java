package com.wavemaker.api.tests.runtime;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.wavemaker.api.client.DatabaseRunTimeControllerClient;
import com.wavemaker.api.rest.models.RestResponse;
import com.wavemaker.api.rest.models.database.HrdbUser;
import com.wavemaker.api.tests.core.BaseTest;
import com.wavemaker.api.tests.designtime.database.SampleDBCreator;

import static com.wavemaker.api.constants.GroupNameConstants.DATABASE;
import static com.wavemaker.api.constants.GroupNameConstants.EXPORT;
import static com.wavemaker.api.constants.GroupNameConstants.GET;
import static com.wavemaker.api.constants.GroupNameConstants.RUNTIME;
import static com.wavemaker.api.constants.GroupNameConstants.SAMPLE_DB;

/**
 * Created by Tejaswi Maryala on 11/17/2017.
 */
public class HRDBTests extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(HRDBTests.class);
    private DatabaseRunTimeControllerClient dbRunTimeClient = new DatabaseRunTimeControllerClient();
    private SampleDBCreator sampleDBCreator = new SampleDBCreator();
    private String runtimeId;
    private String dbName = "hrdb";
    private String tableName = "User";

    @BeforeClass(alwaysRun = true)
    public void importDB() {
        runtimeId = sampleDBCreator.createDBService(getProjectDetails());
    }

    @Test(groups = {RUNTIME, DATABASE, SAMPLE_DB, GET}, description = "Verifies if we are able to get all records with blob table")
    public void getAllRecords() {
        //Step 1
        List<HrdbUser> response = dbRunTimeClient.getAllUsers(runtimeId, getProjectDetails().getName(), dbName, tableName);
        Assert.assertNotNull(response, "no of records in the table should not be 0");
        logger.info("Get all users is successful");
    }

    @Test(groups = {RUNTIME, DATABASE, SAMPLE_DB, EXPORT}, description = "Verifies if export data is successful with blob table")
    public void exportBlobData() {
        RestResponse response = dbRunTimeClient.exportBlobData(runtimeId, getProjectDetails().getName(), dbName, tableName,
                "CSV");
        Assert.assertNotNull(response, "no of records in the table should not be 0");
        logger.info("Export to CSV is successful with response {}", response.toString());
    }
}
