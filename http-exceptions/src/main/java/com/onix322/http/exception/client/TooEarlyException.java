package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.ExtendedStatus;
import jakarta.ws.rs.core.Response;

/**
 * Status 425 Too Early exception.
 */
public class TooEarlyException extends HttpException {

    public TooEarlyException() {
        super(ExtendedStatus.TOO_EARLY);
    }

    public TooEarlyException(String message) {
        super(ExtendedStatus.TOO_EARLY, message);
    }

    public TooEarlyException(String message, Throwable cause) {
        super(ExtendedStatus.TOO_EARLY, message, cause);
    }

    public TooEarlyException(Throwable cause) {
        super(ExtendedStatus.TOO_EARLY, cause);
    }
}
