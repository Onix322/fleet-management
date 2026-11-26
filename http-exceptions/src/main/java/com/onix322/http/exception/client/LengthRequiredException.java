package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.HttpStatusCode;

/**
 * Status Length Required exception.
 */
public class LengthRequiredException extends HttpException {

    public LengthRequiredException() {
        super(HttpStatusCode.LENGTH_REQUIRED);
    }

    public LengthRequiredException(String message) {
        super(HttpStatusCode.LENGTH_REQUIRED, message);
    }

    public LengthRequiredException(String message, Throwable cause) {
        super(HttpStatusCode.LENGTH_REQUIRED, message, cause);
    }

    public LengthRequiredException(Throwable cause) {
        super(HttpStatusCode.LENGTH_REQUIRED, cause);
    }
}
