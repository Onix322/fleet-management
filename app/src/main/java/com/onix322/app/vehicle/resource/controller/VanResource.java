package com.onix322.app.vehicle.resource.controller;

import com.onix322.app.vehicle.resource.model.impl.Van;
import com.onix322.app.vehicle.resource.service.VanService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;

import java.util.UUID;

@Path("/vehicle/van")
@AllArgsConstructor(onConstructor_ = @Inject)
@Singleton
public class VanResource {

    private VanService vanService;

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(Van van) {
        return Response.ok(vanService.create(van)).build();
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.ok(vanService.getAll()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") UUID id) {
        return Response.ok(vanService.getById(id)).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") UUID id, Van van) {
        return Response.ok(vanService.update(id, van)).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") UUID id) {
        return Response.ok(vanService.delete(id)).build();
    }
}
