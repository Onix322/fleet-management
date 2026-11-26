package com.onix322.http.exception.server;

import com.onix322.http.exception.HttpException;
import com.onix322.http.exception.ExtendedStatus;
import jakarta.ws.rs.core.Response;

/**
 * Status 506 Variant Also Negotiates exception.
 */
public class VariantAlsoNegotiatesException extends HttpException {

    public VariantAlsoNegotiatesException() {
        super(ExtendedStatus.VARIANT_ALSO_NEGOTIATES);
    }

    public VariantAlsoNegotiatesException(String message) {
        super(ExtendedStatus.VARIANT_ALSO_NEGOTIATES, message);
    }

    public VariantAlsoNegotiatesException(String message, Throwable cause) {
        super(ExtendedStatus.VARIANT_ALSO_NEGOTIATES, message, cause);
    }

    public VariantAlsoNegotiatesException(Throwable cause) {
        super(ExtendedStatus.VARIANT_ALSO_NEGOTIATES, cause);
    }
}
