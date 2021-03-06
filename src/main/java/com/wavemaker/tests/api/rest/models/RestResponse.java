/**
 * Copyright © 2015 WaveMaker, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wavemaker.tests.api.rest.models;

import java.util.List;
import java.util.Map;

import org.apache.http.impl.cookie.BasicClientCookie;


/**
 * @author Uday Shankar
 */
public class RestResponse {

    private String responseBody;

    private String convertedResponse;

    private int statusCode;

    private Map<String, List<String>> responseHeaders;

    private String contentType;

    private List<BasicClientCookie> cookies;

    public List<BasicClientCookie> getCookies() {
        return cookies;
    }

    public void setCookies(final List<BasicClientCookie> cookies) {
        this.cookies = cookies;
    }

    public String getResponseBody() {
        return responseBody;
    }

    public void setResponseBody(String responseBody) {
        this.responseBody = responseBody;
    }

    public String getConvertedResponse() {
        return convertedResponse;
    }

    public void setConvertedResponse(String convertedResponse) {
        this.convertedResponse = convertedResponse;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public Map<String, List<String>> getResponseHeaders() {
        return responseHeaders;
    }

    public void setResponseHeaders(Map<String, List<String>> responseHeaders) {
        this.responseHeaders = responseHeaders;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    @Override
    public String toString() {
        return "RestResponse{" +
                ", statusCode=" + statusCode +
                ", contentType='" + contentType + '\'' +
                ", responseHeaders=" + responseHeaders +
                ", cookies=" + cookies +
                '}';
    }
}
