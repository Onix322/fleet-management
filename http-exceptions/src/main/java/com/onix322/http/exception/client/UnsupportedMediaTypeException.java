package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.HttpStatusCode;

/**
 * Status Unsupported Media Type exception.
 */
public class UnsupportedMediaTypeException extends HttpException {

    public UnsupportedMediaTypeException() {
        super(HttpStatusCode.UNSUPPORTED_MEDIA_TYPE);
    }

    public UnsupportedMediaTypeException(String message) {
        super(HttpStatusCode.UNSUPPORTED_MEDIA_TYPE, message);
    }

    public UnsupportedMediaTypeException(String message, Throwable cause) {
        super(HttpStatusCode.UNSUPPORTED_MEDIA_TYPE, message, cause);
    }

    public UnsupportedMediaTypeException(Throwable cause) {
        super(HttpStatusCode.UNSUPPORTED_MEDIA_TYPE, cause);
    }
}
