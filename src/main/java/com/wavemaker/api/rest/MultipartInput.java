package com.wavemaker.api.rest;

import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;

/**
 * Created by Tejaswi Maryala on 9/14/2017.
 */
public class MultipartInput implements IRestInput {

    private final MultiValueMap<String, Object> input;

    public MultipartInput(final MultiValueMap<String, Object> input) {
        this.input = input;
    }

    @Override
    public MultiValueMap<String, Object> getPayload() {
        return input;
    }

    @Override
    public String getContentType() {
        return MediaType.MULTIPART_FORM_DATA_VALUE;
    }
}
