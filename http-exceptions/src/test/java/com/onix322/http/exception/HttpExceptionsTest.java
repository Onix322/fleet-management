package com.onix322.http.exception;

import com.onix322.http.exception.client.BadRequestException;
import com.onix322.http.exception.client.ConflictException;
import com.onix322.http.exception.client.ContentTooLargeException;
import com.onix322.http.exception.client.ExpectationFailedException;
import com.onix322.http.exception.client.ForbiddenException;
import com.onix322.http.exception.client.GoneException;
import com.onix322.http.exception.client.LengthRequiredException;
import com.onix322.http.exception.client.MethodNotAllowedException;
import com.onix322.http.exception.client.MisdirectedRequestException;
import com.onix322.http.exception.client.NotAcceptableException;
import com.onix322.http.exception.client.NotFoundException;
import com.onix322.http.exception.client.PaymentRequiredException;
import com.onix322.http.exception.client.PreconditionFailedException;
import com.onix322.http.exception.client.PreconditionRequiredException;
import com.onix322.http.exception.client.ProxyAuthenticationRequiredException;
import com.onix322.http.exception.client.RangeNotSatisfiableException;
import com.onix322.http.exception.client.RequestHeaderFieldsTooLargeException;
import com.onix322.http.exception.client.RequestTimeoutException;
import com.onix322.http.exception.client.TooEarlyException;
import com.onix322.http.exception.client.TooManyRequestsException;
import com.onix322.http.exception.client.UnauthorizedException;
import com.onix322.http.exception.client.UnavailableForLegalReasonsException;
import com.onix322.http.exception.client.UnprocessableContentException;
import com.onix322.http.exception.client.UnsupportedMediaTypeException;
import com.onix322.http.exception.client.UpgradeRequiredException;
import com.onix322.http.exception.client.UriTooLongException;
import com.onix322.http.exception.server.BadGatewayException;
import com.onix322.http.exception.server.GatewayTimeoutException;
import com.onix322.http.exception.server.HttpVersionNotSupportedException;
import com.onix322.http.exception.server.InternalServerErrorException;
import com.onix322.http.exception.server.NetworkAuthenticationRequiredException;
import com.onix322.http.exception.server.NotImplementedException;
import com.onix322.http.exception.server.ServiceUnavailableException;
import com.onix322.http.exception.server.VariantAlsoNegotiatesException;
import com.onix322.http.exception.webdav.FailedDependencyException;
import com.onix322.http.exception.webdav.InsufficientStorageException;
import com.onix322.http.exception.webdav.LockedException;
import com.onix322.http.exception.webdav.LoopDetectedException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import com.onix322.http.exception.ExtendedStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HttpExceptionsTest {

    private record Case(Class<? extends HttpException> type, Response.StatusType status) {
    }

    static Stream<Arguments> cases() {
        return Stream.of(
                Arguments.of(new Case(BadRequestException.class, Response.Status.BAD_REQUEST)),
                Arguments.of(new Case(UnauthorizedException.class, Response.Status.UNAUTHORIZED)),
                Arguments.of(new Case(PaymentRequiredException.class, Response.Status.PAYMENT_REQUIRED)),
                Arguments.of(new Case(ForbiddenException.class, Response.Status.FORBIDDEN)),
                Arguments.of(new Case(NotFoundException.class, Response.Status.NOT_FOUND)),
                Arguments.of(new Case(MethodNotAllowedException.class, Response.Status.METHOD_NOT_ALLOWED)),
                Arguments.of(new Case(NotAcceptableException.class, Response.Status.NOT_ACCEPTABLE)),
                Arguments.of(new Case(ProxyAuthenticationRequiredException.class, Response.Status.PROXY_AUTHENTICATION_REQUIRED)),
                Arguments.of(new Case(RequestTimeoutException.class, Response.Status.REQUEST_TIMEOUT)),
                Arguments.of(new Case(ConflictException.class, Response.Status.CONFLICT)),
                Arguments.of(new Case(GoneException.class, Response.Status.GONE)),
                Arguments.of(new Case(LengthRequiredException.class, Response.Status.LENGTH_REQUIRED)),
                Arguments.of(new Case(PreconditionFailedException.class, Response.Status.PRECONDITION_FAILED)),
                Arguments.of(new Case(ContentTooLargeException.class, Response.Status.REQUEST_ENTITY_TOO_LARGE)),
                Arguments.of(new Case(UriTooLongException.class, Response.Status.REQUEST_URI_TOO_LONG)),
                Arguments.of(new Case(UnsupportedMediaTypeException.class, Response.Status.UNSUPPORTED_MEDIA_TYPE)),
                Arguments.of(new Case(RangeNotSatisfiableException.class, Response.Status.REQUESTED_RANGE_NOT_SATISFIABLE)),
                Arguments.of(new Case(ExpectationFailedException.class, Response.Status.EXPECTATION_FAILED)),
                Arguments.of(new Case(MisdirectedRequestException.class, ExtendedStatus.MISDIRECTED_REQUEST)),
                Arguments.of(new Case(UnprocessableContentException.class, ExtendedStatus.UNPROCESSABLE_CONTENT)),
                Arguments.of(new Case(LockedException.class, ExtendedStatus.LOCKED)),
                Arguments.of(new Case(FailedDependencyException.class, ExtendedStatus.FAILED_DEPENDENCY)),
                Arguments.of(new Case(TooEarlyException.class, ExtendedStatus.TOO_EARLY)),
                Arguments.of(new Case(UpgradeRequiredException.class, ExtendedStatus.UPGRADE_REQUIRED)),
                Arguments.of(new Case(PreconditionRequiredException.class, Response.Status.PRECONDITION_REQUIRED)),
                Arguments.of(new Case(TooManyRequestsException.class, Response.Status.TOO_MANY_REQUESTS)),
                Arguments.of(new Case(RequestHeaderFieldsTooLargeException.class, Response.Status.REQUEST_HEADER_FIELDS_TOO_LARGE)),
                Arguments.of(new Case(UnavailableForLegalReasonsException.class, Response.Status.UNAVAILABLE_FOR_LEGAL_REASONS)),
                Arguments.of(new Case(InternalServerErrorException.class, Response.Status.INTERNAL_SERVER_ERROR)),
                Arguments.of(new Case(NotImplementedException.class, Response.Status.NOT_IMPLEMENTED)),
                Arguments.of(new Case(BadGatewayException.class, Response.Status.BAD_GATEWAY)),
                Arguments.of(new Case(ServiceUnavailableException.class, Response.Status.SERVICE_UNAVAILABLE)),
                Arguments.of(new Case(GatewayTimeoutException.class, Response.Status.GATEWAY_TIMEOUT)),
                Arguments.of(new Case(HttpVersionNotSupportedException.class, Response.Status.HTTP_VERSION_NOT_SUPPORTED)),
                Arguments.of(new Case(VariantAlsoNegotiatesException.class, ExtendedStatus.VARIANT_ALSO_NEGOTIATES)),
                Arguments.of(new Case(InsufficientStorageException.class, ExtendedStatus.INSUFFICIENT_STORAGE)),
                Arguments.of(new Case(LoopDetectedException.class, ExtendedStatus.LOOP_DETECTED)),
                Arguments.of(new Case(NetworkAuthenticationRequiredException.class, Response.Status.NETWORK_AUTHENTICATION_REQUIRED))
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void defaultConstructorCarriesStatus(Case testCase) throws Exception {
        HttpException exception = testCase.type().getDeclaredConstructor().newInstance();

        assertTrue(() -> testCase.status().getStatusCode() == exception.getStatusCode());
        assertEquals(testCase.status().getStatusCode(), exception.getStatusCode());
        assertEquals(testCase.status().getReasonPhrase(), exception.getMessage());
        assertEquals(testCase.status().getReasonPhrase(), exception.getReason());
    }

    @ParameterizedTest
    @MethodSource("cases")
    void messageConstructorOverridesReason(Case testCase) throws Exception {
        HttpException exception = testCase.type().getDeclaredConstructor(String.class).newInstance("custom");

        assertEquals("custom", exception.getMessage());
        assertTrue(() -> testCase.status().getStatusCode() == exception.getStatusCode());
    }

    @Test
    void mapperDispatchesToJsonResponse() {
        HttpExceptionMapper mapper = new HttpExceptionMapper();
        HttpException exception = new NotFoundException("missing");

        try (Response response = mapper.toResponse(exception)) {
            assertEquals(404, response.getStatus());
            assertEquals(MediaType.APPLICATION_JSON_TYPE, response.getMediaType());

            HttpExceptionMapper.HttpError error = (HttpExceptionMapper.HttpError) response.getEntity();
            assertEquals(404, error.code());
            assertEquals("Not Found", error.reason());
            assertEquals("missing", error.message());
        }
    }
}
