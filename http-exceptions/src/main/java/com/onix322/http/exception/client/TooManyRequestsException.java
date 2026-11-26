package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import jakarta.ws.rs.core.Response;

/**
 * Status 429 Too Many Requests exception.
 */
public class TooManyRequestsException extends HttpException {

    public TooManyRequestsException() {
        super(Response.Status.TOO_MANY_REQUESTS);
    }

    public TooManyRequestsException(String message) {
        super(Response.Status.TOO_MANY_REQUESTS, message);
    }

    public TooManyRequestsException(String message, Throwable cause) {
        super(Response.Status.TOO_MANY_REQUESTS, message, cause);
    }

    public TooManyRequestsException(Throwable cause) {
        super(Response.Status.TOO_MANY_REQUESTS, cause);
    }
}
