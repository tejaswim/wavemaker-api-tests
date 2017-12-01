package com.wavemaker.tests.api.runtime;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.wavemaker.tests.api.builder.PostgresDBObjectsBuilder;
import com.wavemaker.tests.api.core.BaseTest;
import com.wavemaker.tests.api.designtime.database.MySqlDBCreator;
import com.wavemaker.tests.api.rest.models.database.postgres.AllTypes;
import com.wavemaker.tests.api.verifications.database.DatabaseBlobVerification;
import com.wavemaker.studio.core.data.constants.DBType;
import com.wavemaker.studio.core.props.DBConnectionProps;
import com.wavemaker.studio.core.props.TableSelector;
import com.wavemaker.tests.api.constants.GroupNameConstants;

/**
 * Created by Tejaswi Maryala on 11/17/2017.
 */
public class PostgressqlWithBlobTests extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(PostgressqlWithBlobTests.class);
    private DatabaseBlobVerification databaseBlobVerification;

    @BeforeClass(alwaysRun = true)
    public void createProjectWithPostgresSQLDB() {

        //Login and Create project
        loginAndCreateProject();

        //Import Database into Project
        String dbName = "viewsdb";
        MySqlDBCreator mySqlDBCreator = new MySqlDBCreator(getPostgressqlDBConnectionProps(getProjectDetails().getName(), dbName));
        mySqlDBCreator.createDBService(getProjectDetails());

        //Run Application and get appUrl
        String runTimeUrl = runApp();

        //Set details for verification of Blob
        databaseBlobVerification = new DatabaseBlobVerification(runTimeUrl, dbName, "AllTypes", AllTypes.class);
    }

    @Test(groups = {GroupNameConstants.RUNTIME, GroupNameConstants.DATABASE, GroupNameConstants.POSTGRES_SQL, GroupNameConstants.GET, GroupNameConstants.INSERT}, description = "Verifies if we are able to get all records with blob table")
    public void verifyInsertAndGetPostgresSqlBlobData() {
        AllTypes allTypes = PostgresDBObjectsBuilder.buildAllTypes();
        databaseBlobVerification.verifyInsertAndGetBlobData(allTypes, allTypes.getId().toString());
    }

    @Test(groups = {GroupNameConstants.RUNTIME, GroupNameConstants.DATABASE, GroupNameConstants.POSTGRES_SQL, GroupNameConstants.EXPORT}, description = "Verifies if export data is successful with blob table")
    public void verifyOracleExportBlobData() {
        databaseBlobVerification.verifyExportedBlobData("CSV");
    }

    @Test(groups = {GroupNameConstants.RUNTIME, GroupNameConstants.DATABASE, GroupNameConstants.POSTGRES_SQL, GroupNameConstants.UPDATE}, description = "Verifies if Updation is successful with blob table")
    public void verifyPostgresSqlUpdateBlobData() {
        AllTypes insertData = PostgresDBObjectsBuilder.buildAllTypes();
        AllTypes updateData = PostgresDBObjectsBuilder.buildAllTypes(insertData.getId());
        databaseBlobVerification.verifyUpdateBlobData(insertData, updateData, insertData.getId().toString());
    }

    @Test(groups = {GroupNameConstants.RUNTIME, GroupNameConstants.DATABASE, GroupNameConstants.POSTGRES_SQL, GroupNameConstants.DELETE}, description = "Verifies if deletion is successful with blob table")
    public void verifyPostgresSqlDeleteBlobData() {
        AllTypes allTypes = PostgresDBObjectsBuilder.buildAllTypes();
        databaseBlobVerification.verifyInsertAndDeleteBlobData(allTypes, allTypes.getId().toString());
    }

    private DBConnectionProps getPostgressqlDBConnectionProps(String projectName, final String dbName) {
        List<TableSelector> tableFilter = new ArrayList<>();
        tableFilter.add(new TableSelector("all types", "public"));
        List<String> schemaFilter = new ArrayList<>();
        schemaFilter.add("public");
        DBConnectionProps dbConnectionProps = new DBConnectionProps();
        dbConnectionProps.setServiceId("");
        dbConnectionProps.setPackageName("com." + projectName);
        dbConnectionProps.setDbType(DBType.POSTGRES);
        dbConnectionProps.setHost("ec2-54-87-2-36.compute-1.amazonaws.com");
        dbConnectionProps.setDbName(dbName);
        dbConnectionProps.setPort("5432");
        dbConnectionProps.setSchemaName("public");
        dbConnectionProps.setTableFilter(tableFilter);
        dbConnectionProps.setSchemaFilter(schemaFilter);
        dbConnectionProps.setImpersonateUser(false);
        dbConnectionProps.setMaxPageSize(100);
        dbConnectionProps.setUsername("postgres");
        dbConnectionProps.setPassword("wavemaker");
        dbConnectionProps.setUrl("jdbc:postgresql://ec2-54-87-2-36.compute-1.amazonaws.com:5432/" + dbName);
        dbConnectionProps.setDialect("com.wavemaker.runtime.data.dialect.WMPostgresSQLDialect");
        dbConnectionProps.setDriverClass("org.postgresql.Driver");
        dbConnectionProps.setReadOnly(true);
        return dbConnectionProps;
    }
}
