package com.onix322.http.exception.server;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.HttpStatusCode;

/**
 * Status Bad Gateway exception.
 */
public class BadGatewayException extends HttpException {

    public BadGatewayException() {
        super(HttpStatusCode.BAD_GATEWAY);
    }

    public BadGatewayException(String message) {
        super(HttpStatusCode.BAD_GATEWAY, message);
    }

    public BadGatewayException(String message, Throwable cause) {
        super(HttpStatusCode.BAD_GATEWAY, message, cause);
    }

    public BadGatewayException(Throwable cause) {
        super(HttpStatusCode.BAD_GATEWAY, cause);
    }
}
