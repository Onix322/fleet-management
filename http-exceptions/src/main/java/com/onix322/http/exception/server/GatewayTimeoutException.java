package com.onix322.http.exception.server;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.HttpStatusCode;

/**
 * Status Gateway Timeout exception.
 */
public class GatewayTimeoutException extends HttpException {

    public GatewayTimeoutException() {
        super(HttpStatusCode.GATEWAY_TIMEOUT);
    }

    public GatewayTimeoutException(String message) {
        super(HttpStatusCode.GATEWAY_TIMEOUT, message);
    }

    public GatewayTimeoutException(String message, Throwable cause) {
        super(HttpStatusCode.GATEWAY_TIMEOUT, message, cause);
    }

    public GatewayTimeoutException(Throwable cause) {
        super(HttpStatusCode.GATEWAY_TIMEOUT, cause);
    }
}
