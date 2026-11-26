package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import jakarta.ws.rs.core.Response;

/**
 * Status 409 Conflict exception.
 */
public class ConflictException extends HttpException {

    public ConflictException() {
        super(Response.Status.CONFLICT);
    }

    public ConflictException(String message) {
        super(Response.Status.CONFLICT, message);
    }

    public ConflictException(String message, Throwable cause) {
        super(Response.Status.CONFLICT, message, cause);
    }

    public ConflictException(Throwable cause) {
        super(Response.Status.CONFLICT, cause);
    }
}
