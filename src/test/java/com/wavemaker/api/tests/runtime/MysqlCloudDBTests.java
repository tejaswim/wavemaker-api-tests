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
import com.wavemaker.api.tests.builder.DBObjectsBuilder;
import com.wavemaker.api.tests.core.BaseTest;
import com.wavemaker.api.tests.designtime.database.MySqlDBCreator;
import com.wavemaker.api.utils.ApiUtils;
import com.wavemaker.studio.core.data.constants.DBType;
import com.wavemaker.studio.core.props.DBConnectionProps;
import com.wavemaker.studio.core.props.TableSelector;

/**
 * Created by Tejaswi Maryala on 11/27/2017.
 */
public class MysqlCloudDBTests extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(MysqlCloudDBTests.class);
    private DatabaseRunTimeControllerClient dbRunTimeClient = new DatabaseRunTimeControllerClient();
    private DatabaseServiceControllerClient dbServiceClient = new DatabaseServiceControllerClient();
    private String runtimeId;
    private String dbName;
    private final String TABLE_NAME = "AllTypes";
    private AllTypes buildAllTypes = DBObjectsBuilder.buildAllTypes();

    @BeforeClass
    public void importDB() {
        dbName = "alltypes";
        MySqlDBCreator mySqlDBCreator = new MySqlDBCreator(getMysqlCloudProps(getProjectDetails().getName(), dbName));
        File file = new File(ApiUtils.getSqlFilePath("alltypesDB.sql"));
        dbServiceClient.importSqlFile(getProjectDetails().getStudioProjectId(), "", file);
        runtimeId = mySqlDBCreator.createDBService(getProjectDetails());
    }

    @Test(groups = {"Runtime", "Database", "mysqlcloud"}, description = "Verifies if we are able to get all records with blob table")
    public void getAllRecords() {
        //Step 1
        List<HrdbUser> response = dbRunTimeClient.getAllUsers(runtimeId, getProjectDetails().getName(), dbName, TABLE_NAME);
        Assert.assertNotNull(response, "no of records in the table should not be 0");
        logger.info("Get all users is successful");
    }

    @Test(groups = {"Runtime", "Database", "mysqlcloud"}, description = "Verifies if insertion is successful with blob table")
    public void insertBlobData() {
        AllTypes response = dbRunTimeClient.insertRecordWithMultipartData(runtimeId, getProjectDetails().getName(), dbName, TABLE_NAME,
                buildAllTypes);
        Assert.assertNotNull(response, "no of records in the table should not be 0");
        logger.info("Successful {}", response.toString());
    }

    @Test(groups = {"Runtime", "Database", "mysqlcloud"}, description = "Verifies if export data is successful with blob table")
    public void exportBlobData() {
        RestResponse response = dbRunTimeClient.exportBlobData(runtimeId, getProjectDetails().getName(), dbName, TABLE_NAME,
                "CSV");
        Assert.assertNotNull(response, "no of records in the table should not be 0");
        logger.info("Successful {}", response.toString());
    }

    public static DBConnectionProps getMysqlCloudProps(String projectName, String dataModelName) {
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
