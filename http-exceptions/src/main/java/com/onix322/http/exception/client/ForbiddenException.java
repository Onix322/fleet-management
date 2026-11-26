package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import jakarta.ws.rs.core.Response;

/**
 * Status 403 Forbidden exception.
 */
public class ForbiddenException extends HttpException {

    public ForbiddenException() {
        super(Response.Status.FORBIDDEN);
    }

    public ForbiddenException(String message) {
        super(Response.Status.FORBIDDEN, message);
    }

    public ForbiddenException(String message, Throwable cause) {
        super(Response.Status.FORBIDDEN, message, cause);
    }

    public ForbiddenException(Throwable cause) {
        super(Response.Status.FORBIDDEN, cause);
    }
}
