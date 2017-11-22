package com.wavemaker.api.tests.runtime;

import java.util.ArrayList;
import java.util.List;

import org.opensaml.util.resource.ResourceException;
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
public class OracleWithBlobTests extends BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(OracleWithBlobTests.class);
    private DatabaseRunTimeControllerClient dbRunTimeClient = new DatabaseRunTimeControllerClient();
    private String runtimeId;
    private AllTypes buildAllTypes = DBObjectsBuilder.buildAllTypes();

    @BeforeClass
    public void importOracleDB() {
        OtherDBService otherDBService = new OtherDBService(getOracleDBConnectionProps(getProjectDetails().getName()),
                "dbjars/ojdbc6-11.2.0.jar");
        runtimeId = otherDBService.createDBService(getProjectDetails());
    }

    @Test(groups = {"Runtime", "Database","testgroup"}, description = "Verifies if we are able to get all records with blob table")
    public void getAllBlobData() {
        List<AllTypes> response = dbRunTimeClient
                .getAllRecords(runtimeId, getProjectDetails().getName(), "WMSTUDIO", "AllTypes", AllTypes.class).getContent();
        Assert.assertNotNull(response, "no of records in the table should not be 0");
        logger.info("Successful {}", response.toString());
    }

    @Test(groups = {"Runtime", "Database"}, description = "Verifies if insertion is successful with blob table")
    public void insertBlobData() {
        AllTypes response = dbRunTimeClient.insertRecordWithMultipartData(runtimeId, getProjectDetails().getName(), "WMSTUDIO", "AllTypes",
                buildAllTypes);
        Assert.assertNotNull(response, "no of records in the table should not be 0");
        logger.info("Successful {}", response.toString());
    }

    @Test(groups = {"Runtime", "Database"}, description = "Verifies if insertion is successful with blob table")
    public void exportBlobData() {
        RestResponse response = dbRunTimeClient.exportBlobData(runtimeId, getProjectDetails().getName(), "WMSTUDIO", "AllTypes",
                "CSV");
        Assert.assertNotNull(response, "no of records in the table should not be 0");
        logger.info("Successful {}", response.toString());
    }

    @Test(groups = {"Runtime", "Database"}, description = "Verifies if Updation is successful with blob table")
    public void updateBlobData() {
        AllTypes response = dbRunTimeClient.insertRecordWithMultipartData(runtimeId, getProjectDetails().getName(), "WMSTUDIO", "AllTypes",
                buildAllTypes);
        Assert.assertNotNull(response, "no of records in the table should not be 0");
        logger.info("Successful {}", response.toString());
        String verificationValue = buildAllTypes.getStringColumn();
        AllTypes buildAllTypes1 = DBObjectsBuilder.buildAllTypes(buildAllTypes.getPkId());
        AllTypes updatedResponse = dbRunTimeClient
                .updateRecordWithMultipartData(runtimeId, getProjectDetails().getName(), "WMSTUDIO", "AllTypes",
                        buildAllTypes.getPkId().toString(), buildAllTypes1);
        String verificationValueAftUpdate = buildAllTypes1.getStringColumn();
        Assert.assertFalse(verificationValue.equals(verificationValueAftUpdate),"Update is not successful");
        Assert.assertNotNull(updatedResponse, "no of records in the table should not be 0");
        logger.info("Successful {}", updatedResponse.toString());
    }

    @Test(groups = {"Runtime", "Database"}, description = "Verifies if deletion is successful with blob table")
    public void DeleteBlobData() {
        AllTypes response = dbRunTimeClient.insertRecordWithMultipartData(runtimeId, getProjectDetails().getName(), "WMSTUDIO", "AllTypes",
                buildAllTypes);
        Assert.assertNotNull(response, "no of records in the table should not be 0");
        logger.info("Successful {}", response.toString());
        Boolean deleteResponse = dbRunTimeClient.deleteRecord(runtimeId, getProjectDetails().getName(), "WMSTUDIO", "AllTypes",
                        buildAllTypes.getPkId().toString(), buildAllTypes);
        Assert.assertTrue(deleteResponse,"Delete is not successful");
        logger.info("Successful {}", deleteResponse.toString());
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
