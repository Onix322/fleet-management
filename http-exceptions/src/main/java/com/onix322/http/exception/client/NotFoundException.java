package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import jakarta.ws.rs.core.Response;

/**
 * Status 404 Not Found exception.
 */
public class NotFoundException extends HttpException {

    public NotFoundException() {
        super(Response.Status.NOT_FOUND);
    }

    public NotFoundException(String message) {
        super(Response.Status.NOT_FOUND, message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(Response.Status.NOT_FOUND, message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(Response.Status.NOT_FOUND, cause);
    }
}
