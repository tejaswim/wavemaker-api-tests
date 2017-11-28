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
import com.wavemaker.api.rest.models.database.postgres.AllTypes;
import com.wavemaker.api.tests.builder.PostgresDBObjectsBuilder;
import com.wavemaker.api.tests.core.BaseTest;
import com.wavemaker.api.tests.designtime.database.MySqlDBCreator;
import com.wavemaker.studio.core.data.constants.DBType;
import com.wavemaker.studio.core.props.DBConnectionProps;
import com.wavemaker.studio.core.props.TableSelector;

import static com.wavemaker.api.constants.GroupNameConstants.*;

/**
 * Created by Tejaswi Maryala on 11/17/2017.
 */
public class PostgressqlWithBlobTests extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(PostgressqlWithBlobTests.class);
    private final String TABLE_NAME = "AllTypes";
    private final String dbName = "viewsdb";
    private DatabaseRunTimeControllerClient dbRunTimeClient = new DatabaseRunTimeControllerClient();
    private String runtimeId;

    @BeforeClass
    public void importPostgresSQLDB() {
        MySqlDBCreator mySqlDBCreator = new MySqlDBCreator(getPostgressqlDBConnectionProps(getProjectDetails().getName()));
        runtimeId = mySqlDBCreator.createDBService(getProjectDetails());
    }

    @Test(groups = {RUNTIME, DATABASE, POSTGRES_SQL, GET}, description = "Verifies if we are able to get all records with blob table")
    public void getAllBlobData() {
        List<AllTypes> response = dbRunTimeClient
                .getAllRecords(runtimeId, getProjectDetails().getName(), dbName, TABLE_NAME, AllTypes.class).getContent();
        Assert.assertNotNull(response, "no of records in the table should not be 0");
        logger.info("Successful {}", response.toString());
    }

    @Test(groups = {RUNTIME, DATABASE, POSTGRES_SQL, INSERT}, description = "Verifies if insertion is successful with blob table")
    public void insertBlobData() {
        AllTypes buildAllTypes = PostgresDBObjectsBuilder.buildAllTypes();
        AllTypes response = dbRunTimeClient.insertRecordWithMultipartData(runtimeId, getProjectDetails().getName(), dbName, TABLE_NAME,
                buildAllTypes);
        Assert.assertNotNull(response, "no of records in the table should not be 0");
        logger.info("Data insertion with blob column is Successful with response {}", response.toString());
    }

    @Test(groups = {RUNTIME, DATABASE, POSTGRES_SQL, EXPORT}, description = "Verifies if export data is successful with blob table")
    public void exportBlobData() {
        RestResponse response = dbRunTimeClient.exportBlobData(runtimeId, getProjectDetails().getName(), dbName, TABLE_NAME,
                "CSV");
        Assert.assertNotNull(response, "no of records in the table should not be 0");
        logger.info("Export to CSV is successful with response {}", response.toString());
    }

    @Test(groups = {RUNTIME, DATABASE, POSTGRES_SQL, UPDATE}, description = "Verifies if Updation is successful with blob table")
    public void updateBlobData() {
        AllTypes buildAllTypes = PostgresDBObjectsBuilder.buildAllTypes();
        AllTypes response = dbRunTimeClient.insertRecordWithMultipartData(runtimeId, getProjectDetails().getName(), dbName, TABLE_NAME,
                buildAllTypes);
        Assert.assertNotNull(response, "no of records in the table should not be 0");
        logger.info("Successful {}", response.toString());
        String verificationValue = buildAllTypes.getStringColumn();
        AllTypes buildAllTypes1 = PostgresDBObjectsBuilder.buildAllTypes(buildAllTypes.getId());
        AllTypes updatedResponse = dbRunTimeClient
                .updateRecordWithMultipartData(runtimeId, getProjectDetails().getName(), dbName, TABLE_NAME,
                        buildAllTypes.getId().toString(), buildAllTypes1);
        String verificationValueAftUpdate = buildAllTypes1.getStringColumn();
        Assert.assertFalse(verificationValue.equals(verificationValueAftUpdate), "Update is not successful");
        Assert.assertNotNull(updatedResponse, "no of records in the table should not be 0");
        logger.info("Data update with blob column is Successful with response {}", updatedResponse.toString());
    }

    @Test(groups = {RUNTIME, DATABASE, POSTGRES_SQL, DELETE}, description = "Verifies if deletion is successful with blob table")
    public void DeleteBlobData() {
        AllTypes buildAllTypes = PostgresDBObjectsBuilder.buildAllTypes();
        AllTypes response = dbRunTimeClient.insertRecordWithMultipartData(runtimeId, getProjectDetails().getName(), dbName, TABLE_NAME,
                buildAllTypes);
        Assert.assertNotNull(response, "no of records in the table should not be 0");
        logger.info("Successful {}", response.toString());
        Boolean deleteResponse = dbRunTimeClient.deleteRecord(runtimeId, getProjectDetails().getName(), dbName, TABLE_NAME,
                buildAllTypes.getId().toString(), buildAllTypes);
        Assert.assertTrue(deleteResponse, "Delete is not successful");
        logger.info("Data deletion with blob column is Successful with response {}", deleteResponse.toString());
    }

    private DBConnectionProps getPostgressqlDBConnectionProps(String projectName) {
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
        dbConnectionProps.setUsername("AllTypes");
        dbConnectionProps.setPassword("wavemaker");
        dbConnectionProps.setUrl("jdbc:postgresql://ec2-54-87-2-36.compute-1.amazonaws.com:5432/" + dbName);
        dbConnectionProps.setDialect("com.wavemaker.runtime.data.dialect.WMPostgresSQLDialect");
        dbConnectionProps.setDriverClass("org.postgresql.Driver");
        dbConnectionProps.setReadOnly(true);
        return dbConnectionProps;
    }
}
