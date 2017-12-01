package com.wavemaker.tests.api.rest;

import org.springframework.http.MediaType;

/**
 * Created by ArjunSahasranam on 5/2/16.
 */
public class RestInputImpl implements IRestInput {
    public Object getPayload() {
        return null;
    }

    public String getContentType() {
        return MediaType.APPLICATION_JSON_VALUE;
    }
}
