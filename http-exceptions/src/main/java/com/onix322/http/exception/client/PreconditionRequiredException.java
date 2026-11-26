package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.HttpStatusCode;

/**
 * Status Precondition Required exception.
 */
public class PreconditionRequiredException extends HttpException {

    public PreconditionRequiredException() {
        super(HttpStatusCode.PRECONDITION_REQUIRED);
    }

    public PreconditionRequiredException(String message) {
        super(HttpStatusCode.PRECONDITION_REQUIRED, message);
    }

    public PreconditionRequiredException(String message, Throwable cause) {
        super(HttpStatusCode.PRECONDITION_REQUIRED, message, cause);
    }

    public PreconditionRequiredException(Throwable cause) {
        super(HttpStatusCode.PRECONDITION_REQUIRED, cause);
    }
}
