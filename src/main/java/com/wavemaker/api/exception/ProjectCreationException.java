package com.wavemaker.api.exception;


/**
 * Created by venkateswarluk on 13/7/17.
 */
public class ProjectCreationException extends WMTestException {

    ProjectCreationException(){
    }

    public ProjectCreationException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ProjectCreationException(final Throwable cause) {
        super(cause);
    }

    public ProjectCreationException(final String message) {
        super(String.format(WmTestExceptionMessage.PROJECT_CREATION_FAILED,message));
    }
}
