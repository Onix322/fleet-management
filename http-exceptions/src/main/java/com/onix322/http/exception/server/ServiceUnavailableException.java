package com.onix322.http.exception.server;

import com.onix322.http.exception.HttpException;
import jakarta.ws.rs.core.Response;

/**
 * Status 503 Service Unavailable exception.
 */
public class ServiceUnavailableException extends HttpException {

    public ServiceUnavailableException() {
        super(Response.Status.SERVICE_UNAVAILABLE);
    }

    public ServiceUnavailableException(String message) {
        super(Response.Status.SERVICE_UNAVAILABLE, message);
    }

    public ServiceUnavailableException(String message, Throwable cause) {
        super(Response.Status.SERVICE_UNAVAILABLE, message, cause);
    }

    public ServiceUnavailableException(Throwable cause) {
        super(Response.Status.SERVICE_UNAVAILABLE, cause);
    }
}
