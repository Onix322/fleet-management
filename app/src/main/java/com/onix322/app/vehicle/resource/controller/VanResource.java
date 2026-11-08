package com.onix322.app.vehicle.resource.controller;

import com.onix322.app.vehicle.resource.model.impl.Van;
import com.onix322.app.vehicle.resource.service.VanService;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.AllArgsConstructor;

@Path("/")
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
}
