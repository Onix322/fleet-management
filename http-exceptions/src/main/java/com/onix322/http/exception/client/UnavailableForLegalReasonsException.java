package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import jakarta.ws.rs.core.Response;

/**
 * Status 451 Unavailable For Legal Reasons exception.
 */
public class UnavailableForLegalReasonsException extends HttpException {

    public UnavailableForLegalReasonsException() {
        super(Response.Status.UNAVAILABLE_FOR_LEGAL_REASONS);
    }

    public UnavailableForLegalReasonsException(String message) {
        super(Response.Status.UNAVAILABLE_FOR_LEGAL_REASONS, message);
    }

    public UnavailableForLegalReasonsException(String message, Throwable cause) {
        super(Response.Status.UNAVAILABLE_FOR_LEGAL_REASONS, message, cause);
    }

    public UnavailableForLegalReasonsException(Throwable cause) {
        super(Response.Status.UNAVAILABLE_FOR_LEGAL_REASONS, cause);
    }
}
