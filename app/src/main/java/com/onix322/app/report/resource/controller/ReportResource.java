package com.onix322.app.report.resource.controller;

import com.onix322.app.report.resource.model.impl.InspectionReport;
import com.onix322.app.report.resource.service.InspectionReportService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;

import java.util.UUID;

@Path("/report")
@AllArgsConstructor(onConstructor_ = @Inject)
@Singleton
public class ReportResource {

    private InspectionReportService inspectionReportService;

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(InspectionReport report) {
        return Response.ok(inspectionReportService.create(report)).build();
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.ok(inspectionReportService.getAll()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") UUID id) {
        return Response.ok(inspectionReportService.getById(id)).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") UUID id, InspectionReport report) {
        return Response.ok(inspectionReportService.update(id, report)).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") UUID id) {
        return Response.ok(inspectionReportService.delete(id)).build();
    }
}
