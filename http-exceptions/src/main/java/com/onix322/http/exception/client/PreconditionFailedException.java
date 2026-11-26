package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.HttpStatusCode;

/**
 * Status Precondition Failed exception.
 */
public class PreconditionFailedException extends HttpException {

    public PreconditionFailedException() {
        super(HttpStatusCode.PRECONDITION_FAILED);
    }

    public PreconditionFailedException(String message) {
        super(HttpStatusCode.PRECONDITION_FAILED, message);
    }

    public PreconditionFailedException(String message, Throwable cause) {
        super(HttpStatusCode.PRECONDITION_FAILED, message, cause);
    }

    public PreconditionFailedException(Throwable cause) {
        super(HttpStatusCode.PRECONDITION_FAILED, cause);
    }
}
