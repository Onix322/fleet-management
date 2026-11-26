package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.HttpStatusCode;

/**
 * Status Uri Too Long exception.
 */
public class UriTooLongException extends HttpException {

    public UriTooLongException() {
        super(HttpStatusCode.URI_TOO_LONG);
    }

    public UriTooLongException(String message) {
        super(HttpStatusCode.URI_TOO_LONG, message);
    }

    public UriTooLongException(String message, Throwable cause) {
        super(HttpStatusCode.URI_TOO_LONG, message, cause);
    }

    public UriTooLongException(Throwable cause) {
        super(HttpStatusCode.URI_TOO_LONG, cause);
    }
}
