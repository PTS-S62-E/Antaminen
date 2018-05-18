package rest;

import domain.Owner;
import domain.Ownership;
import dto.OwnershipWithVehicleDto;
import exceptions.OwnershipException;
import io.sentry.Sentry;
import service.OwnershipService;
import util.jwt.JWTRequired;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/vehicles")
@Stateless
public class VehicleApi {

    @EJB
    private OwnershipService ownershipService;

    @GET
    @Path("id/{id}/history/ownership")
    @JWTRequired
    @Produces(APPLICATION_JSON)
    public ArrayList<Owner> getVehicleOwnershipHistory(@PathParam("id") long vehicleId) {
        if(vehicleId < 1) { throw new WebApplicationException(Response.status(Response.Status.NOT_ACCEPTABLE).entity("Please provide a valid vehicleId").build()); }

        try {
            ArrayList<Ownership> ownerships = ownershipService.findOwnershipByVehicleId(vehicleId);

            ArrayList<Owner> result = new ArrayList<>();

            for(Ownership os : ownerships) {
                result.add(os.getOwner());
            }

            return result;
        } catch (OwnershipException e) {
            Sentry.capture(e);
            throw new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build());
        }
    }

    @GET
    @Path("licensePlate/{licensePlate}/history/ownership")
    @Produces(APPLICATION_JSON)
    public OwnershipWithVehicleDto getOwnershipWithVehicleDto(@PathParam("licensePlate") String licensePlate) {

    	//TODO: JWT REQUIRED

        if(licensePlate == null || licensePlate.equals("")) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_ACCEPTABLE).entity("Please provide a valid licensePlate").build());
        }

        try {
            return ownershipService.findOwnershipByLicensePlate(licensePlate);
        } catch (Exception e) {
            Sentry.capture(e);
            throw new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build());
        }
    }
}
