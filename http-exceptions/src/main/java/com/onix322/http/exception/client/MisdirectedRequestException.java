package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.ExtendedStatus;
import jakarta.ws.rs.core.Response;

/**
 * Status 421 Misdirected Request exception.
 */
public class MisdirectedRequestException extends HttpException {

    public MisdirectedRequestException() {
        super(ExtendedStatus.MISDIRECTED_REQUEST);
    }

    public MisdirectedRequestException(String message) {
        super(ExtendedStatus.MISDIRECTED_REQUEST, message);
    }

    public MisdirectedRequestException(String message, Throwable cause) {
        super(ExtendedStatus.MISDIRECTED_REQUEST, message, cause);
    }

    public MisdirectedRequestException(Throwable cause) {
        super(ExtendedStatus.MISDIRECTED_REQUEST, cause);
    }
}
