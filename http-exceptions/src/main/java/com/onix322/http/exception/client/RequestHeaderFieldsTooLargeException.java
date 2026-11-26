package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import jakarta.ws.rs.core.Response;

/**
 * Status 431 Request Header Fields Too Large exception.
 */
public class RequestHeaderFieldsTooLargeException extends HttpException {

    public RequestHeaderFieldsTooLargeException() {
        super(Response.Status.REQUEST_HEADER_FIELDS_TOO_LARGE);
    }

    public RequestHeaderFieldsTooLargeException(String message) {
        super(Response.Status.REQUEST_HEADER_FIELDS_TOO_LARGE, message);
    }

    public RequestHeaderFieldsTooLargeException(String message, Throwable cause) {
        super(Response.Status.REQUEST_HEADER_FIELDS_TOO_LARGE, message, cause);
    }

    public RequestHeaderFieldsTooLargeException(Throwable cause) {
        super(Response.Status.REQUEST_HEADER_FIELDS_TOO_LARGE, cause);
    }
}
