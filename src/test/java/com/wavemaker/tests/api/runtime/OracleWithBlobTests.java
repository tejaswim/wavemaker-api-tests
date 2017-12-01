package com.wavemaker.tests.api.runtime;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.wavemaker.tests.api.core.BaseTest;
import com.wavemaker.tests.api.rest.models.database.wmstudio.AllTypes;
import com.wavemaker.tests.api.builder.OracleDBObjectsBuilder;
import com.wavemaker.tests.api.designtime.database.OtherDBService;
import com.wavemaker.tests.api.verifications.database.DatabaseBlobVerification;
import com.wavemaker.studio.core.data.constants.DBType;
import com.wavemaker.studio.core.props.DBConnectionProps;
import com.wavemaker.studio.core.props.TableSelector;
import com.wavemaker.tests.api.constants.GroupNameConstants;

/**
 * Created by Tejaswi Maryala on 11/17/2017.
 */
public class OracleWithBlobTests extends BaseTest {

    private DatabaseBlobVerification databaseBlobVerification;

    @BeforeClass(alwaysRun = true)
    public void createProjectWithOracleDB() {
        //Login and Create project
        loginAndCreateProject();

        //Import Database into Project
        OtherDBService otherDBService = new OtherDBService(getOracleDBConnectionProps(getProjectDetails().getName()),
                "testdata/dbjars/ojdbc6-11.2.0.jar");
        otherDBService.createDBService(getProjectDetails());

        //Run Application and get appUrl
        String runTimeUrl = runApp();

        //Set details for verification of Blob
        databaseBlobVerification = new DatabaseBlobVerification(runTimeUrl, "WMSTUDIO", "AllTypes", AllTypes.class);
    }

    @Test(groups = {GroupNameConstants.RUNTIME, GroupNameConstants.DATABASE, GroupNameConstants.ORACLE, GroupNameConstants.GET, GroupNameConstants.INSERT}, description = "Verifies if insertion is successful with blob table")
    public void verifyInsertAndGetOracleBlobData() {
        AllTypes allTypes = OracleDBObjectsBuilder.buildAllTypes();
        databaseBlobVerification.verifyInsertAndGetBlobData(allTypes, allTypes.getPkId().toString());
    }

    @Test(groups = {GroupNameConstants.RUNTIME, GroupNameConstants.DATABASE, GroupNameConstants.ORACLE, GroupNameConstants.EXPORT}, description = "Verifies if export data is successful with blob table")
    public void verifyOracleExportBlobData() {
        databaseBlobVerification.verifyExportedBlobData("CSV");
    }

    @Test(enabled = false, groups = {GroupNameConstants.RUNTIME, GroupNameConstants.DATABASE, GroupNameConstants.ORACLE, GroupNameConstants.UPDATE}, description = "Verifies if Updation is successful with blob table")
    public void verifyOracleUpdateBlobData() {
        AllTypes insertData = OracleDBObjectsBuilder.buildAllTypes();
        AllTypes updateData = OracleDBObjectsBuilder.buildAllTypes(insertData.getPkId());
        databaseBlobVerification.verifyUpdateBlobData(insertData, updateData, insertData.getPkId().toString());
    }

    @Test(groups = {GroupNameConstants.RUNTIME, GroupNameConstants.DATABASE, GroupNameConstants.ORACLE, GroupNameConstants.DELETE}, description = "Verifies if deletion is successful with blob table")
    public void verifyOracleDeleteBlobData() {
        AllTypes allTypes = OracleDBObjectsBuilder.buildAllTypes();
        databaseBlobVerification.verifyInsertAndDeleteBlobData(allTypes, allTypes.getPkId().toString());
    }

    private DBConnectionProps getOracleDBConnectionProps(String projectName) {
        List<TableSelector> tableFilter = new ArrayList<>();
        tableFilter.add(new TableSelector("ALL TYPES", "ANITHA"));
        List<String> schemaFilter = new ArrayList<>();
        schemaFilter.add("ANITHA");
        DBConnectionProps dbConnectionProps = new DBConnectionProps();
        dbConnectionProps.setServiceId("");
        dbConnectionProps.setPackageName("com." + projectName);
        dbConnectionProps.setDbType(DBType.ORACLE);
        dbConnectionProps.setHost("54.189.37.152");
        dbConnectionProps.setDbName("WMSTUDIO");
        dbConnectionProps.setPort("1521");
        dbConnectionProps.setSchemaName("ANITHA");
        dbConnectionProps.setTableFilter(tableFilter);
        dbConnectionProps.setSchemaFilter(schemaFilter);
        dbConnectionProps.setImpersonateUser(false);
        dbConnectionProps.setMaxPageSize(100);
        dbConnectionProps.setUsername("anitha");
        dbConnectionProps.setPassword("anitha");
        dbConnectionProps.setUrl("jdbc:oracle:thin:@//54.189.37.152:1521/WMSTUDIO");
        dbConnectionProps.setDialect("com.wavemaker.runtime.data.dialect.OracleDialect");
        dbConnectionProps.setDriverClass("oracle.jdbc.driver.OracleDriver");
        dbConnectionProps.setReadOnly(true);
        return dbConnectionProps;
    }
}
