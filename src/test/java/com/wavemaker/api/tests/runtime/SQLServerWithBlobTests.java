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
import com.wavemaker.api.tests.builder.DBObjectsBuilder;
import com.wavemaker.api.tests.core.BaseTest;
import com.wavemaker.api.tests.designtime.database.OtherDBService;
import com.wavemaker.studio.core.data.constants.DBType;
import com.wavemaker.studio.core.props.DBConnectionProps;
import com.wavemaker.studio.core.props.TableSelector;

/**
 * Created by Tejaswi Maryala on 11/17/2017.
 */
public class SQLServerWithBlobTests extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(SQLServerWithBlobTests.class);
    private final String TABLE_NAME = "AllTypes";
    private final String dbName = "DB123Test";
    private DatabaseRunTimeControllerClient dbRunTimeClient = new DatabaseRunTimeControllerClient();
    private String runtimeId;
    private AllTypes buildAllTypes = DBObjectsBuilder.buildAllTypes();

    @BeforeClass
    public void importSqlServerDB() {
        OtherDBService otherDBService = new OtherDBService(getSQLServerDBConnectionProps(getProjectDetails().getName()),
                "testdata/dbjars/sqljdbc4-4.0.jar");
        runtimeId = otherDBService.createDBService(getProjectDetails());
    }

    @Test(groups = {"Runtime", "Database", "sqlserver"}, description = "Verifies if we are able to get all records with blob table")
    public void getAllBlobData() {
        List<AllTypes> response = dbRunTimeClient
                .getAllRecords(runtimeId, getProjectDetails().getName(), dbName, TABLE_NAME, AllTypes.class).getContent();
        Assert.assertNotNull(response, "no of records in the table should not be 0");
        logger.info("Successful {}", response.toString());
    }

    @Test(groups = {"Runtime", "Database", "sqlserver"}, description = "Verifies if insertion is successful with blob table")
    public void insertBlobData() {
        AllTypes response = dbRunTimeClient.insertRecordWithMultipartData(runtimeId, getProjectDetails().getName(), dbName, TABLE_NAME,
                buildAllTypes);
        Assert.assertNotNull(response, "no of records in the table should not be 0");
        logger.info("Successful {}", response.toString());
    }

    @Test(groups = {"Runtime", "Database", "sqlserver"}, description = "Verifies if export data is successful with blob table")
    public void exportBlobData() {
        RestResponse response = dbRunTimeClient.exportBlobData(runtimeId, getProjectDetails().getName(), dbName, TABLE_NAME,
                "CSV");
        Assert.assertNotNull(response, "no of records in the table should not be 0");
        logger.info("Successful {}", response.toString());
    }

    @Test(groups = {"Runtime", "Database", "sqlserver"}, description = "Verifies if Updation is successful with blob table")
    public void updateBlobData() {
        AllTypes response = dbRunTimeClient.insertRecordWithMultipartData(runtimeId, getProjectDetails().getName(), dbName, TABLE_NAME,
                buildAllTypes);
        Assert.assertNotNull(response, "no of records in the table should not be 0");
        logger.info("Successful {}", response.toString());
        String verificationValue = buildAllTypes.getStringColumn();
        AllTypes buildAllTypes1 = DBObjectsBuilder.buildAllTypes(buildAllTypes.getPkId());
        AllTypes updatedResponse = dbRunTimeClient
                .updateRecordWithMultipartData(runtimeId, getProjectDetails().getName(), dbName, TABLE_NAME,
                        buildAllTypes.getPkId().toString(), buildAllTypes1);
        String verificationValueAftUpdate = buildAllTypes1.getStringColumn();
        Assert.assertFalse(verificationValue.equals(verificationValueAftUpdate), "Update is not successful");
        Assert.assertNotNull(updatedResponse, "no of records in the table should not be 0");
        logger.info("Successful {}", updatedResponse.toString());
    }

    @Test(groups = {"Runtime", "Database", "sqlserver"}, description = "Verifies if deletion is successful with blob table")
    public void DeleteBlobData() {
        AllTypes response = dbRunTimeClient.insertRecordWithMultipartData(runtimeId, getProjectDetails().getName(), dbName, TABLE_NAME,
                buildAllTypes);
        Assert.assertNotNull(response, "no of records in the table should not be 0");
        logger.info("Successful {}", response.toString());
        Boolean deleteResponse = dbRunTimeClient.deleteRecord(runtimeId, getProjectDetails().getName(), dbName, TABLE_NAME,
                buildAllTypes.getPkId().toString(), buildAllTypes);
        Assert.assertTrue(deleteResponse, "Delete is not successful");
        logger.info("Successful {}", deleteResponse.toString());
    }

    private DBConnectionProps getSQLServerDBConnectionProps(String projectName) {
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
