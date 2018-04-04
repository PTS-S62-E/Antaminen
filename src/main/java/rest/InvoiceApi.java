package rest;
import com.fasterxml.jackson.databind.JsonNode;
import exceptions.InvoiceException;
import interfaces.domain.IInvoice;
import io.sentry.Sentry;
import service.InvoiceService;
import util.jwt.JWTRequired;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.logging.Logger;

import static javax.ws.rs.core.MediaType.*;

@Stateless
@Path("invoices")
public class InvoiceApi {

    @EJB
    InvoiceService service;

    /**
     * Get all invoices for a user based on the token
     * @return
     */
    @GET
    @Path("/")
    @Produces(APPLICATION_JSON)
    @JWTRequired
    public ArrayList<IInvoice> getAllInvoices() {
        try {
            ArrayList<IInvoice> result = service.findInvoiceByUser(1);

            if(result == null) {
                throw new WebApplicationException(Response.Status.NO_CONTENT);
            } else {
                return result;
            }
        } catch (InvoiceException e) {
            Sentry.capture(e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Find an invoice for the provided invoiceNumber
     * @param invoiceNumber number of the invoice you want to search
     * @return
     */
    @Path("/{invoiceNumber}")
    @GET
    @Produces(APPLICATION_JSON)
    @JWTRequired
    public IInvoice findInvoiceByInvoiceNumber(@PathParam("invoiceNumber") String invoiceNumber) {
        if(invoiceNumber.isEmpty()) { throw new WebApplicationException("Unprocessable Entity", Response.Status.fromStatusCode(422)); }

        try {
            IInvoice result = service.findInvoiceByInvoiceNumber(invoiceNumber);
            if(result == null) {
                throw new WebApplicationException(Response.Status.NO_CONTENT);
            }

            return result;
        } catch (InvoiceException e) {
            Sentry.capture(e);
            throw new WebApplicationException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @POST
    @Path("/pay")
    @Consumes(APPLICATION_JSON)
    @JWTRequired
    public boolean payInvoice(JsonNode data) {
        if(data.get("invoiceNumber") == null || data.get("invoiceNumber").asText().isEmpty()) { throw new WebApplicationException("Unprocessable Entity", Response.Status.fromStatusCode(422)); }
        try {
            String invoiceNumber = data.get("invoiceNumber").asText();
            String paymentDetails = "No payment details provided.";

            if(data.get("paymentDetails") != null) { paymentDetails = data.get("paymentDetails").asText(); }

            Logger logger = Logger.getLogger(getClass().getName());
            logger.warning("Invoice number is: " + invoiceNumber);
            logger.warning("Payment details are: " + paymentDetails);



            return service.payInvoice(invoiceNumber, paymentDetails);
        } catch (InvoiceException e) {
            Sentry.capture(e);
            throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
        }
    }
}
