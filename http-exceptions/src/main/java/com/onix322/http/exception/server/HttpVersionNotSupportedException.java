package com.onix322.http.exception.server;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.HttpStatusCode;

/**
 * Status Http Version Not Supported exception.
 */
public class HttpVersionNotSupportedException extends HttpException {

    public HttpVersionNotSupportedException() {
        super(HttpStatusCode.HTTP_VERSION_NOT_SUPPORTED);
    }

    public HttpVersionNotSupportedException(String message) {
        super(HttpStatusCode.HTTP_VERSION_NOT_SUPPORTED, message);
    }

    public HttpVersionNotSupportedException(String message, Throwable cause) {
        super(HttpStatusCode.HTTP_VERSION_NOT_SUPPORTED, message, cause);
    }

    public HttpVersionNotSupportedException(Throwable cause) {
        super(HttpStatusCode.HTTP_VERSION_NOT_SUPPORTED, cause);
    }
}
