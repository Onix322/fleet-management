package com.onix322.http.exception.server;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.HttpStatusCode;

/**
 * Status Internal Server Error exception.
 */
public class InternalServerErrorException extends HttpException {

    public InternalServerErrorException() {
        super(HttpStatusCode.INTERNAL_SERVER_ERROR);
    }

    public InternalServerErrorException(String message) {
        super(HttpStatusCode.INTERNAL_SERVER_ERROR, message);
    }

    public InternalServerErrorException(String message, Throwable cause) {
        super(HttpStatusCode.INTERNAL_SERVER_ERROR, message, cause);
    }

    public InternalServerErrorException(Throwable cause) {
        super(HttpStatusCode.INTERNAL_SERVER_ERROR, cause);
    }
}
