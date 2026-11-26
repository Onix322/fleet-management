package com.onix322.http.exception;

import java.io.Serial;

/**
 * Base type for every HTTP/WebDAV status exception.
 *
 * <p>All status exceptions are unchecked, so a single {@code catch (HttpException e)}
 * or a single {@code ExceptionMapper<HttpException>} handles every code.</p>
 */
public class HttpException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final HttpStatusCode status;

    public HttpException(HttpStatusCode status) {
        super(requireStatus(status).reason());
        this.status = status;
    }

    public HttpException(HttpStatusCode status, String message) {
        super(message != null ? message : requireStatus(status).reason());
        this.status = status;
    }

    public HttpException(HttpStatusCode status, Throwable cause) {
        super(requireStatus(status).reason(), cause);
        this.status = status;
    }

    public HttpException(HttpStatusCode status, String message, Throwable cause) {
        super(message != null ? message : requireStatus(status).reason(), cause);
        this.status = status;
    }

    public HttpStatusCode getStatus() {
        return status;
    }

    public int getStatusCode() {
        return status.code();
    }

    public String getReason() {
        return status.reason();
    }

    private static HttpStatusCode requireStatus(HttpStatusCode status) {
        if (status == null) {
            throw new IllegalArgumentException("status must not be null");
        }
        return status;
    }
}
