package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import jakarta.ws.rs.core.Response;

/**
 * Status 405 Method Not Allowed exception.
 */
public class MethodNotAllowedException extends HttpException {

    public MethodNotAllowedException() {
        super(Response.Status.METHOD_NOT_ALLOWED);
    }

    public MethodNotAllowedException(String message) {
        super(Response.Status.METHOD_NOT_ALLOWED, message);
    }

    public MethodNotAllowedException(String message, Throwable cause) {
        super(Response.Status.METHOD_NOT_ALLOWED, message, cause);
    }

    public MethodNotAllowedException(Throwable cause) {
        super(Response.Status.METHOD_NOT_ALLOWED, cause);
    }
}
