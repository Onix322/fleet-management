package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import jakarta.ws.rs.core.Response;

/**
 * Status 407 Proxy Authentication Required exception.
 */
public class ProxyAuthenticationRequiredException extends HttpException {

    public ProxyAuthenticationRequiredException() {
        super(Response.Status.PROXY_AUTHENTICATION_REQUIRED);
    }

    public ProxyAuthenticationRequiredException(String message) {
        super(Response.Status.PROXY_AUTHENTICATION_REQUIRED, message);
    }

    public ProxyAuthenticationRequiredException(String message, Throwable cause) {
        super(Response.Status.PROXY_AUTHENTICATION_REQUIRED, message, cause);
    }

    public ProxyAuthenticationRequiredException(Throwable cause) {
        super(Response.Status.PROXY_AUTHENTICATION_REQUIRED, cause);
    }
}
