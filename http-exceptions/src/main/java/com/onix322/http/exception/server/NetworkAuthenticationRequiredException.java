package com.onix322.http.exception.server;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.HttpStatusCode;

/**
 * Status Network Authentication Required exception.
 */
public class NetworkAuthenticationRequiredException extends HttpException {

    public NetworkAuthenticationRequiredException() {
        super(HttpStatusCode.NETWORK_AUTHENTICATION_REQUIRED);
    }

    public NetworkAuthenticationRequiredException(String message) {
        super(HttpStatusCode.NETWORK_AUTHENTICATION_REQUIRED, message);
    }

    public NetworkAuthenticationRequiredException(String message, Throwable cause) {
        super(HttpStatusCode.NETWORK_AUTHENTICATION_REQUIRED, message, cause);
    }

    public NetworkAuthenticationRequiredException(Throwable cause) {
        super(HttpStatusCode.NETWORK_AUTHENTICATION_REQUIRED, cause);
    }
}
