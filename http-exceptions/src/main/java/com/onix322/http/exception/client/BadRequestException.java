package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.HttpStatusCode;

/**
 * Status Bad Request exception.
 */
public class BadRequestException extends HttpException {

    public BadRequestException() {
        super(HttpStatusCode.BAD_REQUEST);
    }

    public BadRequestException(String message) {
        super(HttpStatusCode.BAD_REQUEST, message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(HttpStatusCode.BAD_REQUEST, message, cause);
    }

    public BadRequestException(Throwable cause) {
        super(HttpStatusCode.BAD_REQUEST, cause);
    }
}
