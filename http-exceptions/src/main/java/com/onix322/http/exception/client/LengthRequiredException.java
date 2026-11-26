package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import jakarta.ws.rs.core.Response;

/**
 * Status 411 Length Required exception.
 */
public class LengthRequiredException extends HttpException {

    public LengthRequiredException() {
        super(Response.Status.LENGTH_REQUIRED);
    }

    public LengthRequiredException(String message) {
        super(Response.Status.LENGTH_REQUIRED, message);
    }

    public LengthRequiredException(String message, Throwable cause) {
        super(Response.Status.LENGTH_REQUIRED, message, cause);
    }

    public LengthRequiredException(Throwable cause) {
        super(Response.Status.LENGTH_REQUIRED, cause);
    }
}
