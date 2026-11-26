package com.onix322.http.exception;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

/**
 * Dispatches every {@link HttpException} to a JSON response carrying its status.
 */
@Produces(MediaType.APPLICATION_JSON)
public class HttpExceptionMapper implements ExceptionMapper<HttpException> {

    @Override
    public Response toResponse(HttpException exception) {
        return Response.status(exception.getStatusCode())
                .type(MediaType.APPLICATION_JSON)
                .entity(new HttpError(exception.getStatusCode(), exception.getReason(), exception.getMessage()))
                .build();
    }

    public record HttpError(int code, String reason, String message) {
    }
}
