package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import jakarta.ws.rs.core.Response;

/**
 * Status 408 Request Timeout exception.
 */
public class RequestTimeoutException extends HttpException {

    public RequestTimeoutException() {
        super(Response.Status.REQUEST_TIMEOUT);
    }

    public RequestTimeoutException(String message) {
        super(Response.Status.REQUEST_TIMEOUT, message);
    }

    public RequestTimeoutException(String message, Throwable cause) {
        super(Response.Status.REQUEST_TIMEOUT, message, cause);
    }

    public RequestTimeoutException(Throwable cause) {
        super(Response.Status.REQUEST_TIMEOUT, cause);
    }
}
