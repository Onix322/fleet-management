package com.onix322.app.image.resource.controller;

import com.onix322.app.image.resource.model.impl.dao.ImageProfile;
import com.onix322.app.image.resource.service.ImageProfileService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/image/profile")
public class ImageProfileController {

    private final ImageProfileService service;

    @Inject
    public ImageProfileController(ImageProfileService service) {
        this.service = service;
    }

    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(ImageProfile imageProfile) {
        return Response.status(Response.Status.CREATED)
                .entity(service.create(imageProfile))
                .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") UUID id, ImageProfile imageProfileController) {
        return Response.status(Response.Status.CREATED)
                .entity(service.update(id, imageProfileController))
                .build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") UUID id) {
        return Response.status(Response.Status.OK)
                .entity(service.delete(id))
                .build();
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        return Response.ok(service.getAll()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") UUID id) {
        return Response.status(Response.Status.OK)
                .entity(service.getById(id))
                .build();
    }
}
