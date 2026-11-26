package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.HttpStatusCode;

/**
 * Status Unauthorized exception.
 */
public class UnauthorizedException extends HttpException {

    public UnauthorizedException() {
        super(HttpStatusCode.UNAUTHORIZED);
    }

    public UnauthorizedException(String message) {
        super(HttpStatusCode.UNAUTHORIZED, message);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(HttpStatusCode.UNAUTHORIZED, message, cause);
    }

    public UnauthorizedException(Throwable cause) {
        super(HttpStatusCode.UNAUTHORIZED, cause);
    }
}
