package com.wavemaker.tests.api.exception;

/**
 * Created by venkateswarluk on 13/7/17.
 */
public abstract class WMTestException extends RuntimeException{

    public WMTestException(){
        super();
    }

    public WMTestException(Throwable cause) {
        super(cause);
    }

    public WMTestException(String message) {
        super(message);
    }

    public WMTestException(String message, Throwable cause) {
        super(message, cause);
    }
}
