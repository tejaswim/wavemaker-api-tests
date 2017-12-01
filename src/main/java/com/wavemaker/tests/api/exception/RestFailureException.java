package com.wavemaker.tests.api.exception;

/**
 * Created by ArjunSahasranam on 9/2/16.
 */
public class RestFailureException extends RuntimeException {

    public RestFailureException() {
        super();
    }

    public RestFailureException(final String message) {
        super(message);
    }

    public RestFailureException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
