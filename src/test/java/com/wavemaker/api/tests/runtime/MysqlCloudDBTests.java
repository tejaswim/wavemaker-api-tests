package com.wavemaker.api.tests.runtime;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.wavemaker.api.client.DatabaseRunTimeControllerClient;
import com.wavemaker.api.client.DatabaseServiceControllerClient;
import com.wavemaker.api.rest.models.RestResponse;
import com.wavemaker.api.rest.models.database.HrdbUser;
import com.wavemaker.api.rest.models.database.wmstudio.AllTypes;
import com.wavemaker.api.tests.builder.OracleDBObjectsBuilder;
import com.wavemaker.api.tests.core.BaseTest;
import com.wavemaker.api.tests.designtime.database.MySqlDBCreator;
import com.wavemaker.api.tests.verifications.database.DatabaseBlobVerification;
import com.wavemaker.api.utils.ApiUtils;
import com.wavemaker.api.utils.RuntimeUtils;
import com.wavemaker.studio.core.data.constants.DBType;
import com.wavemaker.studio.core.props.DBConnectionProps;
import com.wavemaker.studio.core.props.TableSelector;

import static com.wavemaker.api.constants.GroupNameConstants.*;

/**
 * Created by Tejaswi Maryala on 11/27/2017.
 */
public class MysqlCloudDBTests extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(MysqlCloudDBTests.class);
    private DatabaseServiceControllerClient dbServiceClient = new DatabaseServiceControllerClient();
    private DatabaseBlobVerification databaseBlobVerification;

    @BeforeClass(alwaysRun = true)
    public void createProjectWithMysqlCloudDB() {

        //Login and Create project 
        loginAndCreateProject();

        //Import Database into Project
        String dbName = "alltypes";
        MySqlDBCreator mySqlDBCreator = new MySqlDBCreator(getMysqlCloudProps(getProjectDetails().getName(), dbName));
        File file = new File(ApiUtils.getSqlFilePath("alltypesDB.sql"));
        dbServiceClient.importSqlFile(getProjectDetails().getStudioProjectId(), "", file);
        mySqlDBCreator.createDBService(getProjectDetails());

        //Run Application and get appUrl
        String runTimeUrl = runApp();

        //Set details for verification of Blob
        databaseBlobVerification = new DatabaseBlobVerification(runTimeUrl, dbName, "AllTypes", AllTypes.class);
    }


    @Test(groups = {RUNTIME, DATABASE, MYSQL_CLOUD, GET, INSERT}, description = "Verifies if insertion is successful with blob table")
    public void verifyInsertAndGetMysqlCloudBlobData() {
        AllTypes allTypes = OracleDBObjectsBuilder.buildAllTypes();
        databaseBlobVerification.verifyInsertAndGetBlobData(allTypes, allTypes.getPkId().toString());
    }

    @Test(groups = {RUNTIME, DATABASE, MYSQL_CLOUD, EXPORT}, description = "Verifies if export data is successful with blob table")
    public void verifyMysqlCloudExportBlobData() {
        databaseBlobVerification.verifyExportedBlobData("CSV");
    }

    @Test(enabled = false, groups = {RUNTIME, DATABASE, MYSQL_CLOUD, UPDATE}, description = "Verifies if Updation is successful with blob table")
    public void verifyMysqlCloudUpdateBlobData() {
        AllTypes insertData = OracleDBObjectsBuilder.buildAllTypes();
        AllTypes updateData = OracleDBObjectsBuilder.buildAllTypes(insertData.getPkId());
        databaseBlobVerification.verifyUpdateBlobData(insertData, updateData, insertData.getPkId().toString());
    }

    @Test(groups = {RUNTIME, DATABASE, MYSQL_CLOUD, DELETE}, description = "Verifies if deletion is successful with blob table")
    public void verifyMysqlCloudDeleteBlobData() {
        AllTypes allTypes = OracleDBObjectsBuilder.buildAllTypes();
        databaseBlobVerification.verifyInsertAndDeleteBlobData(allTypes, allTypes.getPkId().toString());
    }

    private static DBConnectionProps getMysqlCloudProps(String projectName, String dataModelName) {
        DBConnectionProps props = new DBConnectionProps();
        String id = dataModelName;
        props.setSchemaName(id);
        props.setDbName(id);
        props.setDbType(DBType.MYSQL);
        props.setServiceId(id);
        props.setHost("{WM_CLOUD_MYSQL_HOST}");
        props.setPackageName("com." + projectName.toLowerCase() + "." + id.toLowerCase());
        props.setPassword("{WM_CLOUD_MYSQL_PASSWORD}");
        props.setPort("");
        props.setSchemaFilter(new ArrayList<String>());
        props.setServiceId(id);
        props.setTableFilter(new ArrayList<TableSelector>());
        props.setUrl("jdbc:mysql://{WM_CLOUD_MYSQL_HOST}/" + id + "?useUnicode=yes&characterEncoding=UTF-8" +
                "&zeroDateTimeBehavior=convertToNull");
        props.setUsername("{WM_CLOUD_MYSQL_USERNAME}");
        props.setDriverClass("com.mysql.jdbc.Driver");
        props.setDialect("com.wavemaker.runtime.data.dialect.MySQLDialect");
        return props;
    }
}
