package com.wavemaker.api.client;

import java.util.List;
import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.wavemaker.api.rest.IRestInput;
import com.wavemaker.api.rest.MultipartInput;
import com.wavemaker.api.rest.RestInputImpl;
import com.wavemaker.api.rest.models.RestResponse;
import com.wavemaker.api.rest.models.database.ApiResponse;
import com.wavemaker.api.rest.models.database.HrdbUser;
import com.wavemaker.api.wrapper.BooleanWrapper;

import static com.wavemaker.api.client.constants.DatabaseRuntimeUrlConstants.ALL_RECORDS_URL;
import static com.wavemaker.api.client.constants.DatabaseRuntimeUrlConstants.EXPORT_URL;
import static com.wavemaker.api.client.constants.DatabaseRuntimeUrlConstants.RECORD_BY_PK_COLUMN_URL;


/**
 * Thia class contains all client methods related to database runtime test cases.
 * Created by tejaswim on 9/27/2016.
 */
public class DatabaseRunTimeControllerClient extends RunTimeClient {

    private static final String RUNTIME_PROJECT_ID = "runtimeProjectId";
    private static final String PROJECT_NAME = "projectName";
    private static final String DB_NAME = "dbName";
    private static final String TABLE_NAME = "tableName";

    public List<HrdbUser> getAllUsers(String runtimeProjectId, String projectName, String dbName, String tableName) {
        String url = constructUrl(ALL_RECORDS_URL, new String[][]{{RUNTIME_PROJECT_ID, runtimeProjectId}, {PROJECT_NAME, projectName},
                {DB_NAME, dbName}, {TABLE_NAME, tableName}});
        ApiResponse<HrdbUser> response = get(url, new ParameterizedTypeReference<ApiResponse<HrdbUser>>() {
        });
        List<HrdbUser> hrdbUsers = response.getContent();
        return hrdbUsers;
    }

    public <T> ApiResponse<T> getAllRecords(String runtimeProjectId, String projectName, String dbName, String tableName, Class<T> t) {
        String url = constructUrl(ALL_RECORDS_URL, new String[][]{{RUNTIME_PROJECT_ID, runtimeProjectId}, {PROJECT_NAME, projectName},
                {DB_NAME, dbName}, {TABLE_NAME, tableName}});
        ApiResponse<T> response = getApiResponse(url, t);
        return response;
    }

    public <T> T insertRecord(String runtimeProjectId, String projectName, String dbName, String tableName, final T t) {
        return insertRecord(runtimeProjectId, projectName, dbName, tableName, t, null);
    }

    public <T> T insertRecord(String runtimeProjectId, String projectName, String dbName, String tableName, final T t, Map csrfHeader) {
        String url = constructUrl(ALL_RECORDS_URL, new String[][]{{RUNTIME_PROJECT_ID, runtimeProjectId}, {PROJECT_NAME, projectName},
                {DB_NAME, dbName}, {TABLE_NAME, tableName}});
        IRestInput payload = new RestInputImpl() {
            @Override
            public T getPayload() {
                return t;
            }
        };
        T response = (T) post(url, payload, t.getClass(), csrfHeader);
        return response;
    }

    public <T> T insertRecordWithMultipartData(String runtimeProjectId, String projectName, String dbName, String tableName, final T t) {
        String url = constructUrl(ALL_RECORDS_URL, new String[][]{{RUNTIME_PROJECT_ID, runtimeProjectId}, {PROJECT_NAME, projectName},
                {DB_NAME, dbName}, {TABLE_NAME, tableName}});
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("wm_data_json", t);
        multiValueMap.add("blobColumn", new ClassPathResource("/images/wmlogo.png"));
        T response = (T) post(url, new MultipartInput(multiValueMap), t.getClass());
        return response;
    }

    public <T> T updateRecordWithMultipartData(
            String runtimeProjectId, String projectName, String dbName, String tableName, String pkColumnValue, final T t) {
        String url = constructUrl(RECORD_BY_PK_COLUMN_URL,
                new String[][]{{RUNTIME_PROJECT_ID, runtimeProjectId}, {PROJECT_NAME, projectName},
                        {DB_NAME, dbName}, {TABLE_NAME, tableName}, {"pkColumnValue", pkColumnValue}});
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("wm_data_json", t);
        multiValueMap.add("blobColumn", new ClassPathResource("/images/chart.png"));
        T response = (T) post(url, new MultipartInput(multiValueMap), t.getClass());
        return response;
    }

    public <T> Boolean deleteRecord(
            String runtimeProjectId, String projectName, String dbName, String tableName, String pkColumnValue, final T t) {
        String url = constructUrl(RECORD_BY_PK_COLUMN_URL,
                new String[][]{{RUNTIME_PROJECT_ID, runtimeProjectId}, {PROJECT_NAME, projectName},
                        {DB_NAME, dbName}, {TABLE_NAME, tableName}, {"pkColumnValue", pkColumnValue}});
        IRestInput payload = new RestInputImpl() {
            @Override
            public T getPayload() {
                return t;
            }
        };
        return delete(url, payload, BooleanWrapper.class).getResult();
    }

    public RestResponse exportBlobData(String runtimeProjectId, String projectName, String dbName, String tableName, String exportType) {
        String url = constructUrl(EXPORT_URL, new String[][]{{RUNTIME_PROJECT_ID, runtimeProjectId}, {PROJECT_NAME, projectName},
                {DB_NAME, dbName}, {TABLE_NAME, tableName}, {"exportType", exportType}});
        return get(url);
    }
}
