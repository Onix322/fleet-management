package com.onix322.http.exception.server;

import com.onix322.http.exception.HttpException;
import jakarta.ws.rs.core.Response;

/**
 * Status 504 Gateway Timeout exception.
 */
public class GatewayTimeoutException extends HttpException {

    public GatewayTimeoutException() {
        super(Response.Status.GATEWAY_TIMEOUT);
    }

    public GatewayTimeoutException(String message) {
        super(Response.Status.GATEWAY_TIMEOUT, message);
    }

    public GatewayTimeoutException(String message, Throwable cause) {
        super(Response.Status.GATEWAY_TIMEOUT, message, cause);
    }

    public GatewayTimeoutException(Throwable cause) {
        super(Response.Status.GATEWAY_TIMEOUT, cause);
    }
}
