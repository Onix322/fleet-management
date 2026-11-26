package com.onix322.app.integration.vehicle;

import com.onix322.app.vehicle.resource.controller.VanController;
import com.onix322.app.vehicle.resource.model.impl.dao.Van;
import com.onix322.app.vehicle.resource.model.impl.dto.VanAddDto;
import com.onix322.app.vehicle.resource.model.impl.dto.VehicleMotorizedDto;
import com.onix322.app.vehicle.resource.service.VanService;
import com.onix322.http.exception.client.BadRequestException;
import com.onix322.http.exception.client.NotFoundException;
import com.onix322.http.exception.server.InternalServerErrorException;
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
        VehicleMotorizedDto expected = VehicleMotorizedDto.builder().build();

        if (thrown != null) {
            when(this.vanService.create(given)).thenThrow(thrown);
            assertThrows(thrown.getClass(), () -> this.vanController.create(given));
            return;
        }

        when(this.vanService.create(given)).thenReturn(expected);
        Response response = this.vanController.create(given);
        assertEquals(expectedStatus.getStatusCode(), response.getStatus());

        assertNotNull(response.getEntity());
        assertEquals(expected, response.getEntity());
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

        if (thrown != null) {
            when(this.vanService.getById(id)).thenThrow(thrown);
            assertThrows(thrown.getClass(), () -> this.vanController.getById(id));
            return;
        }

        Van expected = new Van();
        expected.setId(id);
        when(this.vanService.getById(id)).thenReturn(expected);

        Response response = this.vanController.getById(id);
        assertEquals(expectedStatus.getStatusCode(), response.getStatus());

        assertInstanceOf(Van.class, response.getEntity());
        assertSame(expected, response.getEntity());
    }

    @ParameterizedTest
    @MethodSource("deleteScenarios")
    public void testDeleteMethod(Response.Status expectedStatus, Boolean expectedSuccess, Exception thr) {
        Van van = new Van();

        if (thr != null) {
            when(this.vanService.delete(van.getId())).thenThrow(thr);
            assertThrows(thr.getClass(), () -> this.vanController.delete(null));
            return;
        }

        van.setId(UUID.randomUUID());
        when(this.vanService.delete(van.getId())).thenReturn(expectedSuccess);

        Response response = this.vanController.delete(van.getId());
        assertEquals(expectedStatus.getStatusCode(), response.getStatus());

        assertNotNull(response.getEntity());
        assertEquals(expectedSuccess, response.getEntity());
    }

    static Stream<Arguments> deleteScenarios() {
        return Stream.of(
                Arguments.of(Response.Status.OK, Boolean.TRUE, null),
                Arguments.of(Response.Status.OK, Boolean.FALSE, null),
                Arguments.of(Response.Status.BAD_REQUEST, null, new BadRequestException("Unexpected database error"))
        );
    }

    static Stream<Arguments> getByIdScenarios() {
        return Stream.of(
                Arguments.of(Response.Status.OK, (Exception) null),
                Arguments.of(Response.Status.NOT_FOUND, new NotFoundException("Vehicle not found")),
                Arguments.of(Response.Status.INTERNAL_SERVER_ERROR, new InternalServerErrorException("Unexpected database error"))
        );
    }

    static Stream<Arguments> createdScenarios() {
        return Stream.of(
                Arguments.of(Response.Status.CREATED, (Exception) null),
                Arguments.of(Response.Status.BAD_REQUEST, new BadRequestException("Invalid vehicle data")),
                Arguments.of(Response.Status.INTERNAL_SERVER_ERROR, new InternalServerErrorException("Unexpected database error"))
        );
    }
}
