package com.onix322.http.exception.webdav;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.ExtendedStatus;
import jakarta.ws.rs.core.Response;

/**
 * Status 424 Failed Dependency exception.
 */
public class FailedDependencyException extends HttpException {

    public FailedDependencyException() {
        super(ExtendedStatus.FAILED_DEPENDENCY);
    }

    public FailedDependencyException(String message) {
        super(ExtendedStatus.FAILED_DEPENDENCY, message);
    }

    public FailedDependencyException(String message, Throwable cause) {
        super(ExtendedStatus.FAILED_DEPENDENCY, message, cause);
    }

    public FailedDependencyException(Throwable cause) {
        super(ExtendedStatus.FAILED_DEPENDENCY, cause);
    }
}
