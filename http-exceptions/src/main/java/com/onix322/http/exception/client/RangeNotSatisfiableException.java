package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import jakarta.ws.rs.core.Response;

/**
 * Status 416 Range Not Satisfiable exception.
 */
public class RangeNotSatisfiableException extends HttpException {

    public RangeNotSatisfiableException() {
        super(Response.Status.REQUESTED_RANGE_NOT_SATISFIABLE);
    }

    public RangeNotSatisfiableException(String message) {
        super(Response.Status.REQUESTED_RANGE_NOT_SATISFIABLE, message);
    }

    public RangeNotSatisfiableException(String message, Throwable cause) {
        super(Response.Status.REQUESTED_RANGE_NOT_SATISFIABLE, message, cause);
    }

    public RangeNotSatisfiableException(Throwable cause) {
        super(Response.Status.REQUESTED_RANGE_NOT_SATISFIABLE, cause);
    }
}
