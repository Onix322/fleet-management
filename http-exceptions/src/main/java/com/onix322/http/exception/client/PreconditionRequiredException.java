package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import jakarta.ws.rs.core.Response;

/**
 * Status 428 Precondition Required exception.
 */
public class PreconditionRequiredException extends HttpException {

    public PreconditionRequiredException() {
        super(Response.Status.PRECONDITION_REQUIRED);
    }

    public PreconditionRequiredException(String message) {
        super(Response.Status.PRECONDITION_REQUIRED, message);
    }

    public PreconditionRequiredException(String message, Throwable cause) {
        super(Response.Status.PRECONDITION_REQUIRED, message, cause);
    }

    public PreconditionRequiredException(Throwable cause) {
        super(Response.Status.PRECONDITION_REQUIRED, cause);
    }
}
