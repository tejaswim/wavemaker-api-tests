/**
 * Copyright © 2015 WaveMaker, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wavemaker.tests.api.rest;

import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ssl.AllowAllHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import com.wavemaker.tests.api.exception.ResourceNotFoundException;
import com.wavemaker.tests.api.exception.RestFailureException;
import com.wavemaker.tests.api.exception.WMRuntimeException;
import com.wavemaker.tests.api.rest.models.RestRequestInfo;
import com.wavemaker.tests.api.rest.models.RestResponse;
import com.wavemaker.tests.api.rest.util.RestUtil;
import com.wavemaker.tests.api.rest.util.SSLUtils;
import com.wavemaker.tests.api.rest.util.WMRuntimeUtils;


/**
 * @author Uday Shankar
 */


public class RestConnector {
    private static final String CONTENT_TYPE = "Content-Type";
    private static final Logger LOGGER = LoggerFactory.getLogger(RestConnector.class);

    private final X509HostnameVerifier hostnameVerifier = new AllowAllHostnameVerifier();

    /**
     * BaseClient methods can use this method if they need exception handling and error logging at framework level
     */
    public RestResponse invokeRestCall(RestRequestInfo restRequestInfo) {
        return this.invokeRestCall(restRequestInfo, true);
    }

    /**
     * BaseClient methods can use this method so that tests are not terminated due to exceptions
     */
    public RestResponse invokeRestCall(RestRequestInfo restRequestInfo, boolean handleError) {
        final HttpClientContext httpClientContext = HttpClientContext.create();
        ResponseEntity<String> responseEntity = getResponseEntity(restRequestInfo,
                httpClientContext, String.class, handleError);

        RestResponse restResponse = new RestResponse();
        restResponse.setResponseBody(responseEntity.getBody());
        restResponse.setStatusCode(responseEntity.getStatusCode().value());
        // Converting form Cookie to BasicClientCookie.
        final List<Cookie> cookies = httpClientContext.getCookieStore().getCookies();
        List<BasicClientCookie> clientCookies = new ArrayList<BasicClientCookie>();
        for (Cookie cookie : cookies) {
            clientCookies.add((BasicClientCookie) cookie);
        }
        restResponse.setCookies(clientCookies);
        Map<String, List<String>> responseHeaders = new HashMap<String, List<String>>();
        HttpHeaders httpHeaders = responseEntity.getHeaders();
        for (String responseHeaderKey : httpHeaders.keySet()) {
            responseHeaders.put(responseHeaderKey, httpHeaders.get(responseHeaderKey));
        }
        MediaType mediaType = responseEntity.getHeaders().getContentType();
        if (mediaType != null) {
            String outputContentType = mediaType.toString();
            if (outputContentType.contains(";")) {
                outputContentType = outputContentType.substring(0, outputContentType.indexOf(";"));
            }
            restResponse.setContentType(outputContentType);
        }
        restResponse.setResponseHeaders(responseHeaders);
        return restResponse;
    }

    public <T> ResponseEntity<T> invokeRestCall(RestRequestInfo restRequestInfo, Class<T> t) {

        final HttpClientContext httpClientContext = HttpClientContext.create();
        return getResponseEntity(restRequestInfo, httpClientContext, t, true);
    }

    public <T> ResponseEntity<T> invokeRestCall(RestRequestInfo restRequestInfo, ParameterizedTypeReference<T> t) {

        final HttpClientContext httpClientContext = HttpClientContext.create();
        return getResponseEntity(restRequestInfo, httpClientContext, t, true);
    }

    private <T> ResponseEntity<T> getResponseEntity(
            final RestRequestInfo restRequestInfo, final HttpClientContext
            httpClientContext, Object type, boolean handleError) {

        // equivalent to "http.protocol.handle-redirects", false
        RequestConfig requestConfig = RequestConfig.custom().setRedirectsEnabled(restRequestInfo.isRedirectEnabled())
                .build();

        HttpMethod httpMethod = HttpMethod.valueOf(restRequestInfo.getMethod());
        if (httpMethod == null) {
            throw new IllegalArgumentException("Invalid method value [" + restRequestInfo.getMethod() + "]");
        }

        // Creating HttpClientBuilder and setting Request Config.
        HttpClientBuilder httpClientBuilder = HttpClients.custom();
        httpClientBuilder.setDefaultRequestConfig(requestConfig);

        String endpointAddress = URLDecoder.decode(restRequestInfo.getEndpointAddress());
        if (endpointAddress.startsWith("https")) {
            httpClientBuilder.setSSLSocketFactory(
                    new SSLConnectionSocketFactory(SSLUtils.getAllTrustedCertificateSSLContext(), hostnameVerifier));
        }

        CloseableHttpClient httpClient = httpClientBuilder.build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(
                httpClient) {
            @Override
            protected HttpContext createHttpContext(final HttpMethod httpMethod, final URI uri) {
                return httpClientContext;
            }
        };
        MultiValueMap headersMap = new LinkedMultiValueMap();

        //set headers
        Map<String, Object> headers = restRequestInfo.getHeaders();
        if (headers != null && !headers.isEmpty()) {
            for (Map.Entry<String, Object> entry : headers.entrySet()) {
                String[] stringList = RestUtil.getStringList(entry.getValue());
                for (String str : stringList) {
                    headersMap.add(entry.getKey(), str);
                }
            }
        }

        String contentType = restRequestInfo.getContentType();
        if (!StringUtils.isBlank(contentType)) {
            headersMap.add(CONTENT_TYPE, contentType);
        }
        RestTemplate wmRestTemplate = new RestTemplate(WMRuntimeUtils.getMessageConverters());
        wmRestTemplate.setRequestFactory(factory);
        wmRestTemplate.setErrorHandler(getExceptionHandler(restRequestInfo, handleError));
        HttpEntity requestEntity;
        if (HttpMethod.GET != httpMethod) {
            if (restRequestInfo.getRequestBody() instanceof Resource) {
                MultiValueMap<String, Object> formaData = new LinkedMultiValueMap<String, Object>();
                formaData.add("file", restRequestInfo.getRequestBody());

                requestEntity = new HttpEntity(formaData, headersMap);
            } else {
                requestEntity = new HttpEntity(restRequestInfo.getRequestBody(), headersMap);
            }
        } else {
            requestEntity = new HttpEntity(headersMap);
        }
        ResponseEntity<T> responseEntity;
        if (type instanceof Class) {
            Class aClass = (Class) type;
            responseEntity = wmRestTemplate
                    .exchange(endpointAddress, httpMethod, requestEntity, aClass);
        } else if (type instanceof ParameterizedTypeReference) {
            ParameterizedTypeReference parameterizedTypeReference = (ParameterizedTypeReference) type;
            responseEntity = wmRestTemplate.exchange(endpointAddress, httpMethod, requestEntity, parameterizedTypeReference);
        } else {
            throw new IllegalStateException();
        }
        T body = responseEntity.getBody();
        LOGGER.info("Response entity for request {} with method {} is {}", endpointAddress, httpMethod,
                (body != null) ? body.toString() : "null");
        return responseEntity;
    }

    private ResponseErrorHandler getExceptionHandler(RestRequestInfo restRequestInfo, boolean handleError) {
        return new WMRestServicesErrorHandler(restRequestInfo, handleError);
    }

    private <T> ResponseEntity<T> logApiFailure(
            final HttpMethod httpMethod, final String endpointAddress, final int statusCode,
            final String responseBody, final HttpStatusCodeException e) {
        LOGGER.info("Request for {} with method {} has failed with status code {} and response body {}", endpointAddress, httpMethod,
                statusCode, responseBody);
        if (HttpStatus.NOT_FOUND.equals(statusCode)) {
            throw new ResourceNotFoundException("Status code is " + statusCode, e);
        } else if (HttpStatus.UNAUTHORIZED.equals(statusCode)) {
            throw new WMRuntimeException("Status code is " + statusCode, e);
        } else {
            throw new RestFailureException("Status code is " + statusCode + " with response body [" + responseBody + "]", e);
        }
    }

    class WMRestServicesErrorHandler extends DefaultResponseErrorHandler {

        private RestRequestInfo restRequestInfo;
        private boolean handleError;

        public WMRestServicesErrorHandler(RestRequestInfo restRequestInfo, boolean handleError) {
            this.restRequestInfo = restRequestInfo;
            this.handleError = handleError;
        }

        @Override
        public void handleError(ClientHttpResponse response) throws IOException {
            if (handleError) {
                try {
                    super.handleError(response);
                } catch (HttpStatusCodeException e) {
                    logApiFailure(HttpMethod.valueOf(restRequestInfo.getMethod()), restRequestInfo.getEndpointAddress(), response.getStatusCode().value(), e.getResponseBodyAsString(), e);
                    throw e;
                }
            }
        }
    }
}

