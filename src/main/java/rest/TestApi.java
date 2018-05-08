package rest;

import communication.RegistrationMovement;
import exceptions.CommunicationException;
import exceptions.InvoiceException;
import service.InvoiceService;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.io.IOException;

@Path("/test")
@Stateless
public class TestApi {

    @Inject
    InvoiceService service;

    @GET
    public void test() throws CommunicationException, IOException, InvoiceException {
        service.generateInvoices();
    }
}
