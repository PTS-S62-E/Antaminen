package rest;

import com.rekeningrijden.europe.interfaces.ITransLocation;
import communication.RegistrationMovement;
import domain.Owner;
import domain.Ownership;
import domain.TransLocation;
import dto.AdministrationDto;
import dto.JourneyDto;
import dto.TranslocationDto;
import exceptions.CommunicationException;
import exceptions.OwnershipException;
import io.sentry.Sentry;
import service.OwnershipService;
import util.jwt.JWTRequired;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import java.io.IOException;
import java.util.ArrayList;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/vehicles")
@Stateless
public class VehicleApi {

    @EJB
    private OwnershipService ownershipService;

    @GET
    @Path("/{id}/history/ownership")
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
}
