package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import jakarta.ws.rs.core.Response;

/**
 * Status 401 Unauthorized exception.
 */
public class UnauthorizedException extends HttpException {

    public UnauthorizedException() {
        super(Response.Status.UNAUTHORIZED);
    }

    public UnauthorizedException(String message) {
        super(Response.Status.UNAUTHORIZED, message);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(Response.Status.UNAUTHORIZED, message, cause);
    }

    public UnauthorizedException(Throwable cause) {
        super(Response.Status.UNAUTHORIZED, cause);
    }
}
