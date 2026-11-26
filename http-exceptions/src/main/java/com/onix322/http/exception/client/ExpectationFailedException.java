package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import jakarta.ws.rs.core.Response;

/**
 * Status 417 Expectation Failed exception.
 */
public class ExpectationFailedException extends HttpException {

    public ExpectationFailedException() {
        super(Response.Status.EXPECTATION_FAILED);
    }

    public ExpectationFailedException(String message) {
        super(Response.Status.EXPECTATION_FAILED, message);
    }

    public ExpectationFailedException(String message, Throwable cause) {
        super(Response.Status.EXPECTATION_FAILED, message, cause);
    }

    public ExpectationFailedException(Throwable cause) {
        super(Response.Status.EXPECTATION_FAILED, cause);
    }
}
