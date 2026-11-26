package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import jakarta.ws.rs.core.Response;

/**
 * Status 406 Not Acceptable exception.
 */
public class NotAcceptableException extends HttpException {

    public NotAcceptableException() {
        super(Response.Status.NOT_ACCEPTABLE);
    }

    public NotAcceptableException(String message) {
        super(Response.Status.NOT_ACCEPTABLE, message);
    }

    public NotAcceptableException(String message, Throwable cause) {
        super(Response.Status.NOT_ACCEPTABLE, message, cause);
    }

    public NotAcceptableException(Throwable cause) {
        super(Response.Status.NOT_ACCEPTABLE, cause);
    }
}
