package com.onix322.http.exception.server;

import com.onix322.http.exception.HttpException;
import jakarta.ws.rs.core.Response;

/**
 * Status 505 HTTP Version Not Supported exception.
 */
public class HttpVersionNotSupportedException extends HttpException {

    public HttpVersionNotSupportedException() {
        super(Response.Status.HTTP_VERSION_NOT_SUPPORTED);
    }

    public HttpVersionNotSupportedException(String message) {
        super(Response.Status.HTTP_VERSION_NOT_SUPPORTED, message);
    }

    public HttpVersionNotSupportedException(String message, Throwable cause) {
        super(Response.Status.HTTP_VERSION_NOT_SUPPORTED, message, cause);
    }

    public HttpVersionNotSupportedException(Throwable cause) {
        super(Response.Status.HTTP_VERSION_NOT_SUPPORTED, cause);
    }
}
