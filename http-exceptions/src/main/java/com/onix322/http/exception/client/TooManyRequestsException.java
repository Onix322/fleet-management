package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.HttpStatusCode;

/**
 * Status Too Many Requests exception.
 */
public class TooManyRequestsException extends HttpException {

    public TooManyRequestsException() {
        super(HttpStatusCode.TOO_MANY_REQUESTS);
    }

    public TooManyRequestsException(String message) {
        super(HttpStatusCode.TOO_MANY_REQUESTS, message);
    }

    public TooManyRequestsException(String message, Throwable cause) {
        super(HttpStatusCode.TOO_MANY_REQUESTS, message, cause);
    }

    public TooManyRequestsException(Throwable cause) {
        super(HttpStatusCode.TOO_MANY_REQUESTS, cause);
    }
}
