package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import jakarta.ws.rs.core.Response;

/**
 * Status 410 Gone exception.
 */
public class GoneException extends HttpException {

    public GoneException() {
        super(Response.Status.GONE);
    }

    public GoneException(String message) {
        super(Response.Status.GONE, message);
    }

    public GoneException(String message, Throwable cause) {
        super(Response.Status.GONE, message, cause);
    }

    public GoneException(Throwable cause) {
        super(Response.Status.GONE, cause);
    }
}
