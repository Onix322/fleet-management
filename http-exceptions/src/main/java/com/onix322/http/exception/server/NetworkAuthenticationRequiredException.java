package com.onix322.http.exception.server;

import com.onix322.http.exception.HttpException;
import jakarta.ws.rs.core.Response;

/**
 * Status 511 Network Authentication Required exception.
 */
public class NetworkAuthenticationRequiredException extends HttpException {

    public NetworkAuthenticationRequiredException() {
        super(Response.Status.NETWORK_AUTHENTICATION_REQUIRED);
    }

    public NetworkAuthenticationRequiredException(String message) {
        super(Response.Status.NETWORK_AUTHENTICATION_REQUIRED, message);
    }

    public NetworkAuthenticationRequiredException(String message, Throwable cause) {
        super(Response.Status.NETWORK_AUTHENTICATION_REQUIRED, message, cause);
    }

    public NetworkAuthenticationRequiredException(Throwable cause) {
        super(Response.Status.NETWORK_AUTHENTICATION_REQUIRED, cause);
    }
}
