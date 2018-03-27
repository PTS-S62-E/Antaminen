package rest;

import domain.Invoice;
import domain.InvoiceDetails;
import exceptions.InvoiceException;
import interfaces.IInvoice;
import interfaces.IInvoiceDetail;
import service.InvoiceService;
import util.InvoiceBootstrapper;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
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

    @Path("/")
    @POST
    @Produces(APPLICATION_JSON)
    @Consumes(APPLICATION_JSON)
    public boolean createInvoice(Invoice invoice) {
        System.out.println("Check invoice null");
        if(invoice == null) { throw new WebApplicationException("Unprocessable Entity", Response.Status.fromStatusCode(422)); }
        System.out.println("Check invoice date");
        if(invoice.getInvoiceDate().isEmpty()) { throw new WebApplicationException("Unprocessable Entity", Response.Status.fromStatusCode(422)); }
        System.out.println("Check invoice country");
        if(invoice.getCountry().isEmpty()) { throw new WebApplicationException("Unprocessable Entity", Response.Status.fromStatusCode(422)); }
        System.out.println("Check invoice price");
        if(invoice.getPrice().compareTo(BigDecimal.ZERO) <= 0) { throw new WebApplicationException("Unprocessable Entity", Response.Status.fromStatusCode(422)); }
        System.out.println("Check invoice details");
        if(invoice.invoiceDetails().size() < 1) { throw new WebApplicationException("Unprocessable Entity", Response.Status.fromStatusCode(422)); }

        boolean result = false;

        try {
            result = service.createInvoice(invoice);

            if(!result) {
                throw new WebApplicationException("Returned false when creating invoice.", Response.Status.INTERNAL_SERVER_ERROR);
            } else { return result; }
        } catch (InvoiceException e) {
            throw new WebApplicationException(e.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @Path("/advanced")
    @POST
    @Produces(APPLICATION_JSON)
    public boolean createInvoiceWithParams(InvoiceBootstrapper bootstrapper) {
        if(bootstrapper == null) { throw new WebApplicationException(Response.Status.BAD_REQUEST); }
        if(bootstrapper.getInvoiceDetails().size() < 1) { throw new WebApplicationException("Unprocessable Entity", Response.Status.fromStatusCode(422)); }

        // No checks for params countryCode and invoiceDate. See below

        boolean result = false;

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDateTime date = LocalDateTime.parse(bootstrapper.getInvoiceDate(), formatter);
            // param countryCode and invoiceDate are nullable, service layer will provide correct information for creating invoice
            result = service.createInvoice(bootstrapper.getInvoiceDetails(), bootstrapper.getCountryCode(), date);

            if(!result) {
                throw new WebApplicationException("Returned false when creating invoice.", Response.Status.INTERNAL_SERVER_ERROR);
            } else { return result; }
        } catch (InvoiceException ie) {
            throw new WebApplicationException(ie.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        } catch (Exception ex) {
            throw new WebApplicationException(ex.getMessage(), Response.Status.INTERNAL_SERVER_ERROR);
        }

    }
}
