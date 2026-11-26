package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import jakarta.ws.rs.core.Response;

/**
 * Status 412 Precondition Failed exception.
 */
public class PreconditionFailedException extends HttpException {

    public PreconditionFailedException() {
        super(Response.Status.PRECONDITION_FAILED);
    }

    public PreconditionFailedException(String message) {
        super(Response.Status.PRECONDITION_FAILED, message);
    }

    public PreconditionFailedException(String message, Throwable cause) {
        super(Response.Status.PRECONDITION_FAILED, message, cause);
    }

    public PreconditionFailedException(Throwable cause) {
        super(Response.Status.PRECONDITION_FAILED, cause);
    }
}
