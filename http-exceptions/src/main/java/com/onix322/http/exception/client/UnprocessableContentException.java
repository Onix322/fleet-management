package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.ExtendedStatus;
import jakarta.ws.rs.core.Response;

/**
 * Status 422 Unprocessable Content exception.
 */
public class UnprocessableContentException extends HttpException {

    public UnprocessableContentException() {
        super(ExtendedStatus.UNPROCESSABLE_CONTENT);
    }

    public UnprocessableContentException(String message) {
        super(ExtendedStatus.UNPROCESSABLE_CONTENT, message);
    }

    public UnprocessableContentException(String message, Throwable cause) {
        super(ExtendedStatus.UNPROCESSABLE_CONTENT, message, cause);
    }

    public UnprocessableContentException(Throwable cause) {
        super(ExtendedStatus.UNPROCESSABLE_CONTENT, cause);
    }
}
