package com.onix322.http.exception.webdav;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.HttpStatusCode;

/**
 * Status Loop Detected exception.
 */
public class LoopDetectedException extends HttpException {

    public LoopDetectedException() {
        super(HttpStatusCode.LOOP_DETECTED);
    }

    public LoopDetectedException(String message) {
        super(HttpStatusCode.LOOP_DETECTED, message);
    }

    public LoopDetectedException(String message, Throwable cause) {
        super(HttpStatusCode.LOOP_DETECTED, message, cause);
    }

    public LoopDetectedException(Throwable cause) {
        super(HttpStatusCode.LOOP_DETECTED, cause);
    }
}
