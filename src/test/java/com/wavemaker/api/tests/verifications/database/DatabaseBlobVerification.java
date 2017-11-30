package com.wavemaker.api.tests.verifications.database;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import com.wavemaker.api.client.DatabaseRunTimeControllerClient;
import com.wavemaker.api.rest.models.RestResponse;
import com.wavemaker.api.tests.runtime.OracleWithBlobTests;

/**
 * Created by Tejaswi Maryala on 11/29/2017.
 */
public class DatabaseBlobVerification {

    private static final Logger logger = LoggerFactory.getLogger(OracleWithBlobTests.class);
    private DatabaseRunTimeControllerClient dbRunTimeClient = null;
    private String dbName;
    private String tableName;
    private Class tableClass;

    public DatabaseBlobVerification(final String runtimeUrl, final String dbName, final String tableName, final Class tableClass) {
        dbRunTimeClient = new DatabaseRunTimeControllerClient(runtimeUrl);
        this.dbName = dbName;
        this.tableName = tableName;
        this.tableClass = tableClass;
    }

    public <T> int verifyInsertAndGetBlobData(final T t, final String pkId) {

        //Get all the records in given table
        List<T> blobData = getBlobData(dbName, tableName, tableClass);
        int countBeforeInsert = blobData.size();

        //Inserted given input into required table
        T insertedData = insertBlobInput(dbName, tableName, t);

        //Get records count after insertion of data
        List<T> updatedBlobData = getBlobData(dbName, tableName, tableClass);
        int countAfterInsert = blobData.size();

        //Verifies if count diff is one after data insertion
        Assert.assertTrue(countAfterInsert - countBeforeInsert == 1, "Data Insertion failed");

        //Get inserted data by using getById api
        T blobDataById = getBlobDataById(dbName, tableName, (Class<T>) tableClass, pkId);

        //Verify if data given is inserted
        Assert.assertTrue(insertedData.equals(blobDataById), "Data Insertion mismatch");
        logger.info("Data insertion with blob column is Successful with response {}", updatedBlobData.toString());

        return countAfterInsert;
    }

    public void verifyExportedBlobData(final String exportType) {
        exportData(dbName, tableName, "CSV");
    }

    public <T> void verifyUpdateBlobData(final T t1, final T t2, final String pkId) {
        T insertedBlobInput = insertBlobInput(dbName, tableName, t1);
        T updatedBlobInput = updateBlobInput(dbName, tableName, pkId, insertedBlobInput, t2);

        //Get Updated data by using getById api
        T blobDataById = getBlobDataById(dbName, tableName, (Class<T>) tableClass, pkId);

        //Verify if data given is updated
        Assert.assertTrue(updatedBlobInput.equals(blobDataById), "Data Insertion mismatch");
        logger.info("Data Update with blob column is Successful with response {}", updatedBlobInput.toString());
    }

    public <T> void verifyInsertAndDeleteBlobData(final T t, final String pkId) {
        int countAftInsert = verifyInsertAndGetBlobData(t, pkId);

        //Delete inserted data
        Boolean deleteBlobInput = deleteBlobInput(dbName, tableName, pkId);
        Assert.assertTrue(deleteBlobInput, "Delete data is not successful");

        //Get all the records in given table
        List<T> blobData = getBlobData(dbName, tableName, tableClass);
        int countAfterDeletion = blobData.size();
        Assert.assertTrue(countAftInsert - 1 == countAfterDeletion, "Deletion of record is not successful");
        Assert.assertFalse(blobData.contains(t), "Deletion of record is not successful");
    }

    public void exportData(final String dbName, final String tableName, final String exportType) {
        RestResponse response = dbRunTimeClient.exportBlobData(dbName, tableName, exportType);
        Assert.assertNotNull(response, "no of records in the table should not be 0");
        logger.info("Export to " + exportType + " is successful with response {}", response.toString());
    }

    public <T> T updateBlobInput(final String dbName, final String tableName, final String pkId, final T oldValue, final T newValue) {
        T updatedResponse = dbRunTimeClient.updateRecordWithMultipartData(dbName, tableName, pkId, newValue);
        Assert.assertFalse(oldValue.equals(newValue), "Update is not successful");
        Assert.assertNotNull(updatedResponse, "no of records in the table should not be 0");
        logger.info("Data update with blob column is Successful with response {}", updatedResponse.toString());
        return updatedResponse;
    }

    public Boolean deleteBlobInput(final String dbName, String tableName, final String pkId) {
        Boolean deleteResponse = dbRunTimeClient.deleteRecord(dbName, tableName, pkId);
        Assert.assertTrue(deleteResponse, "Delete is not successful");
        logger.info("Data deletion with blob column is Successful with response {}", deleteResponse.toString());
        return deleteResponse;
    }

    public <T> T insertBlobInput(final String dbName, final String tableName, final T t) {
        T response = dbRunTimeClient.insertRecordWithMultipartData(dbName, tableName, t);
        Assert.assertNotNull(response, "no of records in the table should not be 0");
        return response;
    }

    public <T> List<T> getBlobData(final String dbName, final String tableName, final Class<T> t) {
        List<T> response = dbRunTimeClient.getAllRecords(dbName, tableName, t).getContent();
        Assert.assertNotNull(response, "no of records in the table should not be 0");
        logger.info("Get all records is successful with response", response.toString());
        return response;
    }

    public <T> T getBlobDataById(final String dbName, final String tableName, final Class<T> t, final String pkId) {
        T recordByPkColumn = dbRunTimeClient.getRecordByPkColumn(dbName, tableName, t, pkId);
        Assert.assertNotNull(recordByPkColumn, "no of records in the table should not be 0");
        logger.info("Get all records is successful with response", recordByPkColumn.toString());
        return recordByPkColumn;
    }
}
