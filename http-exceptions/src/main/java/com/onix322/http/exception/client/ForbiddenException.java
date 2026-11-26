package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.HttpStatusCode;

/**
 * Status Forbidden exception.
 */
public class ForbiddenException extends HttpException {

    public ForbiddenException() {
        super(HttpStatusCode.FORBIDDEN);
    }

    public ForbiddenException(String message) {
        super(HttpStatusCode.FORBIDDEN, message);
    }

    public ForbiddenException(String message, Throwable cause) {
        super(HttpStatusCode.FORBIDDEN, message, cause);
    }

    public ForbiddenException(Throwable cause) {
        super(HttpStatusCode.FORBIDDEN, cause);
    }
}
