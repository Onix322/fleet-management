package com.onix322.http.exception.server;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.HttpStatusCode;

/**
 * Status Not Implemented exception.
 */
public class NotImplementedException extends HttpException {

    public NotImplementedException() {
        super(HttpStatusCode.NOT_IMPLEMENTED);
    }

    public NotImplementedException(String message) {
        super(HttpStatusCode.NOT_IMPLEMENTED, message);
    }

    public NotImplementedException(String message, Throwable cause) {
        super(HttpStatusCode.NOT_IMPLEMENTED, message, cause);
    }

    public NotImplementedException(Throwable cause) {
        super(HttpStatusCode.NOT_IMPLEMENTED, cause);
    }
}
