package communication.rabbitmq.handler;

import com.rekeningrijden.europe.dtos.SubInvoiceDto;
import domain.Invoice;
import exceptions.InvoiceException;
import io.sentry.Sentry;
import io.sentry.event.BreadcrumbBuilder;
import service.InvoiceService;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.event.Observes;

@Stateless
public class ExternalInvoiceHandler {

    @EJB
    private InvoiceService invoiceService;

    public void receiveExternalInvoice(@Observes SubInvoiceDto dto) {
        if(dto == null) {
            Sentry.capture("Received invoicedto is null. Can't create new invoice.");
        }

        try {
            invoiceService.publishExternalInvoice(dto);
        } catch (InvoiceException e) {
            Sentry.getContext().recordBreadcrumb(new BreadcrumbBuilder().setMessage("Unable to create new external invoice for country: " + dto.getCountry() + ". Please see exception").build());
            Sentry.capture(e);
        }

    }
}
