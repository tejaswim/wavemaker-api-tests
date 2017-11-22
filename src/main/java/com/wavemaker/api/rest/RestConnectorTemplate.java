package com.wavemaker.api.rest;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import com.wavemaker.api.rest.builder.RequestInfoBuilder;
import com.wavemaker.api.rest.models.RestRequestInfo;
import com.wavemaker.api.rest.models.RestResponse;


/**
 * Created by ArjunSahasranam on 4/2/16.
 */
public class RestConnectorTemplate {
    private RestConnector restConnector;
    private String url;

    public RestConnectorTemplate() {
        restConnector = new RestConnector();
    }


    private Map<String, Object> createHeaders(final String auth, String url, Map customHeaders) {
        Map<String, Object> headers = new HashMap<String, Object>();
        URL url1 = null;
        try {
            url1 = new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Url not valid", e);
        }
        headers.put("Host", url1.getHost());
        if (StringUtils.isNotBlank(auth))
            headers.put("Cookie", auth);
        if (customHeaders != null) {
            headers.putAll(customHeaders);
        }
        return headers;
    }

    public <T> ResponseEntity<T> execute(HttpMethod method, String auth, String url, IRestInput payload, Class<T> t) {
        final RestRequestInfo restRequestInfo = getRestRequestInfo(method, auth, url, payload);
        return restConnector.invokeRestCall(restRequestInfo, t);
    }

    public <T> ResponseEntity<T> execute(HttpMethod method, String auth, String url, IRestInput payload, Class<T> t, Map headers) {
        final RestRequestInfo restRequestInfo = getRestRequestInfo(method, auth, url, payload, headers);
        return restConnector.invokeRestCall(restRequestInfo, t);
    }

    public <T> ResponseEntity<T> execute(HttpMethod method, String auth, String url, IRestInput payload, ParameterizedTypeReference<T> t) {
        final RestRequestInfo restRequestInfo = getRestRequestInfo(method, auth, url, payload);
        return restConnector.invokeRestCall(restRequestInfo, t);
    }

    public <T> ResponseEntity<T> execute(
            HttpMethod method, String auth, String url, IRestInput payload, ParameterizedTypeReference<T> t, Map headers) {
        final RestRequestInfo restRequestInfo = getRestRequestInfo(method, auth, url, payload, headers);
        return restConnector.invokeRestCall(restRequestInfo, t);
    }

    public RestResponse execute(HttpMethod method, String auth, String url, IRestInput payload) {
        return this.execute(method, auth, url, payload, false, null);
    }

    public RestResponse execute(HttpMethod method, String auth, String url, IRestInput payload, boolean handleErrors) {
        return this.execute(method, auth, url, payload, handleErrors, null);
    }

    public RestResponse execute(HttpMethod method, String auth, String url, IRestInput payload, Map headers) {
        return this.execute(method, auth, url, payload, true, headers);
    }

    public RestResponse execute(HttpMethod method, String auth, String url, IRestInput payload, boolean handleErrors, Map headers) {
        RestRequestInfo restRequestInfo = getRestRequestInfo(method, auth, url, payload, headers);
        return restConnector.invokeRestCall(restRequestInfo, handleErrors);
    }

    private RestRequestInfo getRestRequestInfo(
            final HttpMethod method, final String auth, final String url, IRestInput restInputs) {
        return this.getRestRequestInfo(method, auth, url, restInputs, null);
    }

    private RestRequestInfo getRestRequestInfo(
            final HttpMethod method, final String auth, final String url, IRestInput restInputs, Map headers) {
        return RequestInfoBuilder.create().
                setRedirectEnabled(false).
                setHeaders(createHeaders(auth, url, headers)).
                setRequestBody(restInputs.getPayload()).
                setContentType(restInputs.getContentType()).
                setEndpointAddress(url).
                setMethod(method.name()).
                build();
    }
}
