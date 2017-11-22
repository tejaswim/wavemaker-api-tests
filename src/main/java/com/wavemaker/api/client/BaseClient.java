package com.wavemaker.api.client;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.wavemaker.api.config.StudioTestConfig;
import com.wavemaker.api.manager.LoggerManager;
import com.wavemaker.api.manager.SecurityManager;
import com.wavemaker.api.rest.IRestInput;
import com.wavemaker.api.rest.RestConnectorTemplate;
import com.wavemaker.api.rest.RestInputImpl;
import com.wavemaker.api.rest.models.RestResponse;
import com.wavemaker.api.rest.models.database.ApiResponse;
import com.wavemaker.runtime.WMObjectMapper;

/**
 * Created by ArjunSahasranam on 5/2/16.
 */
public class BaseClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseClient.class);
    protected final RestConnectorTemplate restConnectorTemplate = new RestConnectorTemplate();
    private static final LoggerManager loggerManager = new LoggerManager();

    public BaseClient() {

    }

    public String constructUrl(String url, String[][] params) {
        String fullUrl = getBaseUrl() + url;
        for (int i = 0; i < params.length; i++) {
            final String key = params[i][0];
            final String value = params[i][1];
            fullUrl = StringUtils.replace(fullUrl, ":" + key, value);
        }
        return fullUrl;
    }

    public String constructUrl(String url) {
        String fullUrl = getBaseUrl() + url;
        return fullUrl;
    }

    public RestResponse execute(HttpMethod method, String endpointAddress, IRestInput restInput) {
        return this.execute(method, SecurityManager.getAuthCookie(), endpointAddress, restInput, true);
    }

    public RestResponse execute(HttpMethod method, String endpointAddress, IRestInput restInput, boolean handleErrors, Map headers) {
        return restConnectorTemplate.execute(method, SecurityManager.getAuthCookie(), endpointAddress, restInput, handleErrors, headers);
    }

    public RestResponse execute(HttpMethod method, String auth, String endpointAddress, IRestInput restInput, boolean handleErrors, Map headers) {
        return restConnectorTemplate.execute(method, auth, endpointAddress, restInput, handleErrors, headers);
    }

    /**
     * Tests which need RestResponse to check status code can use this method so that framework skips exception handling and termination of test case
     */
    public RestResponse execute(HttpMethod method, String endpointAddress, IRestInput restInput, boolean handleError) {
        return restConnectorTemplate.execute(method, SecurityManager.getAuthCookie(), endpointAddress, restInput, handleError);
    }

    public RestResponse execute(
            HttpMethod method, final String authCookie, String endpointAddress, IRestInput restInput) {
        return this.execute(method, authCookie, endpointAddress, restInput, true);
    }

    /**
     * Tests which need RestResponse to check status code can use this method so that framework skips exception handling and termination of test case
     */
    public RestResponse execute(
            HttpMethod method, final String authCookie, String endpointAddress, IRestInput restInput, boolean handleErrors) {
        return restConnectorTemplate.execute(method, authCookie, endpointAddress, restInput, handleErrors);
    }

    public <T> T execute(
            final HttpMethod method, final String authCookie, final String url, final
    RestInputImpl restInput, Class<T> t) {
        ResponseEntity<T> responseEntity = restConnectorTemplate.execute(method, authCookie, url, restInput, t);
        return returnResponseBody(responseEntity);
    }

    public RestResponse post(String endpointAddress, IRestInput restInput) {
        RestResponse restResponse = restConnectorTemplate
                .execute(HttpMethod.POST, SecurityManager.getAuthCookie(), endpointAddress, restInput);
        LOGGER.info(endpointAddress, restInput.getPayload(), restInput.getContentType());
        LOGGER.info(restResponse.toString());
        return restResponse;
    }

    public RestResponse get(String endpointAddress) {
        RestResponse restResponse = restConnectorTemplate
                .execute(HttpMethod.GET, SecurityManager.getAuthCookie(), endpointAddress, new RestInputImpl());
        return restResponse;
    }

    public RestResponse put(String endpointAddress, IRestInput restInput) {
        final RestResponse response = restConnectorTemplate
                .execute(HttpMethod.PUT, SecurityManager.getAuthCookie(), endpointAddress, restInput);
        LOGGER.info(endpointAddress, restInput.getPayload(), restInput.getContentType());
        LOGGER.info(response.toString());
        return response;
    }

    public <T> T put(String endpointAddress, IRestInput restInput, Class<T> t) {
        final ResponseEntity<T> responseEntity = restConnectorTemplate
                .execute(HttpMethod.PUT, SecurityManager.getAuthCookie(), endpointAddress, restInput, t);
        LOGGER.info(endpointAddress, restInput.getPayload(), restInput.getContentType());
        LOGGER.info(responseEntity.toString());
        return returnResponseBody(responseEntity);

    }

    public RestResponse delete(String endpointAddress) {
        final RestResponse response = restConnectorTemplate
                .execute(HttpMethod.DELETE, SecurityManager.getAuthCookie(), endpointAddress, new RestInputImpl());
        LOGGER.info(endpointAddress);
        LOGGER.info(response.toString());
        return response;
    }

    public <T> T execute(HttpMethod method, String endpointAddress, IRestInput restInput, Class<T> t) {
        final ResponseEntity<T> responseEntity = restConnectorTemplate
                .execute(method, SecurityManager.getAuthCookie(), endpointAddress, restInput, t);
        return returnResponseBody(responseEntity);
    }

    public <T> T post(String endpointAddress, IRestInput restInput, Class<T> t) {
        final ResponseEntity<T> responseEntity = restConnectorTemplate
                .execute(HttpMethod.POST, SecurityManager.getAuthCookie(), endpointAddress, restInput, t);
        return returnResponseBody(responseEntity);
    }

    public <T> ApiResponse<T> getApiResponse(String endpointAddress, Class<T> t) {
        final ResponseEntity<ApiResponse> responseEntity = restConnectorTemplate
                .execute(HttpMethod.GET, SecurityManager.getAuthCookie(), endpointAddress, new RestInputImpl(), ApiResponse.class);
        ApiResponse<T> response = returnResponseBody(responseEntity);
        List<T> content = response.getContent();
        content = convertToActualList(content, t);
        response.setContent(content);
        return response;
    }

    public <T> List<T> convertToActualList(final List<T> content, final Class<T> t) {
        List<T> list = new ArrayList<T>();
        for (Object object : content) {
            try {
                byte[] bytes = WMObjectMapper.getInstance().writeValueAsBytes(object);
                T tObject = WMObjectMapper.getInstance().readValue(bytes, t);
                list.add(tObject);
            } catch (Exception e) {
                throw new RuntimeException("Failed to convert object into type " + t.getClass().getName());
            }
        }
        return list;
    }

    public <T> T get(String endpointAddress, Class<T> t) {
        final ResponseEntity<T> responseEntity = restConnectorTemplate
                .execute(HttpMethod.GET, SecurityManager.getAuthCookie(), endpointAddress, new RestInputImpl(), t);
        return returnResponseBody(responseEntity);
    }

    public <T> T get(String endpointAddress, ParameterizedTypeReference<T> t) {
        final ResponseEntity<T> responseEntity = restConnectorTemplate
                .execute(HttpMethod.GET, SecurityManager.getAuthCookie(), endpointAddress, new RestInputImpl(), t);
        return returnResponseBody(responseEntity);
    }

    public <T> T delete(String endpointAddress, Class<T> t) {
        final ResponseEntity<T> responseEntity = restConnectorTemplate
                .execute(HttpMethod.DELETE, SecurityManager.getAuthCookie(), endpointAddress, new RestInputImpl(), t);
        return returnResponseBody(responseEntity);
    }

    public <T> T delete(String endpointAddress, IRestInput restInput, Class<T> t) {
        final ResponseEntity<T> responseEntity = restConnectorTemplate
                .execute(HttpMethod.DELETE, SecurityManager.getAuthCookie(), endpointAddress, restInput, t);
        return returnResponseBody(responseEntity);
    }

    protected <T> T returnResponseBody(ResponseEntity<T> responseEntity) {
        T body = responseEntity.getBody();
        return body;
    }

    private String getBaseUrl() {
        return StudioTestConfig.getInstance().getUrl();
    }
}
