package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.HttpStatusCode;

/**
 * Status Too Early exception.
 */
public class TooEarlyException extends HttpException {

    public TooEarlyException() {
        super(HttpStatusCode.TOO_EARLY);
    }

    public TooEarlyException(String message) {
        super(HttpStatusCode.TOO_EARLY, message);
    }

    public TooEarlyException(String message, Throwable cause) {
        super(HttpStatusCode.TOO_EARLY, message, cause);
    }

    public TooEarlyException(Throwable cause) {
        super(HttpStatusCode.TOO_EARLY, cause);
    }
}
