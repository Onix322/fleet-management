package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.HttpStatusCode;

/**
 * Status Not Found exception.
 */
public class NotFoundException extends HttpException {

    public NotFoundException() {
        super(HttpStatusCode.NOT_FOUND);
    }

    public NotFoundException(String message) {
        super(HttpStatusCode.NOT_FOUND, message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(HttpStatusCode.NOT_FOUND, message, cause);
    }

    public NotFoundException(Throwable cause) {
        super(HttpStatusCode.NOT_FOUND, cause);
    }
}
