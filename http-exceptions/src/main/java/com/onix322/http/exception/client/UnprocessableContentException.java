package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.HttpStatusCode;

/**
 * Status Unprocessable Content exception.
 */
public class UnprocessableContentException extends HttpException {

    public UnprocessableContentException() {
        super(HttpStatusCode.UNPROCESSABLE_CONTENT);
    }

    public UnprocessableContentException(String message) {
        super(HttpStatusCode.UNPROCESSABLE_CONTENT, message);
    }

    public UnprocessableContentException(String message, Throwable cause) {
        super(HttpStatusCode.UNPROCESSABLE_CONTENT, message, cause);
    }

    public UnprocessableContentException(Throwable cause) {
        super(HttpStatusCode.UNPROCESSABLE_CONTENT, cause);
    }
}
