package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.HttpStatusCode;

/**
 * Status Content Too Large exception.
 */
public class ContentTooLargeException extends HttpException {

    public ContentTooLargeException() {
        super(HttpStatusCode.CONTENT_TOO_LARGE);
    }

    public ContentTooLargeException(String message) {
        super(HttpStatusCode.CONTENT_TOO_LARGE, message);
    }

    public ContentTooLargeException(String message, Throwable cause) {
        super(HttpStatusCode.CONTENT_TOO_LARGE, message, cause);
    }

    public ContentTooLargeException(Throwable cause) {
        super(HttpStatusCode.CONTENT_TOO_LARGE, cause);
    }
}
