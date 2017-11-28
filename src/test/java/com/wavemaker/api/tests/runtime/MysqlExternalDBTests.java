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
import com.wavemaker.studio.core.data.constants.DBType;
import com.wavemaker.studio.core.props.DBConnectionProps;
import com.wavemaker.studio.core.props.TableSelector;

import static com.wavemaker.api.constants.GroupNameConstants.*;

/**
 * Created by Tejaswi Maryala on 11/27/2017.
 */
public class MysqlExternalDBTests extends BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(HRDBTests.class);
    private final String TABLE_NAME = "AllTypes";
    private final String dbName = "AllTypesDBAutomation";
    private DatabaseRunTimeControllerClient dbRunTimeClient = new DatabaseRunTimeControllerClient();
    private String runtimeId;

    @BeforeClass
    public void importDB() {
        MySqlDBCreator mySqlDBCreator = new MySqlDBCreator(getMysqlExternalProps(getProjectDetails().getName(), dbName));
        runtimeId = mySqlDBCreator.createDBService(getProjectDetails());
    }

    @Test(groups = {RUNTIME, DATABASE, MYSQL_EXTERNAL, GET}, description = "Verifies if we are able to get all records with blob table")
    public void getAllRecords() {
        //Step 1
        List<AllTypes> response = dbRunTimeClient
                .getAllRecords(runtimeId, getProjectDetails().getName(), dbName, TABLE_NAME, AllTypes.class).getContent();
        Assert.assertNotNull(response, "no of records in the table should not be 0");
        logger.info("Get all users is successful");
    }

    @Test(groups = {RUNTIME, DATABASE, MYSQL_EXTERNAL, INSERT}, description = "Verifies if insertion is successful with blob table")
    public void insertBlobData() {
        AllTypes buildAllTypes = OracleDBObjectsBuilder.buildAllTypes();
        AllTypes response = dbRunTimeClient.insertRecordWithMultipartData(runtimeId, getProjectDetails().getName(), dbName, TABLE_NAME,
                buildAllTypes);
        Assert.assertNotNull(response, "no of records in the table should not be 0");
        logger.info("Data insertion with blob column is Successful with response {}", response.toString());
    }

    @Test(groups = {RUNTIME, DATABASE, MYSQL_EXTERNAL, EXPORT}, description = "Verifies if export data is successful with blob table")
    public void exportBlobData() {
        RestResponse response = dbRunTimeClient.exportBlobData(runtimeId, getProjectDetails().getName(), dbName, TABLE_NAME,
                "CSV");
        Assert.assertNotNull(response, "no of records in the table should not be 0");
        logger.info("Export to CSV is successful with response {}", response.toString());
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
