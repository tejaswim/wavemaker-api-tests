package com.wavemaker.api.exception;

/**
 * Created by ArjunSahasranam on 11/3/16.
 */
public class ResourceNotFoundException extends RestFailureException {
    public ResourceNotFoundException() {
        super();
    }

    public ResourceNotFoundException(final String message) {
        super(message);
    }

    public ResourceNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
