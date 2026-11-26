package com.onix322.http.exception.webdav;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.ExtendedStatus;
import jakarta.ws.rs.core.Response;

/**
 * Status 423 Locked exception.
 */
public class LockedException extends HttpException {

    public LockedException() {
        super(ExtendedStatus.LOCKED);
    }

    public LockedException(String message) {
        super(ExtendedStatus.LOCKED, message);
    }

    public LockedException(String message, Throwable cause) {
        super(ExtendedStatus.LOCKED, message, cause);
    }

    public LockedException(Throwable cause) {
        super(ExtendedStatus.LOCKED, cause);
    }
}
