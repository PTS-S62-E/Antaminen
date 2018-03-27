package rest;
import exceptions.InvoiceException;
import interfaces.IInvoice;
import service.InvoiceService;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

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
