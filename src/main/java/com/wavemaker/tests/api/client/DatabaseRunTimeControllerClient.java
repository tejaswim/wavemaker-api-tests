package com.wavemaker.tests.api.client;

import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.wavemaker.tests.api.rest.IRestInput;
import com.wavemaker.tests.api.rest.MultipartInput;
import com.wavemaker.tests.api.rest.RestInputImpl;
import com.wavemaker.tests.api.rest.models.RestResponse;
import com.wavemaker.tests.api.rest.models.database.ApiResponse;
import com.wavemaker.tests.api.rest.models.database.HrdbUser;
import com.wavemaker.tests.api.wrapper.BooleanWrapper;
import com.wavemaker.tests.api.client.constants.DatabaseRuntimeUrlConstants;


/**
 * Thia class contains all client methods related to database runtime test cases.
 * Created by tejaswim on 9/27/2016.
 */
public class DatabaseRunTimeControllerClient extends RunTimeClient {

    private static final String RUNTIME_PROJECT_ID = "runtimeProjectId";
    private static final String PROJECT_NAME = "projectName";
    private static final String DB_NAME = "dbName";
    private static final String TABLE_NAME = "tableName";
    private String appUrl;

    public DatabaseRunTimeControllerClient(final String appUrl) {
        this.appUrl = appUrl;
    }

    public List<HrdbUser> getAllUsers(String runtimeProjectId, String projectName, String dbName, String tableName) {
        String url = constructUrl(appUrl + DatabaseRuntimeUrlConstants.ALL_RECORDS_URL,
                new String[][]{{RUNTIME_PROJECT_ID, runtimeProjectId}, {PROJECT_NAME, projectName},
                        {DB_NAME, dbName}, {TABLE_NAME, tableName}});
        ApiResponse<HrdbUser> response = get(url, new ParameterizedTypeReference<ApiResponse<HrdbUser>>() {
        });
        return response.getContent();
    }

    public <T> ApiResponse<T> getAllRecords(String dbName, String tableName, Class<T> t) {
        String url = constructUrl(appUrl + DatabaseRuntimeUrlConstants.ALL_RECORDS_URL, new String[][]{{DB_NAME, dbName}, {TABLE_NAME, tableName}});
        return getApiResponse(url, t);
    }

    public <T> T insertRecord(String runtimeProjectId, String projectName, String dbName, String tableName, final T t) {
        return insertRecord(runtimeProjectId, projectName, dbName, tableName, t, null);
    }

    public <T> T insertRecord(String runtimeProjectId, String projectName, String dbName, String tableName, final T t, Map csrfHeader) {
        String url = constructUrl(appUrl + DatabaseRuntimeUrlConstants.ALL_RECORDS_URL,
                new String[][]{{RUNTIME_PROJECT_ID, runtimeProjectId}, {PROJECT_NAME, projectName},
                        {DB_NAME, dbName}, {TABLE_NAME, tableName}});
        IRestInput payload = new RestInputImpl() {
            @Override
            public T getPayload() {
                return t;
            }
        };
        return (T) post(url, payload, t.getClass(), csrfHeader);
    }

    public <T> T insertRecordWithMultipartData(String dbName, String tableName, final T t) {
        String url = constructUrl(appUrl + DatabaseRuntimeUrlConstants.ALL_RECORDS_URL, new String[][]{{DB_NAME, dbName}, {TABLE_NAME, tableName}});
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("wm_data_json", t);
        multiValueMap.add("blobColumn", new ClassPathResource("/images/wmlogo.png"));
        return (T) post(url, new MultipartInput(multiValueMap), t.getClass());
    }

    public <T> T updateRecordWithMultipartData(String dbName, String tableName, String pkColumnValue, final T t) {
        String url = constructUrl(appUrl + DatabaseRuntimeUrlConstants.RECORD_BY_PK_COLUMN_URL, new String[][]{{DB_NAME, dbName},
                {TABLE_NAME, tableName}, {"pkColumnValue", pkColumnValue}});
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("wm_data_json", t);
        multiValueMap.add("blobColumn", new ClassPathResource("/images/chart.png"));
        return (T) post(url, new MultipartInput(multiValueMap), t.getClass());
    }

    public <T> Boolean deleteRecord(String dbName, String tableName, String pkColumnValue) {
        String url = constructUrl(appUrl + DatabaseRuntimeUrlConstants.RECORD_BY_PK_COLUMN_URL,
                new String[][]{{DB_NAME, dbName}, {TABLE_NAME, tableName}, {"pkColumnValue", pkColumnValue}});
        return delete(url, BooleanWrapper.class).getResult();
    }

    public RestResponse exportBlobData(String dbName, String tableName, String exportType) {
        String url = constructUrl(appUrl + DatabaseRuntimeUrlConstants.EXPORT_URL,
                new String[][]{{DB_NAME, dbName}, {TABLE_NAME, tableName}, {"exportType", exportType}});
        return get(url);
    }

    public <T> T getRecordByPkColumn(String dbName, String tableName, Class<T> klass, String pkColumnValue) {
        String url = constructUrl(appUrl + DatabaseRuntimeUrlConstants.RECORD_BY_PK_COLUMN_URL,
                new String[][]{{DB_NAME, dbName}, {TABLE_NAME, tableName}, {"pkColumnValue", pkColumnValue}});
        T response = get(url, klass);
        return response;
    }
}
