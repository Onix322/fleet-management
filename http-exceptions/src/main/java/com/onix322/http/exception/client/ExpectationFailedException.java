package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.HttpStatusCode;

/**
 * Status Expectation Failed exception.
 */
public class ExpectationFailedException extends HttpException {

    public ExpectationFailedException() {
        super(HttpStatusCode.EXPECTATION_FAILED);
    }

    public ExpectationFailedException(String message) {
        super(HttpStatusCode.EXPECTATION_FAILED, message);
    }

    public ExpectationFailedException(String message, Throwable cause) {
        super(HttpStatusCode.EXPECTATION_FAILED, message, cause);
    }

    public ExpectationFailedException(Throwable cause) {
        super(HttpStatusCode.EXPECTATION_FAILED, cause);
    }
}
