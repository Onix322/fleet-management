package com.onix322.http.exception.client;

import com.onix322.http.exception.HttpException;
import jakarta.ws.rs.core.Response;

/**
 * Status 402 Payment Required exception.
 */
public class PaymentRequiredException extends HttpException {

    public PaymentRequiredException() {
        super(Response.Status.PAYMENT_REQUIRED);
    }

    public PaymentRequiredException(String message) {
        super(Response.Status.PAYMENT_REQUIRED, message);
    }

    public PaymentRequiredException(String message, Throwable cause) {
        super(Response.Status.PAYMENT_REQUIRED, message, cause);
    }

    public PaymentRequiredException(Throwable cause) {
        super(Response.Status.PAYMENT_REQUIRED, cause);
    }
}
