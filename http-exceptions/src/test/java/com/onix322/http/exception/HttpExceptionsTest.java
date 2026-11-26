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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HttpExceptionsTest {

    private record Case(Class<? extends HttpException> type, HttpStatusCode status) {
    }

    static Stream<Arguments> cases() {
        return Stream.of(
                Arguments.of(new Case(BadRequestException.class, HttpStatusCode.BAD_REQUEST)),
                Arguments.of(new Case(UnauthorizedException.class, HttpStatusCode.UNAUTHORIZED)),
                Arguments.of(new Case(PaymentRequiredException.class, HttpStatusCode.PAYMENT_REQUIRED)),
                Arguments.of(new Case(ForbiddenException.class, HttpStatusCode.FORBIDDEN)),
                Arguments.of(new Case(NotFoundException.class, HttpStatusCode.NOT_FOUND)),
                Arguments.of(new Case(MethodNotAllowedException.class, HttpStatusCode.METHOD_NOT_ALLOWED)),
                Arguments.of(new Case(NotAcceptableException.class, HttpStatusCode.NOT_ACCEPTABLE)),
                Arguments.of(new Case(ProxyAuthenticationRequiredException.class, HttpStatusCode.PROXY_AUTHENTICATION_REQUIRED)),
                Arguments.of(new Case(RequestTimeoutException.class, HttpStatusCode.REQUEST_TIMEOUT)),
                Arguments.of(new Case(ConflictException.class, HttpStatusCode.CONFLICT)),
                Arguments.of(new Case(GoneException.class, HttpStatusCode.GONE)),
                Arguments.of(new Case(LengthRequiredException.class, HttpStatusCode.LENGTH_REQUIRED)),
                Arguments.of(new Case(PreconditionFailedException.class, HttpStatusCode.PRECONDITION_FAILED)),
                Arguments.of(new Case(ContentTooLargeException.class, HttpStatusCode.CONTENT_TOO_LARGE)),
                Arguments.of(new Case(UriTooLongException.class, HttpStatusCode.URI_TOO_LONG)),
                Arguments.of(new Case(UnsupportedMediaTypeException.class, HttpStatusCode.UNSUPPORTED_MEDIA_TYPE)),
                Arguments.of(new Case(RangeNotSatisfiableException.class, HttpStatusCode.RANGE_NOT_SATISFIABLE)),
                Arguments.of(new Case(ExpectationFailedException.class, HttpStatusCode.EXPECTATION_FAILED)),
                Arguments.of(new Case(MisdirectedRequestException.class, HttpStatusCode.MISDIRECTED_REQUEST)),
                Arguments.of(new Case(UnprocessableContentException.class, HttpStatusCode.UNPROCESSABLE_CONTENT)),
                Arguments.of(new Case(LockedException.class, HttpStatusCode.LOCKED)),
                Arguments.of(new Case(FailedDependencyException.class, HttpStatusCode.FAILED_DEPENDENCY)),
                Arguments.of(new Case(TooEarlyException.class, HttpStatusCode.TOO_EARLY)),
                Arguments.of(new Case(UpgradeRequiredException.class, HttpStatusCode.UPGRADE_REQUIRED)),
                Arguments.of(new Case(PreconditionRequiredException.class, HttpStatusCode.PRECONDITION_REQUIRED)),
                Arguments.of(new Case(TooManyRequestsException.class, HttpStatusCode.TOO_MANY_REQUESTS)),
                Arguments.of(new Case(RequestHeaderFieldsTooLargeException.class, HttpStatusCode.REQUEST_HEADER_FIELDS_TOO_LARGE)),
                Arguments.of(new Case(UnavailableForLegalReasonsException.class, HttpStatusCode.UNAVAILABLE_FOR_LEGAL_REASONS)),
                Arguments.of(new Case(InternalServerErrorException.class, HttpStatusCode.INTERNAL_SERVER_ERROR)),
                Arguments.of(new Case(NotImplementedException.class, HttpStatusCode.NOT_IMPLEMENTED)),
                Arguments.of(new Case(BadGatewayException.class, HttpStatusCode.BAD_GATEWAY)),
                Arguments.of(new Case(ServiceUnavailableException.class, HttpStatusCode.SERVICE_UNAVAILABLE)),
                Arguments.of(new Case(GatewayTimeoutException.class, HttpStatusCode.GATEWAY_TIMEOUT)),
                Arguments.of(new Case(HttpVersionNotSupportedException.class, HttpStatusCode.HTTP_VERSION_NOT_SUPPORTED)),
                Arguments.of(new Case(VariantAlsoNegotiatesException.class, HttpStatusCode.VARIANT_ALSO_NEGOTIATES)),
                Arguments.of(new Case(InsufficientStorageException.class, HttpStatusCode.INSUFFICIENT_STORAGE)),
                Arguments.of(new Case(LoopDetectedException.class, HttpStatusCode.LOOP_DETECTED)),
                Arguments.of(new Case(NetworkAuthenticationRequiredException.class, HttpStatusCode.NETWORK_AUTHENTICATION_REQUIRED))
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void defaultConstructorCarriesStatus(Case testCase) throws Exception {
        HttpException exception = testCase.type().getDeclaredConstructor().newInstance();

        assertSame(testCase.status(), exception.getStatus());
        assertEquals(testCase.status().code(), exception.getStatusCode());
        assertEquals(testCase.status().reason(), exception.getMessage());
        assertEquals(testCase.status().reason(), exception.getReason());
    }

    @ParameterizedTest
    @MethodSource("cases")
    void messageConstructorOverridesReason(Case testCase) throws Exception {
        HttpException exception = testCase.type().getDeclaredConstructor(String.class).newInstance("custom");

        assertEquals("custom", exception.getMessage());
        assertSame(testCase.status(), exception.getStatus());
    }
}
