package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.HttpStatusCode;

/**
 * Status Misdirected Request exception.
 */
public class MisdirectedRequestException extends HttpException {

    public MisdirectedRequestException() {
        super(HttpStatusCode.MISDIRECTED_REQUEST);
    }

    public MisdirectedRequestException(String message) {
        super(HttpStatusCode.MISDIRECTED_REQUEST, message);
    }

    public MisdirectedRequestException(String message, Throwable cause) {
        super(HttpStatusCode.MISDIRECTED_REQUEST, message, cause);
    }

    public MisdirectedRequestException(Throwable cause) {
        super(HttpStatusCode.MISDIRECTED_REQUEST, cause);
    }
}
