package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.HttpStatusCode;

/**
 * Status Method Not Allowed exception.
 */
public class MethodNotAllowedException extends HttpException {

    public MethodNotAllowedException() {
        super(HttpStatusCode.METHOD_NOT_ALLOWED);
    }

    public MethodNotAllowedException(String message) {
        super(HttpStatusCode.METHOD_NOT_ALLOWED, message);
    }

    public MethodNotAllowedException(String message, Throwable cause) {
        super(HttpStatusCode.METHOD_NOT_ALLOWED, message, cause);
    }

    public MethodNotAllowedException(Throwable cause) {
        super(HttpStatusCode.METHOD_NOT_ALLOWED, cause);
    }
}
