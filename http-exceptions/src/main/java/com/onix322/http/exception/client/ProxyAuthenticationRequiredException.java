package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.HttpStatusCode;

/**
 * Status Proxy Authentication Required exception.
 */
public class ProxyAuthenticationRequiredException extends HttpException {

    public ProxyAuthenticationRequiredException() {
        super(HttpStatusCode.PROXY_AUTHENTICATION_REQUIRED);
    }

    public ProxyAuthenticationRequiredException(String message) {
        super(HttpStatusCode.PROXY_AUTHENTICATION_REQUIRED, message);
    }

    public ProxyAuthenticationRequiredException(String message, Throwable cause) {
        super(HttpStatusCode.PROXY_AUTHENTICATION_REQUIRED, message, cause);
    }

    public ProxyAuthenticationRequiredException(Throwable cause) {
        super(HttpStatusCode.PROXY_AUTHENTICATION_REQUIRED, cause);
    }
}
