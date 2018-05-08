package rest;

import communication.RegistrationMovement;
import exceptions.CommunicationException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.io.IOException;

@Path("/test")
@Stateless
public class TestApi {

    @GET
    public Object test() throws CommunicationException, IOException {
        return RegistrationMovement.getInstance().getVehicleById(103);
    }
}
