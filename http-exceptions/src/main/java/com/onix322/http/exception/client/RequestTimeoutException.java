package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.HttpStatusCode;

/**
 * Status Request Timeout exception.
 */
public class RequestTimeoutException extends HttpException {

    public RequestTimeoutException() {
        super(HttpStatusCode.REQUEST_TIMEOUT);
    }

    public RequestTimeoutException(String message) {
        super(HttpStatusCode.REQUEST_TIMEOUT, message);
    }

    public RequestTimeoutException(String message, Throwable cause) {
        super(HttpStatusCode.REQUEST_TIMEOUT, message, cause);
    }

    public RequestTimeoutException(Throwable cause) {
        super(HttpStatusCode.REQUEST_TIMEOUT, cause);
    }
}
