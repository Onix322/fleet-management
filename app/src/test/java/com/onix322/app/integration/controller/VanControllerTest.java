package com.onix322.app.integration.controller;

import com.onix322.app.vehicle.resource.controller.VanController;
import com.onix322.app.vehicle.resource.model.impl.Van;
import com.onix322.app.vehicle.resource.service.VanService;
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
        this.vanController = new VanController(this.vanService);
    }

    @AfterEach
    public void tearDownEach() {

    }

    @ParameterizedTest
    @MethodSource("createdScenarios")
    public void testCreatedMethodWhenException(Response.Status expectedStatus, Exception thrown) {
        Van van = new Van();

        if (thrown == null) {
            when(this.vanService.create(van)).thenReturn(van);
        } else {
            when(this.vanService.create(van)).thenThrow(thrown);
        }

        try (Response response = this.vanController.create(van)) {
            assertEquals(expectedStatus.getStatusCode(), response.getStatus());

            if (thrown == null) {
                assertNotNull(response.getEntity());
                assertEquals(van, response.getEntity());
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
        Van van = null;

        if (thrown == null) {
            van = new Van();
            van.setId(id);
            when(this.vanService.getById(id)).thenReturn(van);
        } else {
            when(this.vanService.getById(id)).thenThrow(thrown);
        }

        Response response = this.vanController.getById(id);
        assertEquals(expectedStatus.getStatusCode(), response.getStatus());

        if (thrown == null) {
            assertInstanceOf(Van.class, response.getEntity());
            assertSame(van, response.getEntity());
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
