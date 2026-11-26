package com.onix322.http.exception;

import jakarta.ws.rs.core.Response;
import lombok.Getter;

import java.io.Serial;

/**
 * Base type for every HTTP/WebDAV error status exception.
 *
 * <p>All status exceptions are unchecked, so a single {@code catch (HttpException e)}
 * or a single {@link HttpExceptionMapper} handles every code.</p>
 */
public class HttpException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Response.StatusType status;

    public HttpException(Response.StatusType status) {
        super(requireStatus(status).getReasonPhrase());
        this.status = status;
    }

    public HttpException(Response.StatusType status, String message) {
        super(message != null ? message : requireStatus(status).getReasonPhrase());
        this.status = status;
    }

    public HttpException(Response.StatusType status, Throwable cause) {
        super(requireStatus(status).getReasonPhrase(), cause);
        this.status = status;
    }

    public HttpException(Response.StatusType status, String message, Throwable cause) {
        super(message != null ? message : requireStatus(status).getReasonPhrase(), cause);
        this.status = status;
    }

    public int getStatusCode() {
        return status.getStatusCode();
    }

    public String getReason() {
        return status.getReasonPhrase();
    }

    private static Response.StatusType requireStatus(Response.StatusType status) {
        if (status == null) {
            throw new IllegalArgumentException("status must not be null");
        }
        return status;
    }
}
