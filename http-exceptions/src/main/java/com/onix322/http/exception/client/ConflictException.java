package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.HttpStatusCode;

/**
 * Status Conflict exception.
 */
public class ConflictException extends HttpException {

    public ConflictException() {
        super(HttpStatusCode.CONFLICT);
    }

    public ConflictException(String message) {
        super(HttpStatusCode.CONFLICT, message);
    }

    public ConflictException(String message, Throwable cause) {
        super(HttpStatusCode.CONFLICT, message, cause);
    }

    public ConflictException(Throwable cause) {
        super(HttpStatusCode.CONFLICT, cause);
    }
}
