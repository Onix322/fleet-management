package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.HttpStatusCode;

/**
 * Status Not Acceptable exception.
 */
public class NotAcceptableException extends HttpException {

    public NotAcceptableException() {
        super(HttpStatusCode.NOT_ACCEPTABLE);
    }

    public NotAcceptableException(String message) {
        super(HttpStatusCode.NOT_ACCEPTABLE, message);
    }

    public NotAcceptableException(String message, Throwable cause) {
        super(HttpStatusCode.NOT_ACCEPTABLE, message, cause);
    }

    public NotAcceptableException(Throwable cause) {
        super(HttpStatusCode.NOT_ACCEPTABLE, cause);
    }
}
