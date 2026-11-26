package com.onix322.http.exception.webdav;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.HttpStatusCode;

/**
 * Status Locked exception.
 */
public class LockedException extends HttpException {

    public LockedException() {
        super(HttpStatusCode.LOCKED);
    }

    public LockedException(String message) {
        super(HttpStatusCode.LOCKED, message);
    }

    public LockedException(String message, Throwable cause) {
        super(HttpStatusCode.LOCKED, message, cause);
    }

    public LockedException(Throwable cause) {
        super(HttpStatusCode.LOCKED, cause);
    }
}
