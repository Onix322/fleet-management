package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.HttpStatusCode;

/**
 * Status Gone exception.
 */
public class GoneException extends HttpException {

    public GoneException() {
        super(HttpStatusCode.GONE);
    }

    public GoneException(String message) {
        super(HttpStatusCode.GONE, message);
    }

    public GoneException(String message, Throwable cause) {
        super(HttpStatusCode.GONE, message, cause);
    }

    public GoneException(Throwable cause) {
        super(HttpStatusCode.GONE, cause);
    }
}
