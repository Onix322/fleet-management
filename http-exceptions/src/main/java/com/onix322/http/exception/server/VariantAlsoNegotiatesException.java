package com.onix322.http.exception.server;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.HttpStatusCode;

/**
 * Status Variant Also Negotiates exception.
 */
public class VariantAlsoNegotiatesException extends HttpException {

    public VariantAlsoNegotiatesException() {
        super(HttpStatusCode.VARIANT_ALSO_NEGOTIATES);
    }

    public VariantAlsoNegotiatesException(String message) {
        super(HttpStatusCode.VARIANT_ALSO_NEGOTIATES, message);
    }

    public VariantAlsoNegotiatesException(String message, Throwable cause) {
        super(HttpStatusCode.VARIANT_ALSO_NEGOTIATES, message, cause);
    }

    public VariantAlsoNegotiatesException(Throwable cause) {
        super(HttpStatusCode.VARIANT_ALSO_NEGOTIATES, cause);
    }
}
