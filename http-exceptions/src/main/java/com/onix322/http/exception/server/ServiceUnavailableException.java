package com.onix322.http.exception.server;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.HttpStatusCode;

/**
 * Status Service Unavailable exception.
 */
public class ServiceUnavailableException extends HttpException {

    public ServiceUnavailableException() {
        super(HttpStatusCode.SERVICE_UNAVAILABLE);
    }

    public ServiceUnavailableException(String message) {
        super(HttpStatusCode.SERVICE_UNAVAILABLE, message);
    }

    public ServiceUnavailableException(String message, Throwable cause) {
        super(HttpStatusCode.SERVICE_UNAVAILABLE, message, cause);
    }

    public ServiceUnavailableException(Throwable cause) {
        super(HttpStatusCode.SERVICE_UNAVAILABLE, cause);
    }
}
