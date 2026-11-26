package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.HttpStatusCode;

/**
 * Status Unavailable For Legal Reasons exception.
 */
public class UnavailableForLegalReasonsException extends HttpException {

    public UnavailableForLegalReasonsException() {
        super(HttpStatusCode.UNAVAILABLE_FOR_LEGAL_REASONS);
    }

    public UnavailableForLegalReasonsException(String message) {
        super(HttpStatusCode.UNAVAILABLE_FOR_LEGAL_REASONS, message);
    }

    public UnavailableForLegalReasonsException(String message, Throwable cause) {
        super(HttpStatusCode.UNAVAILABLE_FOR_LEGAL_REASONS, message, cause);
    }

    public UnavailableForLegalReasonsException(Throwable cause) {
        super(HttpStatusCode.UNAVAILABLE_FOR_LEGAL_REASONS, cause);
    }
}
