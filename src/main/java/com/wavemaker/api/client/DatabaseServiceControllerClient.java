package com.wavemaker.api.client;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.FileSystemResource;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.wavemaker.api.rest.IRestInput;
import com.wavemaker.api.rest.MultipartInput;
import com.wavemaker.api.rest.RestInputImpl;
import com.wavemaker.api.wrapper.StringWrapper;
import com.wavemaker.studio.core.data.constants.DBType;
import com.wavemaker.studio.core.data.model.DataModel;
import com.wavemaker.studio.core.props.DBConnectionProps;
import com.wavemaker.studio.core.props.TableSelector;

/**
 * Created by Tejaswi Maryala on 11/17/2017.
 */
public class DatabaseServiceControllerClient extends BaseClient {

    private final String STUDIO_PROJECT_ID = "studioProjectId";
    private final String STUDIO_SERVICES_URL = "/studio/services/projects/:studioProjectId";
    private final String verifyJarUrl = STUDIO_SERVICES_URL + "/database/testDriver?dbType=:dbType";
    private final String importDbUrl = STUDIO_SERVICES_URL + "/database/services/import";
    private final String sampleDbConnectionPropsUrl = STUDIO_SERVICES_URL + "/database/sample/connectionProps";
    private final String uploadLibUrl = STUDIO_SERVICES_URL + "/resources/content/lib";
    private final String importSqlFileUrl = "/deployment-cloud/rest/shell/db/import?db=";

    public String verifyJar(String studioProjectId, String dbType) {
        String url = constructUrl(getBaseUrl() + verifyJarUrl,
                new String[][]{{STUDIO_PROJECT_ID, studioProjectId}, {"dbType", dbType}});
        StringWrapper result = get(url, StringWrapper.class);
        return result.getResult();
    }

    public Object uploadJar(String studioProjectId, File file) {
        String url = constructUrl(getBaseUrl() + uploadLibUrl, new String[][]{{STUDIO_PROJECT_ID, studioProjectId}});
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("file", new FileSystemResource(file));
        Object result = post(url, new MultipartInput(multiValueMap), Object.class);
        return result;
    }


    public DataModel importDatabase(String studioProjectId, final DBConnectionProps connectionProps) {
        String url = constructUrl(getBaseUrl() + importDbUrl, new String[][]{{STUDIO_PROJECT_ID, studioProjectId}});
        IRestInput payload = new RestInputImpl() {
            @Override
            public DBConnectionProps getPayload() {
                return connectionProps;
            }
        };
        return post(url, payload, DataModel.class);
    }

    public DBConnectionProps getSampleDbConnectionProps(String studioProjectId) {
        String url = constructUrl(getBaseUrl() + sampleDbConnectionPropsUrl, new String[][]{{STUDIO_PROJECT_ID, studioProjectId}});
        DBConnectionProps dbConnectionProps = get(url, DBConnectionProps.class);
        return dbConnectionProps;
    }

    public Object importSqlFile(String studioProjectId, String dbName, final File file) {
        String url = constructUrl(getBaseUrl() + importSqlFileUrl, new String[][]{{STUDIO_PROJECT_ID, studioProjectId}, {"dbName",
                dbName}});
        MultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
        multiValueMap.add("file", new FileSystemResource(file));
//        multiValueMap.add("properties", new FileSystemResource(file));
        Object result = post(url, new MultipartInput(multiValueMap), Object.class);
        return result;
    }
}
