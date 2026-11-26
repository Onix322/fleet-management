package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.HttpStatusCode;

/**
 * Status Payment Required exception.
 */
public class PaymentRequiredException extends HttpException {

    public PaymentRequiredException() {
        super(HttpStatusCode.PAYMENT_REQUIRED);
    }

    public PaymentRequiredException(String message) {
        super(HttpStatusCode.PAYMENT_REQUIRED, message);
    }

    public PaymentRequiredException(String message, Throwable cause) {
        super(HttpStatusCode.PAYMENT_REQUIRED, message, cause);
    }

    public PaymentRequiredException(Throwable cause) {
        super(HttpStatusCode.PAYMENT_REQUIRED, cause);
    }
}
