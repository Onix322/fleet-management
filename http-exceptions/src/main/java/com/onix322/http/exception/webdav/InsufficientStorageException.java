package com.onix322.http.exception.webdav;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.HttpStatusCode;

/**
 * Status Insufficient Storage exception.
 */
public class InsufficientStorageException extends HttpException {

    public InsufficientStorageException() {
        super(HttpStatusCode.INSUFFICIENT_STORAGE);
    }

    public InsufficientStorageException(String message) {
        super(HttpStatusCode.INSUFFICIENT_STORAGE, message);
    }

    public InsufficientStorageException(String message, Throwable cause) {
        super(HttpStatusCode.INSUFFICIENT_STORAGE, message, cause);
    }

    public InsufficientStorageException(Throwable cause) {
        super(HttpStatusCode.INSUFFICIENT_STORAGE, cause);
    }
}
