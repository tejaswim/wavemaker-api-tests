package com.wavemaker.api.tests.runtime;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.wavemaker.api.client.DatabaseRunTimeControllerClient;
import com.wavemaker.api.rest.models.RestResponse;
import com.wavemaker.api.rest.models.database.wmstudio.AllTypes;
import com.wavemaker.api.tests.builder.OracleDBObjectsBuilder;
import com.wavemaker.api.tests.core.BaseTest;
import com.wavemaker.api.tests.designtime.database.MySqlDBCreator;
import com.wavemaker.api.tests.verifications.database.DatabaseBlobVerification;
import com.wavemaker.api.utils.RuntimeUtils;
import com.wavemaker.studio.core.data.constants.DBType;
import com.wavemaker.studio.core.props.DBConnectionProps;
import com.wavemaker.studio.core.props.TableSelector;

import static com.wavemaker.api.constants.GroupNameConstants.*;

/**
 * Created by Tejaswi Maryala on 11/27/2017.
 */
public class MysqlExternalDBTests extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(MysqlExternalDBTests.class);
    private DatabaseBlobVerification databaseBlobVerification;

    @BeforeClass(alwaysRun = true)
    public void createProjectWithWithExternalMysqlDB() {
        //Login and Create project
        loginAndCreateProject();

        //Import Database into Project
        String dbName = "AllTypesDBAutomation";
        MySqlDBCreator mySqlDBCreator = new MySqlDBCreator(getMysqlExternalProps(getProjectDetails().getName(), dbName));
        mySqlDBCreator.createDBService(getProjectDetails());

        //Run Application and get appUrl
        String runTimeUrl = runApp();

        //Set details for verification of Blob
        databaseBlobVerification = new DatabaseBlobVerification(runTimeUrl, dbName, "AllTypes", AllTypes.class);
    }

    @Test(groups = {RUNTIME, DATABASE, MYSQL_EXTERNAL, GET, INSERT}, description = "Verifies if we are able to get all records with blob table")
    public void verifyInsertAndGetMysqlExtBlobData() {
        AllTypes allTypes = OracleDBObjectsBuilder.buildAllTypes();
        databaseBlobVerification.verifyInsertAndGetBlobData(allTypes, allTypes.getPkId().toString());
    }

    @Test(groups = {RUNTIME, DATABASE, MYSQL_EXTERNAL, UPDATE}, description = "Verifies if insertion is successful with blob table")
    public void verifyMysqlExtUpdateBlobData() {
        AllTypes insertData = OracleDBObjectsBuilder.buildAllTypes();
        AllTypes updateData = OracleDBObjectsBuilder.buildAllTypes(insertData.getPkId());
        databaseBlobVerification.verifyUpdateBlobData(insertData,updateData,insertData.getPkId().toString());
    }

    @Test(groups = {RUNTIME, DATABASE, MYSQL_EXTERNAL, EXPORT}, description = "Verifies if export data is successful with blob table")
    public void verifyMysqlExtExportBlobData() {
        databaseBlobVerification.verifyExportedBlobData("CSV");
    }

    @Test(groups = {RUNTIME, DATABASE, MYSQL_EXTERNAL, DELETE}, description = "Verifies if deletion is successful with blob table")
    public void verifyMysqlExtDeleteBlobData() {
        AllTypes allTypes = OracleDBObjectsBuilder.buildAllTypes();
        databaseBlobVerification.verifyInsertAndDeleteBlobData(allTypes, allTypes.getPkId().toString());
    }

    private static DBConnectionProps getMysqlExternalProps(String projectName, String dataModelName) {
        DBConnectionProps props = new DBConnectionProps();
        String id = dataModelName;
        props.setSchemaName(id);
        props.setDbName(id);
        props.setDbType(DBType.MYSQL);
        props.setServiceId(id);
        props.setHost("ec2-54-87-2-36.compute-1.amazonaws.com");
        props.setPackageName("com." + projectName.toLowerCase() + "." + id.toLowerCase());
        props.setPassword("cloudjee123");
        props.setPort("");
        props.setSchemaFilter(new ArrayList<String>());
        props.setServiceId(id);
        props.setTableFilter(new ArrayList<TableSelector>());
        props.setUrl(
                "jdbc:mysql://ec2-54-87-2-36.compute-1.amazonaws.com:3306/" + id + "?useUnicode=yes&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull");
        props.setUsername("root");
        props.setDriverClass("com.mysql.jdbc.Driver");
        props.setDialect("com.wavemaker.runtime.data.dialect.MySQLDialect");
        return props;
    }
}
