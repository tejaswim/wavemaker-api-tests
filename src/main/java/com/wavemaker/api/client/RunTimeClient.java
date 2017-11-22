package com.wavemaker.api.client;

import java.util.Map;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.wavemaker.api.manager.RunTimeSecurityManager;
import com.wavemaker.api.rest.IRestInput;
import com.wavemaker.api.rest.RestInputImpl;
import com.wavemaker.api.rest.models.RestResponse;

/**
 * Created by Prithvi Medavaram on 12/10/16.
 */
public class RunTimeClient extends BaseClient {

    public RestResponse post(String endpointAddress, IRestInput restInput) {
        RestResponse restResponse = restConnectorTemplate
                .execute(HttpMethod.POST, RunTimeSecurityManager.getAuthCookie(), endpointAddress, restInput);
        return restResponse;
    }
    
    public RestResponse get(String endpointAddress) {
        RestResponse restResponse = restConnectorTemplate
                .execute(HttpMethod.GET, RunTimeSecurityManager.getAuthCookie(), endpointAddress, new RestInputImpl());
        return restResponse;
    }

    public RestResponse put(String endpointAddress, IRestInput restInput) {
        return restConnectorTemplate
                .execute(HttpMethod.PUT, RunTimeSecurityManager.getAuthCookie(), endpointAddress, restInput);
    }

    public <T> T put(String endpointAddress, IRestInput restInput, Class<T> t) {
        final ResponseEntity<T> responseEntity = restConnectorTemplate
                .execute(HttpMethod.PUT, RunTimeSecurityManager.getAuthCookie(), endpointAddress, restInput, t);
        return returnResponseBody(responseEntity);

    }

    public RestResponse delete(String endpointAddress) {
        return restConnectorTemplate
                .execute(HttpMethod.DELETE, RunTimeSecurityManager.getAuthCookie(), endpointAddress, new RestInputImpl());
    }

    public <T> T post(String endpointAddress, IRestInput restInput, Class<T> t) {
        final ResponseEntity<T> responseEntity = restConnectorTemplate
                .execute(HttpMethod.POST, RunTimeSecurityManager.getAuthCookie(), endpointAddress, restInput, t);
        return returnResponseBody(responseEntity);
    }

    public <T> T post(String endpointAddress, IRestInput restInput, Class<T> t, Map headers) {
        final ResponseEntity<T> responseEntity = restConnectorTemplate
                .execute(HttpMethod.POST, RunTimeSecurityManager.getAuthCookie(), endpointAddress, restInput, t, headers);
        return returnResponseBody(responseEntity);
    }

    public <T> T get(String endpointAddress, Class<T> t) {
        final ResponseEntity<T> responseEntity = restConnectorTemplate
                .execute(HttpMethod.GET, RunTimeSecurityManager.getAuthCookie(), endpointAddress, new RestInputImpl(), t);
        return returnResponseBody(responseEntity);
    }

    public <T> T get(String endpointAddress, ParameterizedTypeReference<T> t) {
        final ResponseEntity<T> responseEntity = restConnectorTemplate
                .execute(HttpMethod.GET, RunTimeSecurityManager.getAuthCookie(), endpointAddress, new RestInputImpl(), t);
        return returnResponseBody(responseEntity);
    }

    public <T> T get(String endpointAddress, ParameterizedTypeReference<T> t, Map header) {
        final ResponseEntity<T> responseEntity = restConnectorTemplate
                .execute(HttpMethod.GET, RunTimeSecurityManager.getAuthCookie(), endpointAddress, new RestInputImpl(), t,header);
        return returnResponseBody(responseEntity);
    }

    public <T> T delete(String endpointAddress, Class<T> t) {
        final ResponseEntity<T> responseEntity = restConnectorTemplate
                .execute(HttpMethod.DELETE, RunTimeSecurityManager.getAuthCookie(), endpointAddress, new RestInputImpl(), t);
        return returnResponseBody(responseEntity);
    }

    public <T> T delete(String endpointAddress, IRestInput restInput, Class<T> t) {
        final ResponseEntity<T> responseEntity = restConnectorTemplate
                .execute(HttpMethod.DELETE, RunTimeSecurityManager.getAuthCookie(), endpointAddress, restInput, t);
        return returnResponseBody(responseEntity);
    }

}
