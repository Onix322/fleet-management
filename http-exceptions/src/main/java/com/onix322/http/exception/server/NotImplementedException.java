package com.onix322.http.exception.server;

import com.onix322.http.exception.HttpException;
import jakarta.ws.rs.core.Response;

/**
 * Status 501 Not Implemented exception.
 */
public class NotImplementedException extends HttpException {

    public NotImplementedException() {
        super(Response.Status.NOT_IMPLEMENTED);
    }

    public NotImplementedException(String message) {
        super(Response.Status.NOT_IMPLEMENTED, message);
    }

    public NotImplementedException(String message, Throwable cause) {
        super(Response.Status.NOT_IMPLEMENTED, message, cause);
    }

    public NotImplementedException(Throwable cause) {
        super(Response.Status.NOT_IMPLEMENTED, cause);
    }
}
