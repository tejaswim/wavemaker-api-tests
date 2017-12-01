package com.wavemaker.tests.api.runtime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.wavemaker.tests.api.builder.SampleDBObjectsBuilder;
import com.wavemaker.tests.api.core.BaseTest;
import com.wavemaker.tests.api.designtime.database.SampleDBCreator;
import com.wavemaker.tests.api.rest.models.database.sampledb.User;
import com.wavemaker.tests.api.verifications.database.DatabaseBlobVerification;
import com.wavemaker.tests.api.constants.GroupNameConstants;

/**
 * Created by Tejaswi Maryala on 11/17/2017.
 */
public class HRDBTests extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(HRDBTests.class);
    private DatabaseBlobVerification databaseBlobVerification;

    @BeforeClass(alwaysRun = true)
    public void createProjectAndImportHRDB() {
        //Login and Create project
        loginAndCreateProject();

        //Import Database into Project
        SampleDBCreator sampleDBCreator = new SampleDBCreator();
        sampleDBCreator.createDBService(getProjectDetails());

        //Run Application and get appUrl
        String runTimeUrl = runApp();

        //Set details for verification of Blob
        databaseBlobVerification = new DatabaseBlobVerification(runTimeUrl, "hrdb", "User", User.class);
    }

    @Test(groups = {GroupNameConstants.RUNTIME, GroupNameConstants.DATABASE, GroupNameConstants.ORACLE, GroupNameConstants.GET, GroupNameConstants.INSERT}, description = "Verifies if insertion is successful with blob table")
    public void verifyInsertAndGetSampleDBBlobData() {
        User user = SampleDBObjectsBuilder.buildUser();
        databaseBlobVerification.verifyInsertAndGetBlobData(user, user.getUserId().toString());
    }

    @Test(groups = {GroupNameConstants.RUNTIME, GroupNameConstants.DATABASE, GroupNameConstants.ORACLE, GroupNameConstants.EXPORT}, description = "Verifies if export data is successful with blob table")
    public void verifySampleDBExportBlobData() {
        databaseBlobVerification.verifyExportedBlobData("CSV");
    }

    @Test(enabled = false, groups = {GroupNameConstants.RUNTIME, GroupNameConstants.DATABASE, GroupNameConstants.ORACLE, GroupNameConstants.UPDATE}, description = "Verifies if Updation is successful with blob table")
    public void verifySampleDBUpdateBlobData() {
        User user = SampleDBObjectsBuilder.buildUser();
        User updatedUser = SampleDBObjectsBuilder.buildUser(user.getUserId());
        databaseBlobVerification.verifyUpdateBlobData(user, updatedUser, user.getUserId().toString());
    }

    @Test(groups = {GroupNameConstants.RUNTIME, GroupNameConstants.DATABASE, GroupNameConstants.ORACLE, GroupNameConstants.DELETE}, description = "Verifies if deletion is successful with blob table")
    public void verifySampleDBDeleteBlobData() {
        User user = SampleDBObjectsBuilder.buildUser();
        databaseBlobVerification.verifyInsertAndDeleteBlobData(user, user.getUserId().toString());
    }
}
