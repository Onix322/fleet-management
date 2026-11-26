package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import jakarta.ws.rs.core.Response;

/**
 * Status 415 Unsupported Media Type exception.
 */
public class UnsupportedMediaTypeException extends HttpException {

    public UnsupportedMediaTypeException() {
        super(Response.Status.UNSUPPORTED_MEDIA_TYPE);
    }

    public UnsupportedMediaTypeException(String message) {
        super(Response.Status.UNSUPPORTED_MEDIA_TYPE, message);
    }

    public UnsupportedMediaTypeException(String message, Throwable cause) {
        super(Response.Status.UNSUPPORTED_MEDIA_TYPE, message, cause);
    }

    public UnsupportedMediaTypeException(Throwable cause) {
        super(Response.Status.UNSUPPORTED_MEDIA_TYPE, cause);
    }
}
