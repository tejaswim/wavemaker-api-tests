package com.wavemaker.api.rest;

/**
 * Created by ArjunSahasranam on 5/2/16.
 */
public interface IRestInput {
    Object getPayload();

    String getContentType();
}
