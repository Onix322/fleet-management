package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import jakarta.ws.rs.core.Response;

/**
 * Status 413 Content Too Large exception.
 */
public class ContentTooLargeException extends HttpException {

    public ContentTooLargeException() {
        super(Response.Status.REQUEST_ENTITY_TOO_LARGE);
    }

    public ContentTooLargeException(String message) {
        super(Response.Status.REQUEST_ENTITY_TOO_LARGE, message);
    }

    public ContentTooLargeException(String message, Throwable cause) {
        super(Response.Status.REQUEST_ENTITY_TOO_LARGE, message, cause);
    }

    public ContentTooLargeException(Throwable cause) {
        super(Response.Status.REQUEST_ENTITY_TOO_LARGE, cause);
    }
}
