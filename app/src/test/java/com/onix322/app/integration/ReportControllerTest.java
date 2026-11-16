package com.onix322.app.integration;

import com.onix322.app.report.resource.controller.ReportController;
import com.onix322.app.report.resource.model.impl.InspectionReport;
import com.onix322.app.report.resource.service.InspectionReportService;
import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ReportControllerTest {

    @Mock
    private InspectionReportService reportService;

    private ReportController reportController;

    @BeforeEach
    public void setUpEach() {
        this.reportController = new ReportController(this.reportService);
    }

    @ParameterizedTest
    @MethodSource("createScenarios")
    public void testCreateMethod(Response.Status expectedStatus, Exception thrown) {
        // Given
        InspectionReport inspectionReport = new InspectionReport();

        if (thrown == null) {
            when(this.reportService.create(inspectionReport)).thenReturn(inspectionReport);
        } else {
            when(this.reportService.create(inspectionReport)).thenThrow(thrown);
        }

        // When
        try (Response response = this.reportController.create(inspectionReport)) {
            // Then
            assertEquals(expectedStatus.getStatusCode(), response.getStatus());

            if (thrown == null) {
                assertNotNull(response.getEntity());
                assertEquals(inspectionReport, response.getEntity());
            }
        }
    }

    @Test
    public void testGetAllMethod() {
        // When
        Response response = this.reportController.getAll();

        // Then
        assertEquals(200, response.getStatus());
        assertNotNull(response.getEntity());
    }

    @ParameterizedTest
    @MethodSource("getByIdScenarios")
    public void testGetById(Response.Status expectedStatus, Exception thrown) {
        // Given
        InspectionReport inspectionReport = new InspectionReport();
        UUID reportId = UUID.randomUUID();

        if (thrown == null) {
            when(this.reportService.getById(reportId)).thenReturn(inspectionReport);
        } else {
            when(this.reportService.getById(reportId)).thenThrow(thrown);
        }

        // When
        Response response = this.reportController.getById(reportId);

        // Then
        assertEquals(expectedStatus.getStatusCode(), response.getStatus());

        if (thrown == null) {
            assertNotNull(response.getEntity());
            assertEquals(inspectionReport, response.getEntity());
        }
    }

    @ParameterizedTest
    @MethodSource("deleteScenarios")
    public void testDeleteMethod(Response.Status expectedStatus, Boolean expectedSuccess, Throwable thr) {
        // Given
        UUID reportId = UUID.randomUUID();

        if (thr == null) {
            when(this.reportService.delete(reportId)).thenReturn(expectedSuccess);
        } else {
            when(this.reportService.delete(reportId)).thenThrow(thr);
        }

        // When
        try (Response response = this.reportController.delete(reportId)) {
            // Then
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
                Arguments.of(Response.Status.NOT_FOUND, new NotFoundException("Report not found")),
                Arguments.of(Response.Status.INTERNAL_SERVER_ERROR, new RuntimeException("Unexpected database error"))
        );
    }

    static Stream<Arguments> createScenarios() {
        return Stream.of(
                Arguments.of(Response.Status.CREATED, (Exception) null),
                Arguments.of(Response.Status.BAD_REQUEST, new IllegalArgumentException("Invalid report data")),
                Arguments.of(Response.Status.INTERNAL_SERVER_ERROR, new RuntimeException("Unexpected database error"))
        );
    }
}