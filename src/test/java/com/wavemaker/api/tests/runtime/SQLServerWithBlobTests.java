package com.wavemaker.api.tests.runtime;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.wavemaker.api.rest.models.database.sqlserver.AllTypes;
import com.wavemaker.api.tests.builder.SqlServerDBObjectsBuilder;
import com.wavemaker.api.tests.core.BaseTest;
import com.wavemaker.api.tests.designtime.database.OtherDBService;
import com.wavemaker.api.tests.verifications.database.DatabaseBlobVerification;
import com.wavemaker.studio.core.data.constants.DBType;
import com.wavemaker.studio.core.props.DBConnectionProps;
import com.wavemaker.studio.core.props.TableSelector;

import static com.wavemaker.api.constants.GroupNameConstants.*;

/**
 * Created by Tejaswi Maryala on 11/17/2017.
 */
public class SQLServerWithBlobTests extends BaseTest {
    
    private static final Logger logger = LoggerFactory.getLogger(SQLServerWithBlobTests.class);
    private DatabaseBlobVerification databaseBlobVerification;

    @BeforeClass(alwaysRun = true)
    public void createProjectWithSqlServerDB() {

        //Login and Create project 
        loginAndCreateProject();

        //Import Database into Project
        String dbName = "DB123Test";
        OtherDBService otherDBService = new OtherDBService(getSQLServerDBConnectionProps(getProjectDetails().getName(),dbName),
                "testdata/dbjars/sqljdbc4-4.0.jar");
        otherDBService.createDBService(getProjectDetails());

        //Run Application and get appUrl
        String runTimeUrl = runApp();

        //Set details for verification of Blob
        databaseBlobVerification = new DatabaseBlobVerification(runTimeUrl, dbName, "AllTypes",AllTypes.class);
    }

    @Test(groups = {RUNTIME, DATABASE, SQL_SERVER, INSERT}, description = "Verifies if insertion is successful with blob table")
    public void verifyInsertAndGetSqlServerBlobData() {
        AllTypes allTypes = SqlServerDBObjectsBuilder.buildAllTypes();
        databaseBlobVerification.verifyInsertAndGetBlobData(allTypes, allTypes.getPkId().toString());
    }

    @Test(groups = {RUNTIME, DATABASE, SQL_SERVER, EXPORT}, description = "Verifies if export data is successful with blob table")
    public void verifySqlServerExportBlobData() {
        databaseBlobVerification.verifyExportedBlobData("CSV");
    }

    @Test(enabled = false, groups = {RUNTIME, DATABASE, SQL_SERVER, UPDATE}, description = "Verifies if Updation is successful with blob table")
    public void verifySqlServerUpdateBlobData() {
        AllTypes insertData = SqlServerDBObjectsBuilder.buildAllTypes();
        AllTypes updateData = SqlServerDBObjectsBuilder.buildAllTypes(insertData.getPkId());
        databaseBlobVerification.verifyUpdateBlobData(insertData,updateData,insertData.getPkId().toString());
    }

    @Test(groups = {RUNTIME, DATABASE, SQL_SERVER, DELETE}, description = "Verifies if deletion is successful with blob table")
    public void verifySqlServerDeleteBlobData() {
        AllTypes allTypes = SqlServerDBObjectsBuilder.buildAllTypes();
        databaseBlobVerification.verifyInsertAndDeleteBlobData(allTypes, allTypes.getPkId().toString());
    }
    private DBConnectionProps getSQLServerDBConnectionProps(String projectName,String dbName) {
        List<TableSelector> tableFilter = new ArrayList<>();
        tableFilter.add(new TableSelector("AllTypes", "dbo"));
        List<String> schemaFilter = new ArrayList<>();
        schemaFilter.add("dbo");
        DBConnectionProps dbConnectionProps = new DBConnectionProps();
        dbConnectionProps.setServiceId("");
        dbConnectionProps.setPackageName("com." + projectName);
        dbConnectionProps.setDbType(DBType.SQL_SERVER);
        dbConnectionProps.setHost("52.12.227.219");
        dbConnectionProps.setDbName(dbName);
        dbConnectionProps.setPort("1433");
        dbConnectionProps.setSchemaName("dbo");
        dbConnectionProps.setTableFilter(tableFilter);
        dbConnectionProps.setSchemaFilter(schemaFilter);
        dbConnectionProps.setImpersonateUser(false);
        dbConnectionProps.setMaxPageSize(100);
        dbConnectionProps.setUsername("sa");
        dbConnectionProps.setPassword("pr@m@t!123");
        dbConnectionProps.setUrl("jdbc:sqlserver://52.12.227.219:1433;databaseName=" + dbName);
        dbConnectionProps.setDialect("com.wavemaker.runtime.data.dialect.WMSQLServerDialect");
        dbConnectionProps.setDriverClass("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        dbConnectionProps.setReadOnly(true);
        return dbConnectionProps;
    }
}
