package com.onix322.http.exception.webdav;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.ExtendedStatus;
import jakarta.ws.rs.core.Response;

/**
 * Status 508 Loop Detected exception.
 */
public class LoopDetectedException extends HttpException {

    public LoopDetectedException() {
        super(ExtendedStatus.LOOP_DETECTED);
    }

    public LoopDetectedException(String message) {
        super(ExtendedStatus.LOOP_DETECTED, message);
    }

    public LoopDetectedException(String message, Throwable cause) {
        super(ExtendedStatus.LOOP_DETECTED, message, cause);
    }

    public LoopDetectedException(Throwable cause) {
        super(ExtendedStatus.LOOP_DETECTED, cause);
    }
}
