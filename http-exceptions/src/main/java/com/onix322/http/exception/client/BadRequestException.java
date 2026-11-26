package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import jakarta.ws.rs.core.Response;

/**
 * Status 400 Bad Request exception.
 */
public class BadRequestException extends HttpException {

    public BadRequestException() {
        super(Response.Status.BAD_REQUEST);
    }

    public BadRequestException(String message) {
        super(Response.Status.BAD_REQUEST, message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(Response.Status.BAD_REQUEST, message, cause);
    }

    public BadRequestException(Throwable cause) {
        super(Response.Status.BAD_REQUEST, cause);
    }
}
