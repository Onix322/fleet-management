package com.onix322.app.integration.vehicle;

import com.onix322.app.vehicle.resource.controller.VanController;
import com.onix322.app.vehicle.resource.model.impl.dao.Van;
import com.onix322.app.vehicle.resource.model.impl.dto.VanAddDto;
import com.onix322.app.vehicle.resource.model.impl.dto.VanReturnDto;
import com.onix322.app.vehicle.resource.service.VanService;
import com.onix322.app.vehicle.resource.service.VehicleService;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VanControllerTest {

    @Mock
    private VanService vanService;

    private VanController vanController;

    @BeforeEach
    public void setUpEach() {
        this.vanController = new VanController(vanService);
    }

    @AfterEach
    public void tearDownEach() {

    }

    @ParameterizedTest
    @MethodSource("createdScenarios")
    public void testCreatedMethodWhenException(Response.Status expectedStatus, Exception thrown) {
        VanAddDto given = new VanAddDto();
        VanReturnDto expected = new VanReturnDto();

        if (thrown == null) {
            when(this.vanService.create(given)).thenReturn(expected);
        } else {
            when(this.vanService.create(given)).thenThrow(thrown);
        }

        try (Response response = this.vanController.create(given)) {
            assertEquals(expectedStatus.getStatusCode(), response.getStatus());

            if (thrown == null) {
                assertNotNull(response.getEntity());
                assertEquals(expected, response.getEntity());
            }
        }
    }

    @Test
    public void testGetAllMethod() {
        Response response = this.vanController.getAll();
        assertEquals(200, response.getStatus());
        assertNotNull(response.getEntity());
        assertInstanceOf(List.class, response.getEntity());
    }

    @ParameterizedTest
    @MethodSource("getByIdScenarios")
    public void testGetById(Response.Status expectedStatus, Exception thrown) {
        UUID id = UUID.randomUUID();
        Van expected = null;

        if (thrown == null) {
            expected = new Van();
            expected.setId(id);
            when(this.vanService.getById(id)).thenReturn(expected);
        } else {
            when(this.vanService.getById(id)).thenThrow(thrown);
        }

        Response response = this.vanController.getById(id);
        assertEquals(expectedStatus.getStatusCode(), response.getStatus());

        if (thrown == null) {
            assertInstanceOf(Van.class, response.getEntity());
            assertSame(expected, response.getEntity());
        }
    }

    @ParameterizedTest
    @MethodSource("deleteScenarios")
    public void testDeleteMethod(Response.Status expectedStatus, Boolean expectedSuccess, Throwable thr) {
        Van van = new Van();
        van.setId(UUID.randomUUID());

        if (thr == null) {
            when(this.vanService.delete(van.getId())).thenReturn(expectedSuccess);
        } else {
            when(this.vanService.delete(van.getId())).thenThrow(thr);
        }

        try (Response response = this.vanController.delete(van.getId())) {
            assertEquals(expectedStatus.getStatusCode(), response.getStatus());

            if (thr == null) {
                assertNotNull(response.getEntity());
                assertEquals(true, response.getEntity());
            }
        }
    }

    static Stream<Arguments> deleteScenarios() {
        return Stream.of(
                Arguments.of(Response.Status.OK, Boolean.TRUE, null),
                Arguments.of(Response.Status.INTERNAL_SERVER_ERROR, Boolean.FALSE, new RuntimeException("Unexpected database error"))
        );
    }

    static Stream<Arguments> getByIdScenarios() {
        return Stream.of(
                Arguments.of(Response.Status.OK, (Exception) null),
                Arguments.of(Response.Status.NOT_FOUND, new NotFoundException("Vehicle not found")),
                Arguments.of(Response.Status.INTERNAL_SERVER_ERROR, new RuntimeException("Unexpected database error"))
        );
    }

    static Stream<Arguments> createdScenarios() {
        return Stream.of(
                Arguments.of(Response.Status.CREATED, (Exception) null),
                Arguments.of(Response.Status.BAD_REQUEST, new IllegalArgumentException("Invalid vehicle data")),
                Arguments.of(Response.Status.INTERNAL_SERVER_ERROR, new RuntimeException("Unexpected database error"))
        );
    }
}
