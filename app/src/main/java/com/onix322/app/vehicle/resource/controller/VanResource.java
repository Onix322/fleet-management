package com.onix322.app.vehicle.resource.controller;

import com.onix322.app.vehicle.resource.model.impl.Van;
import com.onix322.app.vehicle.resource.service.VanService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;

@Path("/vehicle")
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
}
