package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import jakarta.ws.rs.core.Response;

/**
 * Status 414 URI Too Long exception.
 */
public class UriTooLongException extends HttpException {

    public UriTooLongException() {
        super(Response.Status.REQUEST_URI_TOO_LONG);
    }

    public UriTooLongException(String message) {
        super(Response.Status.REQUEST_URI_TOO_LONG, message);
    }

    public UriTooLongException(String message, Throwable cause) {
        super(Response.Status.REQUEST_URI_TOO_LONG, message, cause);
    }

    public UriTooLongException(Throwable cause) {
        super(Response.Status.REQUEST_URI_TOO_LONG, cause);
    }
}
