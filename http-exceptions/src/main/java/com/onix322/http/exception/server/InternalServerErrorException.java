package com.onix322.http.exception.server;

import com.onix322.http.exception.HttpException;
import jakarta.ws.rs.core.Response;

/**
 * Status 500 Internal Server Error exception.
 */
public class InternalServerErrorException extends HttpException {

    public InternalServerErrorException() {
        super(Response.Status.INTERNAL_SERVER_ERROR);
    }

    public InternalServerErrorException(String message) {
        super(Response.Status.INTERNAL_SERVER_ERROR, message);
    }

    public InternalServerErrorException(String message, Throwable cause) {
        super(Response.Status.INTERNAL_SERVER_ERROR, message, cause);
    }

    public InternalServerErrorException(Throwable cause) {
        super(Response.Status.INTERNAL_SERVER_ERROR, cause);
    }
}
