package rest;

import com.fasterxml.jackson.databind.JsonNode;
import domain.Account;
import domain.Owner;
import domain.Ownership;
import exceptions.AccountException;
import io.sentry.Sentry;
import service.AccountService;
import util.jwt.JWTUtility;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("/accounts")
@Stateless
public class AccountApi {

    @EJB
    AccountService service;

    @POST
    @Path("/login")
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    public HashMap<String, Object> login(JsonNode data) {
        if(data == null) { throw new WebApplicationException(Response.Status.UNAUTHORIZED); }
        if(data.get("email") == null || data.get("email").asText().isEmpty()) { throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE); }
        if(data.get("password") == null || data.get("password").asText().isEmpty()) { throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE); }

        String email = data.get("email").asText();
        String password = data.get("password").asText();

        try {
            Owner owner = service.login(email, password);
            if(owner == null) { throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR); }

            String token = service.generateJWT(email);

            HashMap<String, Object> result = new HashMap<String, Object>();

            result.put("token", token);
            result.put("owner", owner);

            return result;
        } catch (AccountException e) {
            throw new WebApplicationException(e.getMessage(), Response.Status.UNAUTHORIZED);
        }

    }

    @POST
    @Path("/")
    @Consumes(APPLICATION_JSON)
    public boolean createAccount(JsonNode data) {
        HashMap<String, Object> result = new HashMap<String, Object>();
        // Based on the provided JsonNode data, we should be able to create a new Owner and Account instance;
        if(data == null) { throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE); }
        if(data.get("email") == null || data.get("email").asText().isEmpty()) { throw new WebApplicationException(Response.status(Response.Status.NOT_ACCEPTABLE).entity("Please provide an email address").build()); }
        if(data.get("password") == null || data.get("password").asText().isEmpty()) { throw new WebApplicationException(Response.status(Response.Status.NOT_ACCEPTABLE).entity("Please provide a password").build()); }
        if(data.get("name") == null || data.get("name").asText().isEmpty()) { throw new WebApplicationException(Response.status(Response.Status.NOT_ACCEPTABLE).entity("Please provide a name").build()); }
        if(data.get("address") == null || data.get("address").asText().isEmpty()) { throw new WebApplicationException(Response.status(Response.Status.NOT_ACCEPTABLE).entity("Please provide an address").build()); }
        if(data.get("city") == null || data.get("city").asText().isEmpty()) { throw new WebApplicationException(Response.status(Response.Status.NOT_ACCEPTABLE).entity("Please provide a city").build()); }
        if(data.get("postalCode") == null || data.get("postalCode").asText().isEmpty()) { throw new WebApplicationException(Response.status(Response.Status.NOT_ACCEPTABLE).entity("Please provide a postal code").build()); }

        // Store everything we need in a variable
        String name = data.get("name").asText();
        String address = data.get("address").asText();
        String city = data.get("city").asText();
        String postalCode = data.get("postalCode").asText();
        String email = data.get("email").asText();
        String password = data.get("password").asText();

        Owner owner = new Owner(name, address, city, postalCode);
        Account account = new Account(email, password, owner);

        try {
            service.createAccount(account);
            return true;
        } catch (AccountException e) {
            Sentry.capture(e);
            throw new WebApplicationException(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build());
        }
    }

    @GET
    @Path("/cars")
    @Produces(APPLICATION_JSON)
    public List<Ownership> getVehicleOwnerships(@HeaderParam("Authorization") String token) {
        if(token == null || token.isEmpty()) { throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED).entity("No token provided").build()); }

        try {
            Account account = service.findByEmailAddress(JWTUtility.getSubject(token));

            return account.getOwner().getOwnership();
        } catch (AccountException e) {
            throw new WebApplicationException(Response.status(Response.Status.NOT_ACCEPTABLE).entity(e.getMessage()).build());
        }

    }
}
