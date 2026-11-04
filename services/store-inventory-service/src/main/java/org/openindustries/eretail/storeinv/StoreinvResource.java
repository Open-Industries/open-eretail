package org.openindustries.eretail.storeinv;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.util.List;
import java.util.UUID;

@Path("/api/v1/store-inventory")
@Produces(MediaType.APPLICATION_JSON)
@Tag(name = "Store Inventory", description = "Guarded per-store SKU stock and the channel reservations against it.")
public class StoreinvResource {

    @Inject
    StoreinvService service;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Open a storeinventory")
    public Response open(@Valid OpenStoreInventoryRequest request) {
        return Response.status(Response.Status.CREATED).entity(service.open(request)).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get one by id")
    public StoreInventory get(@PathParam("id") UUID id) {
        return service.get(id);
    }

    @POST
    @Path("/{id}/reservations")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Draw from the account (rejects if it would overdraw, 422)")
    public StoreReservation consume(@PathParam("id") UUID id, @Valid CreateStoreReservationRequest request) {
        return service.consume(id, request);
    }

    @GET
    @Path("/{id}/reservations")
    @Operation(summary = "List consumption records of an account")
    public List<StoreReservation> records(@PathParam("id") UUID id) {
        return service.recordsOf(id);
    }
}
