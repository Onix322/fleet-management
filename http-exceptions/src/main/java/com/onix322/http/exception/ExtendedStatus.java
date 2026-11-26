package com.onix322.http.exception;

import jakarta.ws.rs.core.Response;

/**
 * Status codes that are registered as HTTP errors but are not constants of
 * {@link Response.Status} in the Jakarta REST API.
 */
public record ExtendedStatus(int code, String reason) implements Response.StatusType {

    public static final Response.StatusType MISDIRECTED_REQUEST = new ExtendedStatus(421, "Misdirected Request");
    public static final Response.StatusType UNPROCESSABLE_CONTENT = new ExtendedStatus(422, "Unprocessable Content");
    public static final Response.StatusType LOCKED = new ExtendedStatus(423, "Locked");
    public static final Response.StatusType FAILED_DEPENDENCY = new ExtendedStatus(424, "Failed Dependency");
    public static final Response.StatusType TOO_EARLY = new ExtendedStatus(425, "Too Early");
    public static final Response.StatusType UPGRADE_REQUIRED = new ExtendedStatus(426, "Upgrade Required");
    public static final Response.StatusType VARIANT_ALSO_NEGOTIATES = new ExtendedStatus(506, "Variant Also Negotiates");
    public static final Response.StatusType INSUFFICIENT_STORAGE = new ExtendedStatus(507, "Insufficient Storage");
    public static final Response.StatusType LOOP_DETECTED = new ExtendedStatus(508, "Loop Detected");

    @Override
    public int getStatusCode() {
        return code;
    }

    @Override
    public String getReasonPhrase() {
        return reason;
    }

    @Override
    public Response.Status.Family getFamily() {
        return Response.Status.Family.familyOf(code);
    }
}
