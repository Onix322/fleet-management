package com.onix322.http.exception.webdav;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.ExtendedStatus;
import jakarta.ws.rs.core.Response;

/**
 * Status 507 Insufficient Storage exception.
 */
public class InsufficientStorageException extends HttpException {

    public InsufficientStorageException() {
        super(ExtendedStatus.INSUFFICIENT_STORAGE);
    }

    public InsufficientStorageException(String message) {
        super(ExtendedStatus.INSUFFICIENT_STORAGE, message);
    }

    public InsufficientStorageException(String message, Throwable cause) {
        super(ExtendedStatus.INSUFFICIENT_STORAGE, message, cause);
    }

    public InsufficientStorageException(Throwable cause) {
        super(ExtendedStatus.INSUFFICIENT_STORAGE, cause);
    }
}
