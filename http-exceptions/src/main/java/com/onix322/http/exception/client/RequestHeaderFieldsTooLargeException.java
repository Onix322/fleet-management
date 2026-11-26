package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.HttpStatusCode;

/**
 * Status Request Header Fields Too Large exception.
 */
public class RequestHeaderFieldsTooLargeException extends HttpException {

    public RequestHeaderFieldsTooLargeException() {
        super(HttpStatusCode.REQUEST_HEADER_FIELDS_TOO_LARGE);
    }

    public RequestHeaderFieldsTooLargeException(String message) {
        super(HttpStatusCode.REQUEST_HEADER_FIELDS_TOO_LARGE, message);
    }

    public RequestHeaderFieldsTooLargeException(String message, Throwable cause) {
        super(HttpStatusCode.REQUEST_HEADER_FIELDS_TOO_LARGE, message, cause);
    }

    public RequestHeaderFieldsTooLargeException(Throwable cause) {
        super(HttpStatusCode.REQUEST_HEADER_FIELDS_TOO_LARGE, cause);
    }
}
