package com.onix322.http.exception.webdav;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.HttpStatusCode;

/**
 * Status Failed Dependency exception.
 */
public class FailedDependencyException extends HttpException {

    public FailedDependencyException() {
        super(HttpStatusCode.FAILED_DEPENDENCY);
    }

    public FailedDependencyException(String message) {
        super(HttpStatusCode.FAILED_DEPENDENCY, message);
    }

    public FailedDependencyException(String message, Throwable cause) {
        super(HttpStatusCode.FAILED_DEPENDENCY, message, cause);
    }

    public FailedDependencyException(Throwable cause) {
        super(HttpStatusCode.FAILED_DEPENDENCY, cause);
    }
}
