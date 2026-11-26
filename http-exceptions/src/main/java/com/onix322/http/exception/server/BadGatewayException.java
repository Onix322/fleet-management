package com.onix322.http.exception.server;

import com.onix322.http.exception.HttpException;
import jakarta.ws.rs.core.Response;

/**
 * Status 502 Bad Gateway exception.
 */
public class BadGatewayException extends HttpException {

    public BadGatewayException() {
        super(Response.Status.BAD_GATEWAY);
    }

    public BadGatewayException(String message) {
        super(Response.Status.BAD_GATEWAY, message);
    }

    public BadGatewayException(String message, Throwable cause) {
        super(Response.Status.BAD_GATEWAY, message, cause);
    }

    public BadGatewayException(Throwable cause) {
        super(Response.Status.BAD_GATEWAY, cause);
    }
}
