package com.onix322.app.vehicle.resource.controller;

import com.onix322.app.vehicle.resource.model.impl.dto.VanAddDto;
import com.onix322.app.vehicle.resource.model.impl.dto.VanUpdateDto;
import com.onix322.app.vehicle.resource.service.VanService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/vehicle/van")
@Singleton
public class VanController {

    private final VanService vanService;

    @Inject
    public VanController(VanService vanService) {
        this.vanService = vanService;
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(VanAddDto van) {
        return Response.status(Response.Status.CREATED)
                .entity(vanService.create(van))
                .build();
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.ok(vanService.getAllVans())
                .build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") UUID id) {
        return Response.ok(vanService.getById(id))
                .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") UUID id, VanUpdateDto van) {
        return Response.ok(vanService.update(id, van))
                .build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") UUID id) {
        return Response.ok(vanService.delete(id))
                .build();

    }
}
