package com.wavemaker.api.tests.runtime;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.wavemaker.api.client.DatabaseRunTimeControllerClient;
import com.wavemaker.api.rest.models.database.HrdbUser;
import com.wavemaker.api.tests.core.BaseTest;
import com.wavemaker.api.tests.designtime.database.SampleDBCreator;

/**
 * Created by Tejaswi Maryala on 11/17/2017.
 */
public class HRDBTests extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(HRDBTests.class);
    private DatabaseRunTimeControllerClient dbRunTimeClient = new DatabaseRunTimeControllerClient();
    private SampleDBCreator sampleDBCreator = new SampleDBCreator();
    private String runtimeId;

    @BeforeClass
    public void importDB() {
        runtimeId = sampleDBCreator.createDBService(getProjectDetails());
    }

    @Test(groups = {"Runtime", "Database", "sampledb"}, description = "Verifies if we are able to get all records with blob table")
    public void getAllRecords() {
        //Step 1
        List<HrdbUser> response = dbRunTimeClient.getAllUsers(runtimeId, getProjectDetails().getName(), "hrdb", "User");
        Assert.assertNotNull(response, "no of records in the table should not be 0");
        logger.info("Get all users is successful");
    }
}
