package rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pts62.common.europe.ITransLocation;
import domain.Invoice;
import domain.InvoiceDetails;
import domain.TransLocation;
import exceptions.InvoiceException;
import interfaces.IInvoice;
import interfaces.IInvoiceDetail;
import service.InvoiceService;
import util.InvoiceBootstrapper;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import static javax.ws.rs.core.MediaType.*;

@Stateless
@Path("invoices")
public class InvoiceApi {

    @Inject
    private InvoiceService service;

    /**
     * Find an invoice for the provided invoiceNumber
     * @param invoiceNumber number of the invoice you want to search
     * @return
     */
    @Path("/{invoiceNumber}")
    @GET
    @Produces(APPLICATION_JSON)
    public IInvoice findInvoiceByInvoiceNumber(@PathParam("invoiceNumber") String invoiceNumber) {
        if(invoiceNumber.isEmpty()) { throw new WebApplicationException("Unprocessable Entity", Response.Status.fromStatusCode(422)); }

        try {
            IInvoice result = service.findInvoiceByInvoiceNumber(invoiceNumber);
            if(result == null) {
                throw new WebApplicationException(Response.Status.NO_CONTENT);
            }

            return result;
        } catch (InvoiceException e) {
            throw new WebApplicationException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }

    }

    @GET
    @Path("/")
    @Produces(APPLICATION_JSON)
    public String getAllInvoices() {
        return "{\"error\": \"Use another endpoint to get information.\"}";
    }
}
