package rest;
import com.fasterxml.jackson.databind.JsonNode;
import com.pts62.common.finland.util.JsonExceptionMapper;
import domain.Owner;
import dto.ThinInvoiceDto;
import exceptions.AccountException;
import exceptions.InvoiceException;
import interfaces.domain.IInvoice;
import io.sentry.Sentry;
import service.AccountService;
import service.InvoiceService;
import service.OwnerService;
import util.jwt.JWTRequired;
import util.jwt.JWTUtility;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.json.JsonException;
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

    @EJB
    AccountService accountService;


    /**
     * Get all invoices for a user based on the token
     * @return
     */
    @GET
    @Path("/")
    @Produces(APPLICATION_JSON)
    @JWTRequired
    public ArrayList<ThinInvoiceDto> getAllInvoices(@HeaderParam("Authorization") String token) {
        try {

            String email = JWTUtility.getSubject(token);
            Owner authenticatedOwner = accountService.findByEmailAddress(email).getOwner();

            ArrayList<ThinInvoiceDto> result = service.findInvoiceByUser(authenticatedOwner.getId());

            if(result == null) {
                throw JsonExceptionMapper.mapException(Response.Status.NO_CONTENT, "");
            } else {
                return result;
            }
        } catch (InvoiceException | AccountException e) {
            Sentry.capture(e);
            throw JsonExceptionMapper.mapException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
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
    public IInvoice findInvoiceByInvoiceNumber(@PathParam("invoiceNumber") long invoiceNumber) {
        if(invoiceNumber < 1) { throw JsonExceptionMapper.mapException(Response.Status.fromStatusCode(422), "Unporcessable Entity (invalid Invoice number)"); }

        try {
            IInvoice result = service.findInvoiceByInvoiceNumber(invoiceNumber);
            if(result == null) {
                throw JsonExceptionMapper.mapException(Response.Status.NO_CONTENT, "");
            }

            return result;
        } catch (InvoiceException e) {
            Sentry.capture(e);
            throw JsonExceptionMapper.mapException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @POST
    @Path("/pay")
    @Consumes(APPLICATION_JSON)
    @JWTRequired
    public boolean payInvoice(JsonNode data) {
        if(data.get("invoiceNumber") == null || data.get("invoiceNumber").asText().isEmpty()) { throw JsonExceptionMapper.mapException(Response.Status.fromStatusCode(422), "Unporcessable Entity (invalid Invoice number)"); }
        try {
            long invoiceNumber = data.get("invoiceNumber").asLong();
            String paymentDetails = "No payment details provided.";

            if(data.get("paymentDetails") != null) { paymentDetails = data.get("paymentDetails").asText(); }

            Logger logger = Logger.getLogger(getClass().getName());
            logger.warning("Invoice number is: " + invoiceNumber);
            logger.warning("Payment details are: " + paymentDetails);



            return service.payInvoice(invoiceNumber, paymentDetails);
        } catch (InvoiceException e) {
            Sentry.capture(e);
            throw JsonExceptionMapper.mapException(Response.Status.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GET
    @Path("/generate")
    @JWTRequired
    public Response generatedInvoices() {
        throw JsonExceptionMapper.mapException(Response.Status.NOT_ACCEPTABLE, "Generation of invoices using REST api is not allowed. Please use MessageQueue instead.");
    }
}
