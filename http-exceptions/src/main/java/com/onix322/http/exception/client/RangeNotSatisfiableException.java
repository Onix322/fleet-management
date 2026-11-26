package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.HttpStatusCode;

/**
 * Status Range Not Satisfiable exception.
 */
public class RangeNotSatisfiableException extends HttpException {

    public RangeNotSatisfiableException() {
        super(HttpStatusCode.RANGE_NOT_SATISFIABLE);
    }

    public RangeNotSatisfiableException(String message) {
        super(HttpStatusCode.RANGE_NOT_SATISFIABLE, message);
    }

    public RangeNotSatisfiableException(String message, Throwable cause) {
        super(HttpStatusCode.RANGE_NOT_SATISFIABLE, message, cause);
    }

    public RangeNotSatisfiableException(Throwable cause) {
        super(HttpStatusCode.RANGE_NOT_SATISFIABLE, cause);
    }
}
